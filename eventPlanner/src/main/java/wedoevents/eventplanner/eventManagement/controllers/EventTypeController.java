package wedoevents.eventplanner.eventManagement.controllers;

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
    public EventType createEventType(@RequestBody EventType eventType) {
        return eventTypeService.saveEventType(eventType);
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