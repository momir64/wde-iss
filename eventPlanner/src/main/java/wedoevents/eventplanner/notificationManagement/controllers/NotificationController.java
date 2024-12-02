package wedoevents.eventplanner.notificationManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.notificationManagement.dtos.NotificationDTO;
import wedoevents.eventplanner.notificationManagement.models.Notification;
import wedoevents.eventplanner.notificationManagement.services.NotificationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createOrUpdateNotification(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.saveNotification(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
//        List<Notification> notifications = notificationService.getAllNotifications();
        List<NotificationDTO> notifications = List.of(
                new NotificationDTO(UUID.randomUUID(), false, LocalDate.now(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "Best notification title 1", "", "", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new NotificationDTO(UUID.randomUUID(), false, LocalDate.now(), "This is some message 2.", "Best notification title 2", "", "", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new NotificationDTO(UUID.randomUUID(), true, LocalDate.now(), "This is some message 3.", "Best notification title 3", "", "", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new NotificationDTO(UUID.randomUUID(), true, LocalDate.now(), "This is some message 4.", "Best notification title 4", "", "", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new NotificationDTO(UUID.randomUUID(), true, LocalDate.now(), "This is some message 5.", "Best notification title 5", "", "", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
                                                     );
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> readNotifications(@PathVariable UUID id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable UUID id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok)
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        if (notificationService.getNotificationById(id).isPresent()) {
            notificationService.deleteNotification(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}