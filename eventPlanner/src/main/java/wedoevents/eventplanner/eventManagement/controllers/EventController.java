package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.CreateEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;
    private final ImageService imageService;

    @Autowired
    public EventController(EventService eventService, ImageService imageService) {
        this.eventService = eventService;
        this.imageService = imageService;
    }


    @PostMapping
    public ResponseEntity<EventComplexViewDTO> createEvent(@RequestBody CreateEventDTO createEventDTO) {
        try {
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
            return ResponseEntity.ok(eventService.getTopEvents(city));
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
                                          @RequestParam(value = "organizerId", required = false) UUID organizerId,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRangeStart,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRangeEnd,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String order,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(eventService.searchEvents(searchTerms, city, eventTypeId, minRating, maxRating, dateRangeStart, dateRangeEnd, sortBy, order, page, size,organizerId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping(value = "/{id}/images/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getProfileImage(@PathVariable("id") UUID id, @PathVariable("image_name") String imageName) {
        try {
            ImageLocationConfiguration config = new ImageLocationConfiguration("event", id);
            Optional<byte[]> image = imageService.getImage(imageName, config);
            if (image.isEmpty())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
            return ResponseEntity.ok().body(image.get());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }
}