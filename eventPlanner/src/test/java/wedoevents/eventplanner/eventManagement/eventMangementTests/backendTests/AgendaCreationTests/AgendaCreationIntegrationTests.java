package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests.AgendaCreationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import wedoevents.eventplanner.eventManagement.dtos.EventActivitiesDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventActivityDTO;
import wedoevents.eventplanner.eventManagement.models.EventActivity;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgendaCreationIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String baseUrl = "/api/v1/events/agenda";

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
    @Test
    void createAgenda_withValidActivities_returnsCreatedIds() {
        // Prepare valid contiguous activities (intentionally unsorted)
        EventActivityDTO activity1 = createActivityDTO("Session A", "Desc A", "Room 1",
                LocalTime.of(10, 0), LocalTime.of(11, 0));

        EventActivityDTO activity2 = createActivityDTO("Session B", "Desc B", "Room 2",
                LocalTime.of(9, 0), LocalTime.of(10, 0));

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity1, activity2));

        // Execute request
        ResponseEntity<List> response = restTemplate.postForEntity(
                baseUrl,
                request,
                List.class
        );

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UUID> responseIds = response.getBody();
        assertNotNull(responseIds);
        assertEquals(2, responseIds.size());

        // Verify database state
        List<EventActivity> savedActivities = jdbcTemplate.query(
                "SELECT * FROM event_activity ORDER BY start_time",
                (rs, rowNum) -> new EventActivity(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getString("location")
                )
        );

        assertEquals(2, savedActivities.size());
        assertEquals("Session B", savedActivities.get(0).getName());
        assertEquals(LocalTime.of(9, 0), savedActivities.get(0).getStartTime());
        assertEquals("Session A", savedActivities.get(1).getName());
        assertEquals(LocalTime.of(10, 0), savedActivities.get(1).getStartTime());
    }

    @Test
    void createAgenda_withEmptyActivities_returnsBadRequest() {
        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(Collections.emptyList());

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createAgenda_withInvalidTimes_returnsBadRequest() {
        // Start time equals end time
        EventActivityDTO invalidActivity = createActivityDTO("Invalid", "Desc", "Room X",
                LocalTime.of(10, 0), LocalTime.of(10, 0));

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(invalidActivity));

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createAgenda_withGapBetweenActivities_returnsBadRequest() {
        EventActivityDTO activity1 = createActivityDTO("Morning", "Desc", "Room A",
                LocalTime.of(9, 0), LocalTime.of(10, 0));

        EventActivityDTO activity2 = createActivityDTO("Afternoon", "Desc", "Room B",
                LocalTime.of(10, 15), LocalTime.of(11, 0));  // 15-minute gap

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity1, activity2));

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createAgenda_withOverlappingActivities_returnsBadRequest() {
        EventActivityDTO activity1 = createActivityDTO("Session 1", "Desc", "Room 1",
                LocalTime.of(9, 0), LocalTime.of(10, 30));

        EventActivityDTO activity2 = createActivityDTO("Session 2", "Desc", "Room 2",
                LocalTime.of(10, 0), LocalTime.of(11, 0));  // Overlaps by 30 minutes

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity1, activity2));

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createAgenda_withMissingRequiredFields_returnsBadRequest() {
        EventActivityDTO invalidActivity = new EventActivityDTO();
        invalidActivity.setStartTime(LocalTime.of(9, 0));
        invalidActivity.setEndTime(LocalTime.of(10, 0));
        // Missing name, description, location

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(invalidActivity));

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createAgenda_withMidnightActivity_returnsBadRequest() {
        EventActivityDTO activity = createActivityDTO("Night", "Desc", "Hall",
                LocalTime.of(23, 0), LocalTime.of(1, 0));  // Spans midnight

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity));

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createAgenda_withSingleActivity_createsSuccessfully() {
        EventActivityDTO activity = createActivityDTO("Single", "Desc", "Room",
                LocalTime.of(9, 0), LocalTime.of(10, 0));

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity));

        ResponseEntity<List> response = restTemplate.postForEntity(
                baseUrl,
                request,
                List.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM event_activity", Integer.class
        );
        assertEquals(1, count);
    }

    private EventActivityDTO createActivityDTO(String name, String desc, String location,
                                               LocalTime start, LocalTime end) {
        EventActivityDTO dto = new EventActivityDTO();
        dto.setName(name);
        dto.setDescription(desc);
        dto.setLocation(location);
        dto.setStartTime(start);
        dto.setEndTime(end);
        return dto;
    }
}
