package wedoevents.eventplanner.eventManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public EventType saveEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    public EventType getEventTypeById(UUID id) {
        return eventTypeRepository.findById(id).orElse(null);
    }

    public void deleteEventType(UUID id) {
        eventTypeRepository.deleteById(id);
    }
}