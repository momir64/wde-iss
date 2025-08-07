package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests.AgendaCreationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
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
    private final String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5lLnNtaXRoQGV4YW1wbGUuY29tIiwicHJvZmlsZUlkIjoiM2Q4MmU5YjgtM2Q5Yi00YzdkLWIyNDQtMWU2NzI1Yjc4NDU2Iiwicm9sZXMiOlsiT1JHQU5JWkVSIl0sInVzZXJJZCI6ImIzOGQ3MTZiLTRkMmEtNGZkMy1iMThjLWJmYTEyOGYyNGI5OSIsImlhdCI6MTc0OTk5NjM2OCwiZXhwIjoxNzgxNTMyMzY4fQ.Ip4mqHnzLatgaLRy58JKZH_l3fWfhDn5kPIu3k6lkpScUNiaF_fxzMRoCm8Rb8Jj5US4QfeiLSH79MLRHhlDXA";
    @BeforeAll
    public void setUpDatabase() {
        // referential integrity is turned off, because H2 sees inserting of
        // primary keys that are set to GENERATED_VALUE (the default) as
        // a violation of referential integrity, and in our file,
        // all primary keys are predefined, to be able to connect entities
        // referential integrity is never broken because this same script is
        // used in the PostgreSQL database where referential integrity is turned on
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        restTemplate.getRestTemplate()
                .getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().setBearerAuth(jwt);
                    return execution.execute(request, body);
                });
    }
    private <T> ResponseEntity<T> postWithAuth(Object body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                responseType
        );
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
    @Sql({"classpath:entity-insertion.sql"})
    void testValidAgendaCreation() {
        EventActivityDTO activity1 = createActivityDTO("Session A", "Desc A", "Room 1",
                LocalTime.of(10, 0), LocalTime.of(11, 0));

        EventActivityDTO activity2 = createActivityDTO("Session B", "Desc B", "Room 2",
                LocalTime.of(9, 0), LocalTime.of(10, 0));

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity1, activity2));

        ResponseEntity<List> response = postWithAuth(
                request,
                List.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<String> rawIds = response.getBody();          // <- strings
        assertNotNull(rawIds);
        assertEquals(2, rawIds.size());

        List<UUID> responseIds = rawIds.stream()
                .map(UUID::fromString)
                .toList();

        String placeholders = String.join(",", Collections.nCopies(responseIds.size(), "?"));

        String sql = """
            SELECT * FROM event_activity
            WHERE id IN (%s)
            ORDER BY start_time
        """.formatted(placeholders);

        Object[] params = responseIds.toArray();

        List<EventActivity> savedActivities = jdbcTemplate.query(
                sql,
                params,                                          // <-- here
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
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaCreationWithEmptyValues() {
        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(Collections.emptyList());

        ResponseEntity<String> response = postWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaCreationWithSameStartAndEndTime() {
        // Start time equals end time
        EventActivityDTO invalidActivity = createActivityDTO("Invalid", "Desc", "Room X",
                LocalTime.of(10, 0), LocalTime.of(10, 0));

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(invalidActivity));

        ResponseEntity<String> response = postWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaCreationWithEndTimeBeforeStartTime() {
        // Start time is after end time
        EventActivityDTO invalidActivity = createActivityDTO("Invalid", "Desc", "Room X",
                LocalTime.of(10, 0), LocalTime.of(9, 0));

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(invalidActivity));

        ResponseEntity<String> response = postWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaCreationWithActivityGaps() {
        EventActivityDTO activity1 = createActivityDTO("Morning", "Desc", "Room A",
                LocalTime.of(9, 0), LocalTime.of(10, 0));

        EventActivityDTO activity2 = createActivityDTO("Afternoon", "Desc", "Room B",
                LocalTime.of(10, 15), LocalTime.of(11, 0));  // 15-minute gap

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity1, activity2));

        ResponseEntity<String> response = postWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})

    void testAgendaCreationWithOverlappingActivities() {
        EventActivityDTO activity1 = createActivityDTO("Session 1", "Desc", "Room 1",
                LocalTime.of(9, 0), LocalTime.of(10, 30));

        EventActivityDTO activity2 = createActivityDTO("Session 2", "Desc", "Room 2",
                LocalTime.of(10, 0), LocalTime.of(11, 0));  // Overlaps by 30 minutes

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity1, activity2));

        ResponseEntity<String> response = postWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaCreationWithMissingValues() {
        EventActivityDTO invalidActivity = new EventActivityDTO();
        invalidActivity.setStartTime(LocalTime.of(9, 0));
        invalidActivity.setEndTime(LocalTime.of(10, 0));
        // Missing name, description, location

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(invalidActivity));

        ResponseEntity<String> response = postWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaCreationWithActivitiesSpanningTwoDays() {
        EventActivityDTO activity = createActivityDTO("Night", "Desc", "Hall",
                LocalTime.of(23, 0), LocalTime.of(1, 0));  // Spans two days

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EventActivitiesDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = postWithAuth(
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaCreationWIthSingleActivity() {
        EventActivityDTO activity = createActivityDTO("Single", "Desc", "Room",
                LocalTime.of(9, 0), LocalTime.of(10, 0));

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(UUID.randomUUID());
        request.setEventActivities(List.of(activity));

        ResponseEntity<List> response = postWithAuth(
                request,
                List.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());

        UUID activityId = UUID.fromString((String) response.getBody().get(0));


        EventActivity saved = jdbcTemplate.queryForObject(
                "SELECT * FROM event_activity WHERE id = ?",
                (rs, rowNum) -> new EventActivity(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getString("location")
                ),
                activityId
        );

        assertNotNull(saved);
        assertEquals("Single", saved.getName());
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
