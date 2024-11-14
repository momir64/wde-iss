package wedoevents.eventplanner.userManagement.controllers.userTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/eventOrganizers")
public class EventOrganizerController {

    private final EventOrganizerService eventOrganizerService;

    @Autowired
    public EventOrganizerController(EventOrganizerService eventOrganizerService) {
        this.eventOrganizerService = eventOrganizerService;
    }

    @PostMapping
    public ResponseEntity<EventOrganizer> createEventOrganizer(@RequestBody EventOrganizer eventOrganizer) {
        EventOrganizer savedAttempt = eventOrganizerService.saveEventOrganizer(eventOrganizer);
        return ResponseEntity.ok(savedAttempt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventOrganizer> getEventOrganizerById(@PathVariable UUID id) {
        return eventOrganizerService.getEventOrganizerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventOrganizer>> getAllEventOrganizers() {
        return ResponseEntity.ok(eventOrganizerService.getAllEventOrganizers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventOrganizer(@PathVariable UUID id) {
        eventOrganizerService.deleteEventOrganizer(id);
        return ResponseEntity.noContent().build();
    }
}