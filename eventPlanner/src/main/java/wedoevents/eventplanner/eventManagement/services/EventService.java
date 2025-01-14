package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.eventManagement.dtos.*;
import wedoevents.eventplanner.eventManagement.models.*;
import wedoevents.eventplanner.eventManagement.repositories.EventActivityRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;
import wedoevents.eventplanner.userManagement.services.EventReviewService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.io.IOException;
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
    private final EventActivityRepository eventActivityRepository;
    private final EventReviewService eventReviewService;
    private final GuestService guestService;
    private final ImageService imageService;

    @Autowired
    public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository,
                        EventOrganizerRepository eventOrganizerRepository, EventActivityRepository eventActivityRepository,
                        EventReviewService eventReviewService, GuestService guestService, ImageService imageService) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.eventActivityRepository = eventActivityRepository;
        this.eventReviewService = eventReviewService;
        this.guestService = guestService;
        this.imageService = imageService;
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

    public EventComplexViewDTO createEvent(CreateEventDTO createEventDTO) throws Exception {
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
        newEvent.setEventActivities(new ArrayList<>());
        for(UUID id: createEventDTO.getAgenda()){
            Optional<EventActivity> activity = eventActivityRepository.findById(id);
            activity.ifPresent(eventActivity -> newEvent.getEventActivities().add(eventActivity));
        }
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

    public List<String> putEventImages(List<MultipartFile> images, UUID eventId) throws Exception {
        List<String> imageNames = new ArrayList<>();
        if(images == null || images.isEmpty()){
            return imageNames;
        }
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Event event = eventOptional.get();
        ImageLocationConfiguration config = new ImageLocationConfiguration("event", event.getId());

        try{
            for(MultipartFile file: images){
                String imageName = imageService.saveImageToStorage(file,config);
                imageNames.add(imageName);
            }
        }catch (Exception e){
            imageNames = new ArrayList<>();

            event.setImages(imageNames);
            eventRepository.save(event);

            throw new Exception();
        }
        event.setImages(imageNames);
        eventRepository.save(event);
        return imageNames;
    }

    public Optional<Event> getEventById(UUID id) {
        return eventRepository.findById(id);
    }

    public List<UUID> createAgenda(EventActivitiesDTO agenda){
        List<UUID> createdActivityIds = new ArrayList<>();

        for (EventActivityDTO activityDTO : agenda.getEventActivities()) {
            EventActivity eventActivity = new EventActivity();

            eventActivity.setName(activityDTO.getName());
            eventActivity.setDescription(activityDTO.getDescription());
            eventActivity.setLocation(activityDTO.getLocation());
            eventActivity.setStartTime(activityDTO.getStartTime());
            eventActivity.setEndTime(activityDTO.getEndTime());
            eventActivity = eventActivityRepository.save(eventActivity);
            createdActivityIds.add(eventActivity.getId());
        }

        return createdActivityIds;
    }
    public List<EventAdminViewDTO> getAllPublicEvents() {
        List<Event> events =  eventRepository.findAllPublicEvents();
        return events.stream().map(event -> {
            EventAdminViewDTO dto = new EventAdminViewDTO();
            dto.setId(event.getId());
            dto.setName(event.getName());
            dto.setCity(event.getCity().getName());
            dto.setAttendance(guestService.getAcceptedGuestCount(event.getId()));
            dto.setRating(eventReviewService.getAverageRating(event.getId())); // Get the rating from ReviewService
            return dto;
        }).collect(Collectors.toList());
    }
}