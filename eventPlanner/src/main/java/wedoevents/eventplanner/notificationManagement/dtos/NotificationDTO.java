package wedoevents.eventplanner.notificationManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.notificationManagement.models.Notification;
import wedoevents.eventplanner.notificationManagement.models.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDTO {
    private UUID id;
    private boolean isSeen;
    private LocalDateTime time;
    private String title;
    private String message;
    private String webRedirect;
    private NotificationType type;
    private UUID entityId;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.isSeen = notification.isSeen();
        this.time = notification.getTime();
        this.title = notification.getTitle();
        this.message = notification.getMessage();
        this.webRedirect = notification.getWebRedirect();
        this.entityId = notification.getEntityId();
        this.type = notification.getType();
    }
}
