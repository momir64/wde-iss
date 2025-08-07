package wedoevents.eventplanner.notificationManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.notificationManagement.dtos.NotificationDTO;
import wedoevents.eventplanner.notificationManagement.models.NotificationType;
import wedoevents.eventplanner.notificationManagement.services.NotificationService;

import java.util.List;
import java.util.UUID;

import wedoevents.eventplanner.shared.config.auth.JwtUtil;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications(HttpServletRequest request) {
        try {
            UUID profileId = JwtUtil.extractProfileId(request);
            return new ResponseEntity<>(notificationService.getNotifications(profileId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> readNotification(@PathVariable UUID id) {
        try {
            notificationService.readNotification(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}