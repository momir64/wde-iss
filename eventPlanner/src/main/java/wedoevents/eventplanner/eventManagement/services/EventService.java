package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CreateEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.models.*;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final EventOrganizerRepository eventOrganizerRepository;

    @Autowired
    public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository,
                        EventOrganizerRepository eventOrganizerRepository) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
    }

    public List<EventComplexViewDTO> getEventsFromOrganizer(UUID eventOrganizerId) {
        return eventOrganizerRepository.getMyEventsById(eventOrganizerId)
                .stream()
                .map(EventComplexViewDTO::toDto)
                .toList();
    }

    public EventComplexViewDTO createEvent(CreateEventDTO createEventDTO) {
        Optional<EventType> eventTypeMaybe = eventTypeRepository.findById(createEventDTO.getEventTypeId());

        if (eventTypeMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Optional<EventOrganizer> eventOrganizerMaybe = this.eventOrganizerRepository.findById(createEventDTO.getOrganizerId());

        if (eventOrganizerMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        EventOrganizer eventOrganizer = eventOrganizerMaybe.get();

        Event newEvent = new Event();
//        newEvent.setEventActivities(new ArrayList<>()); // todo agenda
//        newEvent.setImages(new ArrayList<>()); // todo images with image service
        newEvent.setEventType(eventTypeMaybe.get());

        newEvent.setDescription(createEventDTO.getDescription());
        newEvent.setName(createEventDTO.getName());
        newEvent.setCity(createEventDTO.getCity());
        newEvent.setAddress(createEventDTO.getAddress());
        newEvent.setIsPublic(createEventDTO.getIsPublic());
        newEvent.setDate(createEventDTO.getDate());
        newEvent.setTime(createEventDTO.getTime());
        newEvent.setLocation(new Location(createEventDTO.getLongitude(), createEventDTO.getLatitude())); // todo map
        newEvent.setGuestCount(createEventDTO.getGuestCount());

        Event createdEvent = eventRepository.save(newEvent);
        eventOrganizer.getMyEvents().add(createdEvent);
        eventOrganizerRepository.save(eventOrganizer);

        return EventComplexViewDTO.toDto(createdEvent);
    }

    public Optional<Event> getEventById(UUID id) {
        return eventRepository.findById(id);
    }
}