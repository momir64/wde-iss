package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.GuestListDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.notificationManagement.models.NotificationType;
import wedoevents.eventplanner.notificationManagement.services.NotificationService;
import wedoevents.eventplanner.shared.services.emailService.IEmailService;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invitations")
public class InvitationsController {
    private final IEmailService emailService;
    private final ProfileService profileService;
    private final GuestService guestService;
    private final EventService eventService;
    private final NotificationService notificationService;

    @Autowired
    public InvitationsController(ProfileService profileService, EventService eventService, @Qualifier("sendGridEmailService") IEmailService emailService, GuestService guestService, NotificationService notificationService) {
        this.profileService = profileService;
        this.eventService = eventService;
        this.emailService = emailService;
        this.guestService = guestService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<?> createInvitations(@RequestBody GuestListDTO request) {
        Optional<Event> event = eventService.getEventById(request.getEventId());
        if (event.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event id");

        if (event.get().getIsPublic())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Event is public");

        List<String> emails = request.getEmails();
        if (emails.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty guest list");

        for (String email : emails) {
            Optional<Profile> profile = profileService.findProfileByEmail(email);
            if (profile.isEmpty()) {
                //create an account, send email
                Profile newProfile = profileService.createEmptyGuestProfile(email);
                guestService.createGuestForEvent(newProfile, event.get());
                try {
                    String response = emailService.sendQuickRegistrationEmail(email, email, event.get().getName(), request.getOrganizerName(), request.getOrganizerSurname(), newProfile.getId().toString());
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
                }
            } else {
                Optional<Guest> guest = guestService.getGuestByProfile(profile.get());
                if (guest.isPresent()) {
                    guestService.addInvitation(guest.get(), event.get());
                    try {
                        String response = emailService.sendEventInvitationEmail(email, email, event.get().getName(), request.getOrganizerName(), request.getOrganizerSurname(), guest.get().getProfile().getId().toString(), event.get().getId().toString());
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
                    }
                    notificationService.sendNotification(guest.get().getProfile(), "New invitation", "You've been invited to the " + event.get().getName() + " event!", NotificationType.EVENT, event.get().getId());
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm/{eventId}/{profileId}/{decision}")
    public ResponseEntity<?> confirmInvitation(@PathVariable UUID eventId, @PathVariable UUID profileId, @PathVariable boolean decision) {
        final String errorUrl = "http://localhost:4200/error/";

        Optional<Profile> profileOptional = profileService.findProfileById(profileId);
        if (profileOptional.isEmpty()) {
            String message = "Profile%20not%20found";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl + message).build();
        }

        Profile profile = profileOptional.get();
        Optional<Guest> guestOptional = guestService.getGuestByProfile(profile);
        if (guestOptional.isEmpty()) {
            String message = "Guest%20not%20found";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl + message).build();
        }

        Guest guest = guestOptional.get();
        Optional<Event> eventOptional = guestService.getInvitedEvent(guest, eventId);
        if (eventOptional.isEmpty()) {
            String message = "Event%20invitation%20already%20processed";
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", errorUrl + message).build();
        }

        guestService.confirmInvitation(eventOptional.get(), guest, decision);
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:4200/login").build();
    }
}
