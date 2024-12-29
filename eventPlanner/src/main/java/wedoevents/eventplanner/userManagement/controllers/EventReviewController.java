package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.notificationManagement.models.NotificationType;
import wedoevents.eventplanner.notificationManagement.services.NotificationService;
import wedoevents.eventplanner.userManagement.dtos.EvenReviewResponseDTO;
import wedoevents.eventplanner.userManagement.dtos.EventReviewDTO;
import wedoevents.eventplanner.userManagement.models.EventReview;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.services.EventReviewService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-reviews")
public class EventReviewController {

    private final EventReviewService eventReviewService;
    private final EventService eventService;
    private final GuestService guestService;
    private final EventOrganizerService eventOrganizerService;
    private final NotificationService notificationService;

    @Autowired
    public EventReviewController(EventReviewService eventReviewService, EventService eventService, GuestService guestService, EventOrganizerService eventOrganizerService, NotificationService notificationService) {
        this.eventReviewService = eventReviewService;
        this.eventService = eventService;
        this.guestService = guestService;
        this.eventOrganizerService = eventOrganizerService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody EventReviewDTO eventReviewDTO) {
        Optional<Guest> guest = guestService.getGuestById(eventReviewDTO.getGuestId());
        Optional<Event> event = eventService.getEventById(eventReviewDTO.getEventId());
        if (guest.isEmpty() || event.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (!eventReviewService.IsReviewAllowed(guest.get(), event.get()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        eventReviewService.createReview(eventReviewDTO, guest.get(), event.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/check/{guestId}/{eventId}")
    public ResponseEntity<?> checkIfReviewIsAllowed(@PathVariable UUID guestId, @PathVariable UUID eventId) {
        Optional<Guest> guest = guestService.getGuestById(guestId);
        Optional<Event> event = eventService.getEventById(eventId);
        if (guest.isEmpty() || event.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (!eventReviewService.IsReviewAllowed(guest.get(), event.get()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("process")
    public ResponseEntity<?> processReview(@RequestBody UUID eventReviewId, @RequestBody boolean isAccepted) {
        Optional<EventReview> eventReview = eventReviewService.getReviewById(eventReviewId);
        if (eventReview.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        eventReviewService.processReview(eventReview.get(), isAccepted);
        if (isAccepted) {
            Event event = eventReview.get().getEvent();
            Optional<EventOrganizer> organizerOptional = eventOrganizerService.getEventOrganizerByEventId(event.getId());
            if (organizerOptional.isPresent()) {
                String title = "New review for " + event.getName();
                String message = "Your event " + event.getName() + " has received a new review from " +
                                 eventReview.get().getGuest().getName() + " " + eventReview.get().getGuest().getSurname() + "!";
                notificationService.sendNotification(organizerOptional.get().getProfile(), title, message, NotificationType.EVENT, event.getId());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EvenReviewResponseDTO>> getAcceptedReviewsByEventId(@PathVariable UUID eventId) {
        return new ResponseEntity<>(eventReviewService.getAcceptedReviewsByEventId(eventId), HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EvenReviewResponseDTO>> getPendingReviews() {
        return new ResponseEntity<>(eventReviewService.getAllPendingReviews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventReview> getReviewById(@PathVariable UUID id) {
        return eventReviewService.getReviewById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventReview>> getAllReviews() {
        return ResponseEntity.ok(eventReviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        eventReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}