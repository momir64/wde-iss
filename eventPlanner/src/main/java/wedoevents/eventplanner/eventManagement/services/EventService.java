package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CreateEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.models.*;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
                                       .map(EventComplexViewDTO::new)
                                       .toList();
    }

    public Page<EventComplexViewDTO> searchEvents(String searchTerms, String city, UUID eventTypeId, Double minRating, Double maxRating,
                                                  LocalDate dateRangeStart, LocalDate dateRangeEnd, String sortBy, String order, int page, int size, UUID organizerId) {
        Pageable pageable;
        if (sortBy != null && (sortBy.equals("name") || sortBy.equals("date")))
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order != null ? order : "ASC"), sortBy));
        else
            pageable = PageRequest.of(page, size);
        Page<Event> eventPage;
        if(organizerId != null){
            List<Event> myEvents = eventOrganizerRepository.getMyEventsByProfileId(organizerId);

            // Filter the events based on the criteria
            List<Event> filteredEvents = myEvents.stream()
                    .filter(event -> {
                        boolean matchesSearchTerms = searchTerms == null || event.getName().toLowerCase().contains(searchTerms.toLowerCase());
                        boolean matchesCity = city == null || event.getCity().getName().equalsIgnoreCase(city);
                        boolean matchesEventType = eventTypeId == null || event.getEventType().getId().equals(eventTypeId);
                        boolean matchesDateRange = (dateRangeStart == null || !event.getDate().isBefore(dateRangeStart)) &&
                                (dateRangeEnd == null || !event.getDate().isAfter(dateRangeEnd));
//                        boolean matchesRating = (minRating == null || event.getRating() >= minRating) &&
//                                (maxRating == null || event.getRating() <= maxRating);
                        return matchesSearchTerms && matchesCity && matchesEventType && matchesDateRange; //&& matchesRating;
                    })
                    .collect(Collectors.toList());
            int start = page * size;
            int end = Math.min(start + size, filteredEvents.size());
            List<Event> paginatedEvents = filteredEvents.subList(start, end);

            // Convert filtered and paginated events to DTOs
            return new PageImpl<>(paginatedEvents.stream().map(EventComplexViewDTO::new).collect(Collectors.toList()), pageable, filteredEvents.size());

        }else{
            eventPage = eventRepository.searchEvents(searchTerms, city, eventTypeId, dateRangeStart, dateRangeEnd, pageable);
            return eventPage.map(EventComplexViewDTO::new);
        }
    }

    public List<EventComplexViewDTO> getTopEvents(String city) {
        return eventRepository.getTopEvents(city).stream().map(EventComplexViewDTO::new).collect(Collectors.toList());
    }

    public EventComplexViewDTO createEvent(CreateEventDTO createEventDTO) {
        Optional<EventType> eventTypeMaybe = eventTypeRepository.findById(createEventDTO.getEventTypeId());

        if (eventTypeMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Optional<EventOrganizer> eventOrganizerMaybe = this.eventOrganizerRepository.findByProfileId(createEventDTO.getOrganizerProfileId());

        if (eventOrganizerMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        EventOrganizer eventOrganizer = eventOrganizerMaybe.get();

        Event newEvent = new Event();
        newEvent.setEventActivities(new ArrayList<>()); // todo agenda
        newEvent.setImages(new ArrayList<>()); // todo images with image service
        newEvent.setProductBudgetItems(new ArrayList<>());
        newEvent.setServiceBudgetItems(new ArrayList<>());

        newEvent.setEventType(eventTypeMaybe.get());
        newEvent.setDescription(createEventDTO.getDescription());
        newEvent.setName(createEventDTO.getName());
        newEvent.setCity(new City(createEventDTO.getCity()));
        newEvent.setAddress(createEventDTO.getAddress());
        newEvent.setIsPublic(createEventDTO.getIsPublic());
        newEvent.setDate(createEventDTO.getDate());
        newEvent.setTime(createEventDTO.getTime());
        newEvent.setLocation(new Location(createEventDTO.getLongitude(), createEventDTO.getLatitude())); // todo map
        newEvent.setGuestCount(createEventDTO.getGuestCount());

        Event createdEvent = eventRepository.save(newEvent);
        eventOrganizer.getMyEvents().add(createdEvent);
        eventOrganizerRepository.save(eventOrganizer);

        return new EventComplexViewDTO(createdEvent);
    }

    public Optional<Event> getEventById(UUID id) {
        return eventRepository.findById(id);
    }
}