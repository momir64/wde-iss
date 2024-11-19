package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventOrganizerService {

    private final EventOrganizerRepository eventOrganizerRepository;

    @Autowired
    public EventOrganizerService(EventOrganizerRepository eventOrganizerRepository) {
        this.eventOrganizerRepository = eventOrganizerRepository;
    }

    public EventOrganizer saveEventOrganizer(EventOrganizer eventOrganizer) {
        return eventOrganizerRepository.save(eventOrganizer);
    }

    public Optional<EventOrganizer> getEventOrganizerById(UUID id) {
        return eventOrganizerRepository.findById(id);
    }

    public List<EventOrganizer> getAllEventOrganizers() {
        return eventOrganizerRepository.findAll();
    }

    public void deleteEventOrganizer(UUID id) {
        eventOrganizerRepository.deleteById(id);
    }

    public EventOrganizer createOrUpdateEventOrganizer(EventOrganizer eventOrganizer) {
        if (eventOrganizer.getId() != null && eventOrganizerRepository.existsById(eventOrganizer.getId())) {
            EventOrganizer existingOrganizer = eventOrganizerRepository.findById(eventOrganizer.getId()).orElse(null);
            if (existingOrganizer != null) {
                // Copy all properties except the ID
                BeanUtils.copyProperties(eventOrganizer, existingOrganizer, "id");
                return eventOrganizerRepository.save(existingOrganizer);
            }
        }
        // Create new
        return eventOrganizerRepository.save(eventOrganizer);
    }
}