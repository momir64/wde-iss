package wedoevents.eventplanner.notificationManagement.services;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.notificationManagement.dtos.NotificationDTO;
import wedoevents.eventplanner.notificationManagement.models.Notification;
import wedoevents.eventplanner.notificationManagement.models.NotificationType;
import wedoevents.eventplanner.notificationManagement.repositories.NotificationRepository;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.repositories.ProfileRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, ProfileRepository profileRepository) {
        this.notificationRepository = notificationRepository;
        this.profileRepository = profileRepository;
    }

    public List<NotificationDTO> getNotifications(UUID profileId) {
        return notificationRepository.findAllByProfile_IdOrderByTimeDesc(profileId).stream().map(NotificationDTO::new).toList();
    }

    public void readNotification(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new);
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    public void sendNotification(UUID profileId, String title, String body) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        sendNotification(profile, title, body, null, null);
    }

    public void sendNotification(UUID profileId, String title, String body, NotificationType type, UUID entityId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        sendNotification(profile, title, body, type, entityId);
    }

    public void sendNotification(Profile profile, String title, String body) {
        sendNotification(profile, title, body, null, null);
    }

    public void sendNotification(Profile profile, String title, String body, @Nullable NotificationType type, @Nullable UUID entityId) {
        String webRedirect = type != null && entityId != null ? type.toString().toLowerCase() + "/" + entityId : null;
        Notification notification = new Notification(null, false, LocalDateTime.now(), title, body, webRedirect, type, entityId, profile);
        notificationRepository.save(notification);
        sendNotificationToFirebase(profile.getId(), title, body, type, entityId);
    }

    private void sendNotificationToFirebase(UUID profileId, String title, String body, @Nullable NotificationType type, @Nullable UUID entityId) {
        com.google.firebase.messaging.Notification firebaseNotification =
                com.google.firebase.messaging.Notification.builder().setTitle(title).setBody(body).build();
        AndroidConfig config = AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build();
        Message.Builder messageBuilder = Message.builder().setTopic("user_" + profileId).setAndroidConfig(config).setNotification(firebaseNotification);

        if (type != null && entityId != null) {
            HashMap<String, String> data = new HashMap<>();
            data.put("type", type.toString());
            data.put("entityId", entityId.toString());
            messageBuilder = messageBuilder.putAllData(data);
        }

        try {
            FirebaseMessaging.getInstance().send(messageBuilder.build());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}