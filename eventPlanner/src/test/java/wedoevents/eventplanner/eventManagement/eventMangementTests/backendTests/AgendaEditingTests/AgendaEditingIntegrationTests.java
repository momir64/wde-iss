package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests.AgendaEditingTests;

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
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgendaEditingIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String baseUrl = "/api/v1/events/agenda";
    private UUID existingEventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
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
    private <T> ResponseEntity<T> putWithAuth(Object body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
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

    private UUID createEventWithActivities() {
        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");

        EventActivity activity1 = new EventActivity(
                UUID.randomUUID(),
                "Ceremony",
                "Wedding ceremony",
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                "Main Hall"
        );

        EventActivity activity2 = new EventActivity(
                UUID.randomUUID(),
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
                eventId
        );

        jdbcTemplate.update(
                "INSERT INTO event_activity (id, name, description, start_time, end_time, location, event_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                activity2.getId(), activity2.getName(), activity2.getDescription(),
                activity2.getStartTime(), activity2.getEndTime(), activity2.getLocation(),
                eventId
        );

        return eventId;
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testValidAgendaEditing() {
        existingEventId = createEventWithActivities();

        EventActivityDTO updatedActivity1 = createActivityDTO(
                "Updated Morning", "New desc", "New Room",
                LocalTime.of(8, 30), LocalTime.of(9, 30)
        );

        EventActivityDTO updatedActivity2 = createActivityDTO(
                "Extended Workshop", "Updated", "Main Hall",
                LocalTime.of(9, 30), LocalTime.of(11, 30)
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(updatedActivity1, updatedActivity2));

        ResponseEntity<Void> response = putWithAuth(
                request,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());


        Integer activityCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM event_activity WHERE event_id = ?",
                Integer.class,
                existingEventId
        );
        assertEquals(2, activityCount);

        List<EventActivity> savedActivities = jdbcTemplate.query(
                "SELECT * FROM event_activity WHERE event_id = ? ORDER BY start_time",
                (rs, rowNum) -> new EventActivity(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getString("location")
                ),
                existingEventId
        );

        assertEquals(2, savedActivities.size());
        assertEquals("Updated Morning", savedActivities.get(0).getName());
        assertEquals(LocalTime.of(8, 30), savedActivities.get(0).getStartTime());
        assertEquals("Extended Workshop", savedActivities.get(1).getName());
        assertEquals(LocalTime.of(9, 30), savedActivities.get(1).getStartTime());

        LocalTime eventTime = jdbcTemplate.queryForObject(
                "SELECT time FROM event WHERE id = ?",
                LocalTime.class,
                existingEventId
        );
        assertEquals(LocalTime.of(8, 30), eventTime);
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaEditingWithUnknownEventId() {
        UUID nonExistentId = UUID.randomUUID();

        EventActivityDTO updatedActivity1 = createActivityDTO(
                "Updated Morning", "New desc", "New Room",
                LocalTime.of(8, 30), LocalTime.of(9, 30)
        );

        EventActivityDTO updatedActivity2 = createActivityDTO(
                "Extended Workshop", "Updated", "Main Hall",
                LocalTime.of(9, 30), LocalTime.of(11, 30)
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(updatedActivity1, updatedActivity2));
        request.setEventId(nonExistentId);

        ResponseEntity<Void> response = putWithAuth(
                request,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaEditingWithSameStartAndEndTime() {
        existingEventId = createEventWithActivities();

        EventActivityDTO invalidActivity = createActivityDTO(
                "Invalid", "Desc", "Room",
                LocalTime.of(10, 0), LocalTime.of(10, 0) // Same start and end time
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(invalidActivity));

        ResponseEntity<String> response = putWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Integer activityCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM event_activity WHERE event_id = ?",
                Integer.class,
                existingEventId
        );
        assertEquals(2, activityCount);
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaEditingWithEndTimeBeforeStartTime() {
        existingEventId = createEventWithActivities();

        EventActivityDTO invalidActivity = createActivityDTO(
                "Invalid", "Desc", "Room",
                LocalTime.of(10, 0), LocalTime.of(9, 0) // End time before start time
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(invalidActivity));

        ResponseEntity<String> response = putWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Integer activityCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM event_activity WHERE event_id = ?",
                Integer.class,
                existingEventId
        );
        assertEquals(2, activityCount);
    }


    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaEditingWithGaps() {
        existingEventId = createEventWithActivities();

        EventActivityDTO activity1 = createActivityDTO(
                "Activity 1", "Desc", "Room",
                LocalTime.of(13, 0), LocalTime.of(14, 0)
        );

        EventActivityDTO activity2 = createActivityDTO(
                "Activity 2", "Desc", "Room",
                LocalTime.of(14, 15), LocalTime.of(15, 0)  // 15-min gap
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(activity1, activity2));

        ResponseEntity<String> response = putWithAuth(
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        List<EventActivity> activities = jdbcTemplate.query(
                "SELECT * FROM event_activity WHERE event_id = ?",
                (rs, rowNum) -> new EventActivity(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getString("location")
                ),
                existingEventId
        );
        assertEquals(2, activities.size());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})

    void testAgendaEditingWithUnorderedActivities() {
        existingEventId = createEventWithActivities();

        EventActivityDTO activity2 = createActivityDTO(
                "Later Activity", "Desc", "Room",
                LocalTime.of(14, 0), LocalTime.of(15, 0)
        );

        EventActivityDTO activity1 = createActivityDTO(
                "Earlier Activity", "Desc", "Room",
                LocalTime.of(13, 0), LocalTime.of(14, 0)
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(activity2, activity1));

        ResponseEntity<Void> response = putWithAuth(
                request,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<EventActivity> savedActivities = jdbcTemplate.query(
                "SELECT * FROM event_activity WHERE event_id = ? ORDER BY start_time",
                (rs, rowNum) -> new EventActivity(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getString("location")
                ),
                existingEventId
        );

        assertEquals(2, savedActivities.size());
        assertEquals("Earlier Activity", savedActivities.get(0).getName());
        assertEquals(LocalTime.of(13, 0), savedActivities.get(0).getStartTime());
        assertEquals("Later Activity", savedActivities.get(1).getName());
        assertEquals(LocalTime.of(14, 0), savedActivities.get(1).getStartTime());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaEditingWithDifferentTime() {
        existingEventId = createEventWithActivities();

        LocalTime originalTime = jdbcTemplate.queryForObject(
                "SELECT time FROM event WHERE id = ?",
                LocalTime.class,
                existingEventId
        );
        assertEquals(LocalTime.of(15, 0), originalTime);

        EventActivityDTO activity = createActivityDTO(
                "Afternoon Session", "Desc", "Room",
                LocalTime.of(13, 0), LocalTime.of(15, 0)
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(activity));

        ResponseEntity<Void> response = putWithAuth(
                request,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        LocalTime updatedTime = jdbcTemplate.queryForObject(
                "SELECT time FROM event WHERE id = ?",
                LocalTime.class,
                existingEventId
        );
        assertEquals(LocalTime.of(13, 0), updatedTime);
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testAgendaEditingOldActivitiesDeletion() {
        existingEventId = createEventWithActivities();

        List<UUID> originalIds = jdbcTemplate.queryForList(
                "SELECT id FROM event_activity WHERE event_id = ?",
                UUID.class,
                existingEventId
        );

        EventActivityDTO newActivity = createActivityDTO(
                "New Activity", "Desc", "Room",
                LocalTime.of(12, 0), LocalTime.of(13, 0)
        );

        EventActivitiesDTO request = new EventActivitiesDTO();
        request.setEventId(existingEventId);
        request.setEventActivities(List.of(newActivity));

        ResponseEntity<Void> response = putWithAuth(
                request,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        for (UUID id : originalIds) {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM event_activity WHERE id = ?",
                    Integer.class,
                    id
            );
            assertEquals(0, count, "Old activity should be deleted");
        }

        Integer newCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM event_activity WHERE event_id = ?",
                Integer.class,
                existingEventId
        );
        assertEquals(1, newCount);
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
