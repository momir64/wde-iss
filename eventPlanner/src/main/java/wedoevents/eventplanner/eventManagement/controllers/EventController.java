package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.CreateEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.services.EventService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;


    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // todo when session tracking is enabled, add which organizer created the event
    // todo for now the organizer id is fixed to "1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236"
    @PostMapping
    public ResponseEntity<EventComplexViewDTO> createEvent(@RequestBody CreateEventDTO createEventDTO) {
        try {
            createEventDTO.setOrganizerId(UUID.fromString("1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236"));
            EventComplexViewDTO createdEvent = eventService.createEvent(createEventDTO);
            return ResponseEntity.ok(createdEvent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{eventOrganizerId}/my-events")
    public ResponseEntity<List<EventComplexViewDTO>> getEventsFromOrganizer(@PathVariable UUID eventOrganizerId) {
        try {
            List<EventComplexViewDTO> events = eventService.getEventsFromOrganizer(eventOrganizerId);
            return ResponseEntity.ok(events);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopEvents(@RequestParam(value = "city", required = false) String city) {
        try {
            List<EventComplexViewDTO> events = buildMockEvents(5);
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

            List<EventComplexViewDTO> events = buildMockEvents(10);
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

    private List<EventComplexViewDTO> buildMockEvents(int n) {
        List<EventComplexViewDTO> events = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            events.add(new EventComplexViewDTO(UUID.randomUUID(),
                                               String.format("Best party ever %d", i),
                                               String.format("This will be the best party ever %d!", i),
                                               LocalDate.now(),
                                               LocalTime.now(),
                                               String.format("London %d", i),
                                               String.format("Big Ben %d", i),
                                               100 * i,
                                               false,
                                               Arrays.asList(String.format("https://picsum.photos/303/20%d", i),
                                                             String.format("https://picsum.photos/304/20%d", i),
                                                             String.format("https://picsum.photos/305/20%d", i)),
                                               UUID.randomUUID(),
                                               new ArrayList<>(),
                                               new ArrayList<>(),
                                               0.0,
                                               0.0,
                                               buildMockUUIDs(3),
                                               2 + i * 0.9 % 5
            ));
        }

        return events;
    }
}