package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.EventDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createOrUpdateEvent(@RequestBody Event event) {
        Event savedEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        if (eventService.getEventById(id).isPresent()) {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopEvents(@RequestParam(value = "city", required = false) String city) {
        try {
            List<EventDTO> events = buildMockEvents(5);
            return ResponseEntity.ok(events);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping
    public ResponseEntity<?> searchEvents(@RequestParam(value = "searchTerms", required = false) String searchTerms,
                                          @RequestParam(value = "city", required = false) String city,
                                          @RequestParam(value = "type", required = false) UUID eventTypeId,
                                          @RequestParam(value = "minRating", required = false) Double minRating,
                                          @RequestParam(value = "maxRating", required = false) Double maxRating,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRangeStart,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRangeEnd,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String order,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            List<EventDTO> events = buildMockEvents(10);
            return ResponseEntity.ok(events);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    private List<UUID> buildMockUUIDs(int n) {
        List<UUID> uuids = new ArrayList<>();
        for (int i = 0; i < n; i++)
             uuids.add(UUID.randomUUID());
        return uuids;
    }

    private List<EventDTO> buildMockEvents(int n) {
        List<EventDTO> events = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            events.add(new EventDTO(UUID.randomUUID(),
                                    String.format("London %d", i),
                                    String.format("Best party ever %d", i),
                                    String.format("This will be the best party ever %d!", i),
                                    LocalDateTime.now(),
                                    2 + i * 0.9 % 5,
                                    100 * i,
                                    Arrays.asList(String.format("https://picsum.photos/303/20%d", i),
                                                  String.format("https://picsum.photos/304/20%d", i),
                                                  String.format("https://picsum.photos/305/20%d", i)),
                                    UUID.randomUUID(),
                                    buildMockUUIDs(3),
                                    buildMockUUIDs(3),
                                    UUID.randomUUID(),
                                    buildMockUUIDs(3)
            ));
        }

        return events;
    }
}