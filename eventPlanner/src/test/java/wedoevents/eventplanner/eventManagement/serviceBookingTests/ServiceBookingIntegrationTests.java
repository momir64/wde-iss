package wedoevents.eventplanner.eventManagement.serviceBookingTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import wedoevents.eventplanner.eventManagement.dtos.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceBookingIntegrationTests {
    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public void setUpDatabase() {
        // referential integrity is turned off, because H2 sees inserting of
        // primary keys that are set to GENERATED_VALUE (the default) as
        // a violation of referential integrity, and in our file,
        // all primary keys are predefined, to be able to connect entities
        // referential integrity is never broken because this same script is
        // used in the PostgreSQL database where referential integrity is turned on
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TO FALSE");
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
    void testReserveServiceWhileHavingServiceBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("6b351a75-3061-4d96-8856-d58f1576a568");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(eventId, serviceCategoryId, 1000.0);
        ResponseEntity<ServiceBudgetItemDTO> r1 = rest.postForEntity("/api/v1/service-budget-items", new HttpEntity<>(hasCategoryPath), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r1.getStatusCode());

        ServiceBudgetItemDTO createdSBI = r1.getBody();
        assertNotNull(createdSBI);

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:30");

        ResponseEntity<ServiceBudgetItemDTO> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r2.getStatusCode());

        ServiceBudgetItemDTO sbiWithService = r2.getBody();
        assertNotNull(sbiWithService);

        assertEquals(versionedServiceId, sbiWithService.getServiceId());
        assertEquals(1, sbiWithService.getServiceVersion());
        assertEquals(1000.0, sbiWithService.getMaxPrice());
        assertEquals(serviceCategoryId, sbiWithService.getServiceCategoryId());
        assertEquals(sbiWithService.getStartTime(), LocalDateTime.of(2025, 10, 31, 0, 0, 0));
        assertEquals(sbiWithService.getEndTime(), LocalDateTime.of(2025, 10, 31, 0, 30, 0));
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testReserveServiceWithoutServiceBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("6b351a75-3061-4d96-8856-d58f1576a568");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:30");

        ResponseEntity<ServiceBudgetItemDTO> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r2.getStatusCode());

        ServiceBudgetItemDTO sbiWithService = r2.getBody();
        assertNotNull(sbiWithService);

        assertEquals(versionedServiceId, sbiWithService.getServiceId());
        assertEquals(1, sbiWithService.getServiceVersion());
        assertEquals(0.0, sbiWithService.getMaxPrice());
        assertEquals(serviceCategoryId, sbiWithService.getServiceCategoryId());
        assertEquals(sbiWithService.getStartTime(), LocalDateTime.of(2025, 10, 31, 0, 0, 0));
        assertEquals(sbiWithService.getEndTime(), LocalDateTime.of(2025, 10, 31, 0, 30, 0));
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testReserveServiceWhileHavingServiceBudgetItemButPriceTooMuch() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("6b351a75-3061-4d96-8856-d58f1576a568");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(eventId, serviceCategoryId, 500.0);

        ResponseEntity<ServiceBudgetItemDTO> r1 = rest.postForEntity("/api/v1/service-budget-items", new HttpEntity<>(hasCategoryPath), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r1.getStatusCode());

        ServiceBudgetItemDTO createdSBI = r1.getBody();
        assertNotNull(createdSBI);

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:30");

        ResponseEntity<String> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.FORBIDDEN, r2.getStatusCode());
        assertEquals("Service too expensive", r2.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testDeletePlannedServiceBudgetItemWithReservedService() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("6b351a75-3061-4d96-8856-d58f1576a568");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:30");

        ResponseEntity<ServiceBudgetItemDTO> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r2.getStatusCode());

        ServiceBudgetItemDTO sbiWithService = r2.getBody();
        assertNotNull(sbiWithService);

        ResponseEntity<String> r3 = rest.exchange("/api/v1/service-budget-items/" + eventId + "/" + serviceCategoryId, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, r3.getStatusCode());
        assertEquals("Service category has reserved services", r3.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testReserveServiceForCategoryThatAlreadyHasReservedService() {
        // reserve the service "Event Photography"
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto1 = new BuyServiceDTO();
        dto1.setEventId(eventId);
        dto1.setServiceId(versionedServiceId);
        dto1.setStartTime("00:00");
        dto1.setEndTime("00:30");

        ResponseEntity<ServiceBudgetItemDTO> r1 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto1), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r1.getStatusCode());

        // try to reserve "Family Photography"
        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(UUID.fromString("9447c5d5-3a82-44e2-8fe3-5d836f0eda63"));
        dto.setStartTime("00:00");
        dto.setEndTime("00:30");

        ResponseEntity<String> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.FORBIDDEN, r2.getStatusCode());
        assertEquals("Service budget item for that event and category is already booked", r2.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testReserveServiceWithBelowMinimalDuration() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:20"); // minimum for "Event Photography" is 30 minutes

        ResponseEntity<String> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.FORBIDDEN, r2.getStatusCode());
        assertEquals("The service's reservation must be at least 30 minutes", r2.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testReserveServiceWithAboveMaximalDuration() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("03:00"); // maximum for "Event Photography" is 150 minutes

        ResponseEntity<String> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.FORBIDDEN, r2.getStatusCode());
        assertEquals("The service's reservation must be at most 150 minutes", r2.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testGetServiceSlotsAfterReservation() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b20ud2lsbGlhbXNAZXhhbXBsZS5jb20iLCJwcm9maWxlSWQiOiIwY2QxM2Y0ZS1mN2RlLTQ1MzMtOTA3MS1jNDJiN2IzYjRkNDUiLCJyb2xlcyI6WyJPUkdBTklaRVIiXSwidXNlcklkIjoiMWQ4MzJhNmUtN2IzZi00Y2Q0LWJjMzctZmFjM2UwZWY5MjM2IiwiaWF0IjoxNzU0NDAxMDE3LCJleHAiOjE3NTg2OTU5ODR9.LYm65PBwscutsEoHLKuwIFUES9xJggHSUn1N0HV7Xz0A_Lgzjv3pzagFO0AVjcqq9GNlqLD8YeLsFb6DtVjWTg";
        // jwt of tom.williams@example.com (lasts a month), that is the event organizer of the event in question

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:30");

        ResponseEntity<ServiceBudgetItemDTO> r1 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r1.getStatusCode());

        rest.getRestTemplate().getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setBearerAuth(jwt);
            return execution.execute(request, body);
        });

        ResponseEntity<List<BookingSlotsDTO>> r2 = rest.exchange("/api/v1/service-budget-items/" + versionedServiceId + "/slots", HttpMethod.GET,
                                                                 new HttpEntity<>(null, new HttpHeaders()), new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, r2.getStatusCode());
        assertNotNull(r2.getBody());
        assertTrue(r2.getBody().stream().filter(slot -> slot.getEvent().getId().equals(eventId)).findFirst().isEmpty());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testReserveOverlappingService() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto1 = new BuyServiceDTO();
        dto1.setEventId(eventId);
        dto1.setServiceId(versionedServiceId);
        dto1.setStartTime("00:00");
        dto1.setEndTime("00:30");

        ResponseEntity<ServiceBudgetItemDTO> r1 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto1), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r1.getStatusCode());

        UUID versionedServiceId2 = UUID.fromString("daa22294-5377-487a-aa3f-7cd5a42cc568");

        BuyServiceDTO dto2 = new BuyServiceDTO();
        dto2.setEventId(eventId);
        dto2.setServiceId(versionedServiceId2);
        dto2.setStartTime("00:00"); // overlaps
        dto2.setEndTime("00:15"); // with the previous buy

        ResponseEntity<ServiceBudgetItemDTO> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto2), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r2.getStatusCode()); // successful overlapped reservation
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testReserveTwoServicesAtTheSameTimeForTheSameEvent() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto1 = new BuyServiceDTO();
        dto1.setEventId(eventId);
        dto1.setServiceId(versionedServiceId);
        dto1.setStartTime("00:00");
        dto1.setEndTime("00:30");

        ResponseEntity<ServiceBudgetItemDTO> r1 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto1), ServiceBudgetItemDTO.class);
        assertEquals(HttpStatus.OK, r1.getStatusCode());

        BuyServiceDTO dto2 = new BuyServiceDTO();
        dto2.setEventId(eventId);
        dto2.setServiceId(versionedServiceId);
        dto2.setStartTime("00:00"); // overlaps
        dto2.setEndTime("00:30"); // with the previous buy, but the same service, so should not be possible

        ResponseEntity<String> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto2), String.class);
        assertEquals(HttpStatus.FORBIDDEN, r2.getStatusCode());
        assertEquals("Service is unavailable at that time", r2.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyServiceForEventThatDoesntExist() {
        UUID eventThatDoesntExist = UUID.fromString("0000000-0000-0000-0000-00000000000");
        UUID versionedServiceId = UUID.fromString("9ee88634-aa10-48d1-b2c4-98556eac1684");

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventThatDoesntExist);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:30");

        ResponseEntity<String> r = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyServiceForEventWhoseEventTypeIsNotInServiceAvailableEventTypes() {
        UUID eventId = UUID.fromString("57787b97-680f-436f-95e4-9690357472e4"); // "Techno Party" event type
        UUID versionedServiceId = UUID.fromString("8fb67698-2344-4b1d-950e-478c14f477cd"); // "Videography" category

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:00");

        ResponseEntity<String> r = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.FORBIDDEN, r.getStatusCode());
        assertEquals("Event type not in event's available event types", r.getBody());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyNotExistingService() {
        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
        UUID versionedServiceId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:00");

        ResponseEntity<String> r = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "db3fc51a-0775-44ba-b031-955503ed74d1",
            "f6bb3ca6-cd9c-42dc-9fe0-fd0b84dd79ca",
            "96997487-e316-46f0-8868-d795b80157ba",
    })
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyServiceButNotVisibleService(String serviceId) {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");

        // at least one visibility boolean for these services is false
        UUID versionedServiceId = UUID.fromString(serviceId);

        BuyServiceDTO dto = new BuyServiceDTO();
        dto.setEventId(eventId);
        dto.setServiceId(versionedServiceId);
        dto.setStartTime("00:00");
        dto.setEndTime("00:00");

        ResponseEntity<String> r2 = rest.postForEntity("/api/v1/service-budget-items/buy", new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.FORBIDDEN, r2.getStatusCode());
    }
}