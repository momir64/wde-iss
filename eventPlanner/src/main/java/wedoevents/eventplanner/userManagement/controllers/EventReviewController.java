package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;
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

    @Autowired
    public EventReviewController(EventReviewService eventReviewService, EventService eventService, GuestService guestService, EventOrganizerService eventOrganizerService) {
        this.eventReviewService = eventReviewService;
        this.eventService = eventService;
        this.guestService = guestService;
        this.eventOrganizerService = eventOrganizerService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody EventReviewDTO eventReviewDTO) {
        Optional<Guest> guest = guestService.getGuestById(eventReviewDTO.getGuestId());
        if (guest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Event> event = eventService.getEventById(eventReviewDTO.getEventId());
        if (event.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventReviewService.createReview(eventReviewDTO,guest.get(),event.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("process")
    public ResponseEntity<?> processReview(@RequestBody UUID eventReviewId, @RequestBody boolean isAccepted) {
        Optional<EventReview> eventReview = eventReviewService.getReviewById(eventReviewId);
        if (eventReview.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventReviewService.processReview(eventReview.get(),isAccepted);
        if(isAccepted){
            Optional<EventOrganizer> organizerOptional = eventOrganizerService.getEventOrganizerByEventId(eventReview.get().getEvent().getId());
            //TODO send review notification
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
        return eventReviewService.getReviewById(id)
                                 .map(ResponseEntity::ok)
                                 .orElse(ResponseEntity.notFound().build());
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