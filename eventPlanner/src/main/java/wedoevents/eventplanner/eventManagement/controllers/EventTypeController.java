package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.EventTypeResponseDTO;
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
        List<EventTypeResponseDTO> eventTypes = eventTypeService.getAllEventTypes();
        return new ResponseEntity<>(eventTypes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventTypeResponseDTO> createEventType(@RequestBody EventTypeResponseDTO eventTypeDTO) {
        EventType eventType = eventTypeService.mapToEntity(eventTypeDTO);

        EventType savedEventType = eventTypeService.saveEventType(eventType);

        EventTypeResponseDTO savedEventTypeDTO = eventTypeService.mapToResponseDTO(savedEventType);

        return new ResponseEntity<>(savedEventTypeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventTypeResponseDTO> updateEventType(
            @PathVariable UUID id,
            @RequestBody EventTypeResponseDTO updatedEventTypeDTO) {
        try {
            EventTypeResponseDTO savedEventTypeDTO = eventTypeService.updateEventType(id, updatedEventTypeDTO);
            return new ResponseEntity<>(savedEventTypeDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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