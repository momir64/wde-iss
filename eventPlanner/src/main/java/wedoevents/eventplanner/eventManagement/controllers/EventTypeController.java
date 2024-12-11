package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.ExtendedEventTypeDTO;
import wedoevents.eventplanner.eventManagement.dtos.RecommendedCategoriesDTO;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.services.EventTypeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-types")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @GetMapping
    public ResponseEntity<?> getAllEventTypes() {
        List<ExtendedEventTypeDTO> eventTypes = eventTypeService.getAllEventTypes();
        return new ResponseEntity<>(eventTypes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExtendedEventTypeDTO> createEventType(@RequestBody ExtendedEventTypeDTO eventTypeDTO) {
        EventType eventType = eventTypeService.mapToEntity(eventTypeDTO);

        EventType savedEventType = eventTypeService.saveEventType(eventType);

        ExtendedEventTypeDTO savedEventTypeDTO = eventTypeService.mapToResponseDTO(savedEventType);

        return new ResponseEntity<>(savedEventTypeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtendedEventTypeDTO> updateEventType(
            @PathVariable UUID id,
            @RequestBody ExtendedEventTypeDTO updatedEventTypeDTO) {
        try {
            ExtendedEventTypeDTO savedEventTypeDTO = eventTypeService.updateEventType(id, updatedEventTypeDTO);
            return new ResponseEntity<>(savedEventTypeDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recommended/{eventTypeId}")
    public ResponseEntity<RecommendedCategoriesDTO> getRecommendedListingCategoriesForEventType(@PathVariable UUID eventTypeId) {
        try {
            return ResponseEntity.ok(eventTypeService.getRecommendedCategoriesForType(eventTypeId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public EventType getEventTypeById(@PathVariable UUID id) {
        return eventTypeService.getEventTypeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEventType(@PathVariable UUID id) {
        eventTypeService.deleteEventType(id);
    }
}