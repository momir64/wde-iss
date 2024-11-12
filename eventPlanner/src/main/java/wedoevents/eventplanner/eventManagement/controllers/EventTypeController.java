package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public List<EventType> getAllEventTypes() {
        return eventTypeService.getAllEventTypes();
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