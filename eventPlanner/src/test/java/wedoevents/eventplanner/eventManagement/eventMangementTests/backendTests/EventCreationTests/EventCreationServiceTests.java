package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests.EventCreationTests;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wedoevents.eventplanner.eventManagement.dtos.CreateEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.models.Location;
import wedoevents.eventplanner.eventManagement.repositories.EventActivityRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventCreationServiceTests {
    @BeforeAll
    public void setUpClass() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private EventOrganizerRepository eventOrganizerRepository;

    @Mock
    private EventActivityRepository eventActivityRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private CreateEventDTO validCreateEventDTO;
    private EventType mockEventType;
    private EventOrganizer mockOrganizer;
    private EventActivity mockActivity;
    private Event savedEvent;

    @BeforeEach
    public void setUp() {
        // mock http request context for images
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        request.setContextPath("/api");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        validCreateEventDTO = new CreateEventDTO();
        validCreateEventDTO.setName("Summer Festival");
        validCreateEventDTO.setDescription("Annual summer event");
        validCreateEventDTO.setCity("New York");
        validCreateEventDTO.setAddress("123 Main St");
        validCreateEventDTO.setIsPublic(true);
        validCreateEventDTO.setDate(LocalDate.now().plusDays(1));
        validCreateEventDTO.setTime(LocalTime.NOON);
        validCreateEventDTO.setGuestCount(100);
        validCreateEventDTO.setLatitude(40.7128);
        validCreateEventDTO.setLongitude(-74.0060);
        validCreateEventDTO.setEventTypeId(UUID.randomUUID());
        validCreateEventDTO.setAgenda(Arrays.asList(UUID.randomUUID()));
        validCreateEventDTO.setOrganizerProfileId(UUID.randomUUID());

        mockEventType = new EventType();
        mockEventType.setId(validCreateEventDTO.getEventTypeId());

        mockOrganizer = new EventOrganizer();
        mockOrganizer.setId(UUID.randomUUID());
        mockOrganizer.setMyEvents(new ArrayList<>());

        mockActivity = new EventActivity();
        mockActivity.setId(validCreateEventDTO.getAgenda().get(0));

        savedEvent = new Event();
        savedEvent.setId(UUID.randomUUID());
        savedEvent.setName(validCreateEventDTO.getName());
        savedEvent.setDescription(validCreateEventDTO.getDescription());
        savedEvent.setCity(new City(validCreateEventDTO.getCity()));
        savedEvent.setAddress(validCreateEventDTO.getAddress());
        savedEvent.setIsPublic(validCreateEventDTO.getIsPublic());
        savedEvent.setDate(validCreateEventDTO.getDate());
        savedEvent.setTime(validCreateEventDTO.getTime());
        savedEvent.setGuestCount(validCreateEventDTO.getGuestCount());
        savedEvent.setLocation(new Location(
                validCreateEventDTO.getLongitude(),
                validCreateEventDTO.getLatitude()
        ));
        savedEvent.setEventType(mockEventType);
        savedEvent.setEventActivities(new ArrayList<>(Arrays.asList(mockActivity)));
        savedEvent.setImages(new ArrayList<>());
        savedEvent.setProductBudgetItems(new ArrayList<>());
        savedEvent.setServiceBudgetItems(new ArrayList<>());
        savedEvent.setEventActivities(new ArrayList<>());
    }

    @AfterEach
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();
        reset(eventRepository);

    }
    @Test
    public void testValidEventCreation() {
        when(eventTypeRepository.findById(validCreateEventDTO.getEventTypeId()))
                .thenReturn(Optional.of(mockEventType));
        when(eventOrganizerRepository.findByProfileId(validCreateEventDTO.getOrganizerProfileId()))
                .thenReturn(Optional.of(mockOrganizer));
        when(eventActivityRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockActivity));
        when(eventRepository.save(any(Event.class)))
                .thenReturn(savedEvent);

        EventComplexViewDTO result = eventService.createEvent(validCreateEventDTO);

        assertNotNull(result);
        assertEquals(savedEvent.getId(), result.getId());
        assertEquals(validCreateEventDTO.getName(), result.getName());
        assertEquals(validCreateEventDTO.getDescription(), result.getDescription());

        verify(eventRepository).save(any(Event.class));
        verify(eventOrganizerRepository).save(mockOrganizer);
        assertTrue(mockOrganizer.getMyEvents().contains(savedEvent));
    }

    @Test
    public void testEventCreationWithUnknownEventTypeId() {
        when(eventTypeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            eventService.createEvent(validCreateEventDTO);
        });
    }

    @Test
    public void testEventCreationWithUnknownOrganizerId() {
        when(eventTypeRepository.findById(validCreateEventDTO.getEventTypeId()))
                .thenReturn(Optional.of(mockEventType));
        when(eventOrganizerRepository.findByProfileId(validCreateEventDTO.getOrganizerProfileId()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            eventService.createEvent(validCreateEventDTO);
        });
    }

    @Test
    public void testEventCreationWIthPartiallyValidAgenda() {
        UUID validId = UUID.randomUUID();
        UUID invalidId = UUID.randomUUID();
        validCreateEventDTO.setAgenda(Arrays.asList(validId, invalidId));

        EventActivity validActivity = new EventActivity();
        validActivity.setId(validId);

        when(eventTypeRepository.findById(validCreateEventDTO.getEventTypeId()))
                .thenReturn(Optional.of(mockEventType));
        when(eventOrganizerRepository.findByProfileId(validCreateEventDTO.getOrganizerProfileId()))
                .thenReturn(Optional.of(mockOrganizer));
        when(eventActivityRepository.findById(validId))
                .thenReturn(Optional.of(validActivity));
        when(eventActivityRepository.findById(invalidId))
                .thenReturn(Optional.empty());
        when(eventRepository.save(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event event = invocation.getArgument(0);
                    assertEquals(1, event.getEventActivities().size()); // Only valid activity added
                    return savedEvent;
                });

        eventService.createEvent(validCreateEventDTO);
    }

    @Test
    public void testEventCreationOrganizerGetsNewEvent() {
        when(eventTypeRepository.findById(validCreateEventDTO.getEventTypeId()))
                .thenReturn(Optional.of(mockEventType));
        when(eventOrganizerRepository.findByProfileId(validCreateEventDTO.getOrganizerProfileId()))
                .thenReturn(Optional.of(mockOrganizer));
        when(eventActivityRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockActivity));
        when(eventRepository.save(any(Event.class)))
                .thenReturn(savedEvent);

        eventService.createEvent(validCreateEventDTO);

        verify(eventOrganizerRepository).save(mockOrganizer);
        assertTrue(mockOrganizer.getMyEvents().contains(savedEvent));
    }

    @Test
    public void testEventCreationWithValidImages() {
        List<String> imageNames = Arrays.asList("img1.jpg", "img2.png");
        savedEvent.setImages(imageNames);

        when(eventTypeRepository.findById(validCreateEventDTO.getEventTypeId()))
                .thenReturn(Optional.of(mockEventType));
        when(eventOrganizerRepository.findByProfileId(validCreateEventDTO.getOrganizerProfileId()))
                .thenReturn(Optional.of(mockOrganizer));
        when(eventActivityRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockActivity));
        when(eventRepository.save(any(Event.class)))
                .thenReturn(savedEvent);

        EventComplexViewDTO result = eventService.createEvent(validCreateEventDTO);

        assertNotNull(result.getImages());
        assertEquals(2, result.getImages().size());
        var imageBaseUrl = "http://localhost:8080/api/v1/events/" + savedEvent.getId();
        assertEquals(imageBaseUrl + "/images/img1.jpg", result.getImages().get(0));
        assertEquals(imageBaseUrl + "/images/img2.png", result.getImages().get(1));
    }
    @Test
    public void testEventCreationWithOrganizerWithNoEvents() {
        mockOrganizer.setMyEvents(new ArrayList<>());

        when(eventTypeRepository.findById(validCreateEventDTO.getEventTypeId()))
                .thenReturn(Optional.of(mockEventType));
        when(eventOrganizerRepository.findByProfileId(validCreateEventDTO.getOrganizerProfileId()))
                .thenReturn(Optional.of(mockOrganizer));
        when(eventActivityRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockActivity));
        when(eventRepository.save(any(Event.class)))
                .thenReturn(savedEvent);

        eventService.createEvent(validCreateEventDTO);

        assertNotNull(mockOrganizer.getMyEvents());
        assertTrue(mockOrganizer.getMyEvents().contains(savedEvent));
    }
    @Test
    public void testEventCreationWIthValidEventType() {
        UUID eventTypeId = UUID.randomUUID();
        validCreateEventDTO.setEventTypeId(eventTypeId);
        mockEventType.setId(eventTypeId);

        when(eventTypeRepository.findById(eventTypeId))
                .thenReturn(Optional.of(mockEventType));
        when(eventOrganizerRepository.findByProfileId(validCreateEventDTO.getOrganizerProfileId()))
                .thenReturn(Optional.of(mockOrganizer));
        when(eventActivityRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockActivity));
        when(eventRepository.save(any(Event.class)))
                .thenReturn(savedEvent);

        EventComplexViewDTO result = eventService.createEvent(validCreateEventDTO);

        assertEquals(eventTypeId, result.getEventTypeId());
    }
}

