package wedoevents.eventplanner.userManagement.services.userTypes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.shared.services.mappers.CalendarEventMapper;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    //proveri da li je listing favorite
    //dodaj listing u favorite
    //dobavi favorite listinge

    public List<CalendarEventDTO> getCalendarEvents(UUID organizerId) {
        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(organizerId).orElse(null);
        if (eventOrganizer == null) {
            return null;
        }
        List<Event> myEvents = eventOrganizer.getMyEvents();
        return myEvents.stream()
                .map(CalendarEventMapper::toCalendarEventDTO)
                .collect(Collectors.toList());
    }
}