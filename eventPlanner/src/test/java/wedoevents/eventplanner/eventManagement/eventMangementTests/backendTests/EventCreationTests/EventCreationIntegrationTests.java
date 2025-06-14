package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests.EventCreationTests;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import wedoevents.eventplanner.eventManagement.dtos.CreateEventDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;
import wedoevents.eventplanner.eventManagement.repositories.EventActivityRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventCreationIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;

    @Autowired
    private EventActivityRepository eventActivityRepository;

    @Autowired
    private EventRepository eventRepository;

    private final String baseUrl = "/api/v1/events";
    private final UUID eventOrganizerId = UUID.fromString("b38d716b-4d2a-4fd3-b18c-bfa128f24b99");
    private final UUID eventOrganizerProfileId = UUID.fromString("3d82e9b8-3d9b-4c7d-b244-1e6725b78456");
    private final UUID eventTypeId = UUID.fromString("a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832");
    private final  List<UUID> activityIds = Arrays.asList(
            UUID.fromString("c68e6b75-31a3-4f9c-a2ec-5f15246ad2a1"),
            UUID.fromString("ae2c6d3f-8ff7-41ec-b1be-1fa0931c3902")
    );
    private EventOrganizer organizer;

    @BeforeAll
    public void setUpDatabase() {
        // referential integrity is turned off, because H2 sees inserting of
        // primary keys that are set to GENERATED_VALUE (the default) as
        // a violation of referential integrity, and in our file,
        // all primary keys are predefined, to be able to connect entities
        // referential integrity is never broken because this same script is
        // used in the PostgreSQL database where referential integrity is turned on
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
    }

    @AfterEach
    public void truncateAllTables() {
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'PUBLIC'";

        List<String> tableNames = jdbcTemplate.queryForList(query, String.class);

        for (String tableName : tableNames) {
            String truncateQuery = "TRUNCATE TABLE " + tableName;
            jdbcTemplate.execute(truncateQuery);
        }
    }

    private CreateEventDTO createValidEventDTO() {
        CreateEventDTO dto = new CreateEventDTO();
        dto.setName("Summer Wedding");
        dto.setDescription("Outdoor summer wedding ceremony");
        dto.setCity("Novi Sad");
        dto.setAddress("123 Park Avenue");
        dto.setIsPublic(true);
        dto.setDate(LocalDate.now().plusDays(30));
        dto.setTime(LocalTime.of(14, 30));
        dto.setGuestCount(150);
        dto.setLatitude(12.0);
        dto.setLongitude(12.0);
        dto.setEventTypeId(eventTypeId);
        dto.setAgenda(activityIds);
        dto.setOrganizerProfileId(eventOrganizerProfileId);
        return dto;
    }

    private void createAgendaForEvent() {

        EventActivity activity1 = new EventActivity(
                activityIds.get(0),
                "Ceremony",
                "Wedding ceremony",
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                "Main Hall"
        );

        EventActivity activity2 = new EventActivity(
                activityIds.get(1),
                "Reception",
                "Wedding reception",
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                "Garden"
        );

        jdbcTemplate.update(
                "INSERT INTO event_activity (id, name, description, start_time, end_time, location, event_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                activity1.getId(), activity1.getName(), activity1.getDescription(),
                activity1.getStartTime(), activity1.getEndTime(), activity1.getLocation(),
                null
        );

        jdbcTemplate.update(
                "INSERT INTO event_activity (id, name, description, start_time, end_time, location, event_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                activity2.getId(), activity2.getName(), activity2.getDescription(),
                activity2.getStartTime(), activity2.getEndTime(), activity2.getLocation(),
                null
        );
    }


    @Test
    @Sql({"classpath:entity-insertion.sql"})
    public void testValidEventCreation() {
        CreateEventDTO dto = createValidEventDTO();
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<EventComplexViewDTO> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                EventComplexViewDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        EventComplexViewDTO createdEvent = response.getBody();
        assertNotNull(createdEvent);
        assertEquals(dto.getName(), createdEvent.getName());
        assertEquals(dto.getAddress(), createdEvent.getAddress());

        Optional<Event> savedEvent = eventRepository.findById(createdEvent.getId());
        assertTrue(savedEvent.isPresent());

        EventOrganizer organizer = eventOrganizerRepository.findByProfileIdWithEvents(eventOrganizerProfileId).orElseThrow();
        assertTrue(organizer.getMyEvents().stream()
                .anyMatch(e -> e.getId().equals(createdEvent.getId())));
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    public void testEventCreationWithUnknownEventTypeId() {
        CreateEventDTO dto = createValidEventDTO();
        dto.setEventTypeId(UUID.randomUUID()); // Invalid ID
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    public void testEventCreationWithUnknownOrganizerId() {
        CreateEventDTO dto = createValidEventDTO();
        dto.setOrganizerProfileId(UUID.randomUUID()); // Invalid ID
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error processing request", response.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    public void testEventCreationWithValidAndInvalidActivities() {
        createAgendaForEvent();
        CreateEventDTO dto = createValidEventDTO();
        UUID invalidActivityId = UUID.randomUUID();
        List<UUID> mixedAgenda = new ArrayList<>(activityIds);
        mixedAgenda.add(invalidActivityId); // Add invalid activity
        dto.setAgenda(mixedAgenda);
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<EventComplexViewDTO> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                EventComplexViewDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        EventComplexViewDTO createdEvent = response.getBody();
        assertNotNull(createdEvent);
        Event savedEvent = eventRepository.findByIdWithActivities(createdEvent.getId()).get();
        List<UUID> savedActivityIds = savedEvent.getEventActivities().stream()
                .map(EventActivity::getId)
                .toList();

        assertEquals(activityIds.size(), savedActivityIds.size());
        assertTrue(savedActivityIds.containsAll(activityIds));

        assertFalse(savedActivityIds.contains(invalidActivityId));
    }


    @Test
    public void testEventCreationWithPastDate() {
        CreateEventDTO dto = createValidEventDTO();
        dto.setDate(LocalDate.now().minusDays(1)); // Past date
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testEventCreationWithZeroGuestCount() {
        CreateEventDTO dto = createValidEventDTO();
        dto.setGuestCount(0); // Invalid count
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testEventCreationWithNegativeGuestCount() {
        CreateEventDTO dto = createValidEventDTO();
        dto.setGuestCount(-1); // Invalid count
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    public void testEventCreationWithMissingFields() {
        CreateEventDTO dto = new CreateEventDTO(); // Missing required fields
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


}
