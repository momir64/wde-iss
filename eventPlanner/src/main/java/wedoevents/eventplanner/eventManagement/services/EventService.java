package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.dtos.*;
import wedoevents.eventplanner.eventManagement.models.*;
import wedoevents.eventplanner.eventManagement.repositories.EventActivityRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.userManagement.dtos.EvenReviewResponseDTO;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;
import wedoevents.eventplanner.userManagement.services.EventReviewService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
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
    private final EventOrganizerService eventOrganizerService;

    @Autowired
    public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository,
                        EventOrganizerRepository eventOrganizerRepository, EventActivityRepository eventActivityRepository,
                        EventReviewService eventReviewService, GuestService guestService, ImageService imageService,
                        EventOrganizerService eventOrganizerService) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.eventActivityRepository = eventActivityRepository;
        this.eventReviewService = eventReviewService;
        this.guestService = guestService;
        this.imageService = imageService;
        this.eventOrganizerService = eventOrganizerService;
    }

    public List<EventComplexViewDTO> getEventsFromOrganizer(UUID eventOrganizerId) {
        return eventOrganizerRepository.getMyEventsById(eventOrganizerId)
                                       .stream()
                                       .map(EventComplexViewDTO::new)
                                       .toList();
    }

    public EventEditViewDTO getEventFromOrganizer(UUID eventOrganizerId, UUID eventId) {
        Optional<EventOrganizer> organizer = eventOrganizerRepository.findById(eventOrganizerId);
        if (organizer.isEmpty()) {
            return null;
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty() || !organizer.get().getMyEvents().contains(event.get())) {
            return null;
        }
        EventEditViewDTO response = new EventEditViewDTO(event.get());
        response.setMinGuestCount(eventRepository.countPossibleGuestsByEventId(eventId));
        return response;
    }

    public Page<EventComplexViewDTO> searchEvents(String searchTerms, String city, UUID eventTypeId, Double minRating, Double maxRating,
                                                  LocalDate dateRangeStart, LocalDate dateRangeEnd, String sortBy, String order, int page, int size, UUID organizerId) {
        Pageable pageable;
        Page<Event> eventPage;

        if (order == null || (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")))
            order = "asc";

        if (sortBy != null)
            sortBy = sortBy.toLowerCase();

        pageable = PageRequest.of(page, size);

        if (organizerId != null)
            eventPage = eventOrganizerRepository.searchMyEvents(organizerId, searchTerms, city, eventTypeId, minRating, maxRating, dateRangeStart, dateRangeEnd, sortBy, order, pageable);
        else
            eventPage = eventRepository.searchEvents(searchTerms, city, eventTypeId, minRating, maxRating, dateRangeStart, dateRangeEnd, sortBy, order, pageable);

        Page<EventComplexViewDTO> responsePage = eventPage.map(EventComplexViewDTO::new);
        for (EventComplexViewDTO event : responsePage.getContent()) {
            List<EvenReviewResponseDTO> reviews = eventReviewService.getAcceptedReviewsByEventId(event.getId());
            event.setRating(calculateAverageGrade(reviews));
        }
        return responsePage;
    }

    public List<EventComplexViewDTO> getTopEvents(String city) {
        List<EventComplexViewDTO> events = eventRepository.getTopEvents(city).stream().map(EventComplexViewDTO::new).collect(Collectors.toList());
        for (EventComplexViewDTO event : events) {
            List<EvenReviewResponseDTO> reviews = eventReviewService.getAcceptedReviewsByEventId(event.getId());
            event.setRating(calculateAverageGrade(reviews));
        }
        return events;
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
        newEvent.setEventActivities(new ArrayList<>());
        for (UUID id : createEventDTO.getAgenda()) {
            Optional<EventActivity> activity = eventActivityRepository.findById(id);
            activity.ifPresent(eventActivity -> newEvent.getEventActivities().add(eventActivity));
        }
        newEvent.setImages(new ArrayList<>());
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
        newEvent.setLocation(new Location(createEventDTO.getLongitude(), createEventDTO.getLatitude()));
        newEvent.setGuestCount(createEventDTO.getGuestCount());

        Event createdEvent = eventRepository.save(newEvent);

        eventOrganizer.getMyEvents().add(createdEvent);
        eventOrganizerRepository.save(eventOrganizer);


        return new EventComplexViewDTO(createdEvent);
    }

    public void updateEvent(CreateEventDTO createEventDTO) throws EntityNotFoundException {
        Optional<EventType> eventTypeMaybe = eventTypeRepository.findById(createEventDTO.getEventTypeId());

        if (eventTypeMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Optional<EventOrganizer> eventOrganizerMaybe = this.eventOrganizerRepository.findById(createEventDTO.getOrganizerProfileId());

        if (eventOrganizerMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Optional<Event> eventOptional = eventRepository.findById(createEventDTO.getEventId());
        if (eventOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Event event = eventOptional.get();
        event.setEventType(eventTypeMaybe.get());
        event.setDescription(createEventDTO.getDescription());
        event.setName(createEventDTO.getName());
        event.setCity(new City(createEventDTO.getCity()));
        event.setAddress(createEventDTO.getAddress());
        event.setIsPublic(createEventDTO.getIsPublic());
        event.setDate(createEventDTO.getDate());
        event.setLocation(new Location(createEventDTO.getLongitude(), createEventDTO.getLatitude()));
        event.setGuestCount(createEventDTO.getGuestCount());

        eventRepository.save(event);
    }

    public List<String> putEventImages(List<MultipartFile> images, UUID eventId) throws Exception {
        List<String> imageNames = new ArrayList<>();

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Event event = eventOptional.get();
        ImageLocationConfiguration config = new ImageLocationConfiguration("event", event.getId());
        if (images == null || images.isEmpty()) {
            event.setImages(imageNames);
            eventRepository.save(event);
            return imageNames;
        }
        try {
            for (MultipartFile file : images) {
                String imageName = imageService.saveImageToStorage(file, config);
                imageNames.add(imageName);
            }
        } catch (Exception e) {
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

    public List<UUID> createAgenda(EventActivitiesDTO agenda) {
        List<EventActivityDTO> activities = new ArrayList<>(agenda.getEventActivities());

        validateActivityTimes(activities);
        validateSequentialActivities(activities);

        List<UUID> createdIds = new ArrayList<>();
        for (EventActivityDTO dto : activities) {
            EventActivity entity = new EventActivity();
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setLocation(dto.getLocation());
            entity.setStartTime(dto.getStartTime());
            entity.setEndTime(dto.getEndTime());
            entity = eventActivityRepository.save(entity);
            createdIds.add(entity.getId());
        }
        return createdIds;
    }
    @Transactional
    public boolean updateAgenda(EventActivitiesDTO agenda) {
        Optional<Event> eventOptional = eventRepository.findById(agenda.getEventId());
        if (eventOptional.isEmpty()) {
            return false;
        }

        List<EventActivityDTO> activities = new ArrayList<>(agenda.getEventActivities());
        validateActivityTimes(activities);
        validateSequentialActivities(activities);

        Event event = eventOptional.get();
        //clear previous agenda
        event.getEventActivities().clear();
        eventRepository.save(event);

        for (EventActivityDTO activityDTO : agenda.getEventActivities()) {
            EventActivity eventActivity = new EventActivity();

            eventActivity.setName(activityDTO.getName());
            eventActivity.setDescription(activityDTO.getDescription());
            eventActivity.setLocation(activityDTO.getLocation());
            eventActivity.setStartTime(activityDTO.getStartTime());
            eventActivity.setEndTime(activityDTO.getEndTime());
            eventActivity = eventActivityRepository.save(eventActivity);
            event.getEventActivities().add(eventActivity);
        }
        //set earliest as start
        LocalTime earliestStartTime = findEarliestStartTime(event.getEventActivities());
        if (earliestStartTime != null) {
            event.setTime(earliestStartTime);
        }

        eventRepository.save(event);

        return true;
    }
    private void validateActivityTimes(List<EventActivityDTO> activities) {
        for (EventActivityDTO a : activities) {
            if (!a.getStartTime().isBefore(a.getEndTime())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("Activity '%s' has start time %s which is not before end time %s",
                                      a.getName(), a.getStartTime(), a.getEndTime())
                );
            }
        }
    }

    private void validateSequentialActivities(List<EventActivityDTO> activities) {
        activities.sort(Comparator.comparing(EventActivityDTO::getStartTime));
        for (int i = 1; i < activities.size(); i++) {
            LocalTime prevEnd = activities.get(i - 1).getEndTime();
            LocalTime thisStart = activities.get(i).getStartTime();
            if (!thisStart.equals(prevEnd)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("Activity '%s' must start at %s to follow '%s' ending at %s",
                                      activities.get(i).getName(),
                                      thisStart,
                                      activities.get(i - 1).getName(),
                                      prevEnd)
                );
            }
        }
    }

    private LocalTime findEarliestStartTime(List<EventActivity> eventActivities) {
        if (eventActivities == null || eventActivities.isEmpty()) {
            return null;
        }

        LocalTime earliestStartTime = eventActivities.get(0).getStartTime();
        for (EventActivity activity : eventActivities) {
            if (activity.getStartTime().isBefore(earliestStartTime)) {
                earliestStartTime = activity.getStartTime();
            }
        }

        return earliestStartTime;
    }
    public List<EventActivityDTO> getAgenda(UUID eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            return null;
        }
        List<EventActivityDTO> response = new ArrayList<>();
        for (EventActivity activity : eventOptional.get().getEventActivities()) {
            EventActivityDTO activityDTO = new EventActivityDTO();
            activityDTO.setName(activity.getName());
            activityDTO.setDescription(activity.getDescription());
            activityDTO.setLocation(activity.getLocation());
            activityDTO.setStartTime(activity.getStartTime());
            activityDTO.setEndTime(activity.getEndTime());
            response.add(activityDTO);
        }
        return response;
    }
    public List<EventAdminViewDTO> getAllPublicEvents() {
        List<Event> events = eventRepository.findAllPublicEvents();
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
    public EventDetailedViewDTO getDetailedEvent(UUID eventId, boolean isGuest, UUID userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            return null;
        }
        Event event = eventOptional.get();
        EventDetailedViewDTO response = new EventDetailedViewDTO();
        List<EvenReviewResponseDTO> reviews = eventReviewService.getAcceptedReviewsByEventId(eventId);
        Optional<EventOrganizer> organizer = eventOrganizerService.getEventOrganizerByEventId(eventId);
        response.setReviews(reviews);
        response.setAcceptedGuests((double) guestService.getAcceptedGuestCount(eventId));
        response.setAverageRating(calculateAverageGrade(reviews));
        response.setId(event.getId());
        response.setCity(event.getCity().getName());
        response.setDescription(event.getDescription());
        response.setAddress(event.getAddress());
        response.setIsPublic(event.getIsPublic());
        response.setDate(event.getDate());
        response.setTime(event.getTime());
        response.setName(event.getName());
        response.setGuestCount(event.getGuestCount());
        response.setLongitude(event.getLocation().getLongitude());
        response.setLatitude(event.getLocation().getLatitude());
        if (organizer.isPresent()) {
            response.setOrganizerCredentials(organizer.get().getName() + " " + organizer.get().getSurname());
        } else {
            response.setOrganizerCredentials("");
        }
        if (isGuest) {
            response.setIsDeletable(false);
            response.setIsFavorite(guestService.isEventFavorited(userId, eventId));
            response.setIsAccepted(guestService.isEventAccepted(userId, eventId));
            response.setIsPdfAvailable(false);
            response.setIsEditable(false);
        } else {
            response.setIsFavorite(false);
            response.setIsAccepted(false);
            if (organizer.isPresent()) {
                response.setIsEditable(true);
                response.setIsPdfAvailable(true);
                response.setIsDeletable(checkIfEventIsDeletable(eventId, organizer.get().getId(), userId));
            } else {
                response.setIsDeletable(false);
                response.setIsEditable(false);
                response.setIsPdfAvailable(false);
            }
        }
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();
        response.setImages(event.getImages()
                                .stream()
                                .map(image -> String.format("%s/api/v1/events/%s/images/%s", baseUrl, event.getId(), image))
                                .collect(Collectors.toList()));

        return response;
    }

    public double calculateAverageGrade(List<EvenReviewResponseDTO> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        OptionalDouble average = reviews.stream()
                                        .mapToInt(EvenReviewResponseDTO::getGrade)
                                        .average();

        return average.orElse(0.0);
    }
    public boolean deleteEvent(UUID eventId, UUID userId) {
        Optional<EventOrganizer> organizer = eventOrganizerService.getEventOrganizerById(userId);
        if (organizer.isEmpty()) {
            return false;
        }
        if (checkIfEventIsDeletable(eventId, userId, userId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }
    public boolean checkIfEventIsDeletable(UUID eventId, UUID organizerId, UUID userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            return false;
        }

        Event event = eventOptional.get();
        if (guestService.checkIfGuestIsInvitedOrAccepted(null, eventId)) {
            return false;
        }

        if (event.getServiceBudgetItems().stream()
                 .anyMatch(serviceBudgetItem -> serviceBudgetItem.getService() != null)) {
            return false;
        }

        if (event.getProductBudgetItems().stream()
                 .anyMatch(productBudgetItem -> productBudgetItem.getProduct() != null)) {
            return false;
        }

        return organizerId == userId;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}