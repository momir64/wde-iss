package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.Profile;
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
        if (eventOrganizer.getProfile() != null) {
            Optional<EventOrganizer> existingOrganizerOpt = eventOrganizerRepository.findByProfile(eventOrganizer.getProfile());

            if (existingOrganizerOpt.isPresent()) {
                EventOrganizer existingOrganizer = existingOrganizerOpt.get();
                BeanUtils.copyProperties(eventOrganizer, existingOrganizer, "id", "profile"); // Preserve id and profile
                return eventOrganizerRepository.save(existingOrganizer);
            }
        }
        // Create new if no existing organizer found
        return eventOrganizerRepository.save(eventOrganizer);
    }
    public void deleteByProfile(Profile profile){
        eventOrganizerRepository.deleteByProfile(profile);
    }
    public Optional<EventOrganizer> getEventOrganizerByEventId(UUID eventId) {
        return eventOrganizerRepository.findByEventId(eventId);
    }
    public Optional<EventOrganizer> getEventOrganizerByProfileId(UUID profileId) {
        return eventOrganizerRepository.findByProfileId(profileId);
    }

}