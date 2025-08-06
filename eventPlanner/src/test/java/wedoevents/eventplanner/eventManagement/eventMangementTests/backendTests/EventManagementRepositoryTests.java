package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@Sql("classpath:entity-insertion.sql")
@DataJpaTest
@ActiveProfiles("test")
public class EventManagementRepositoryTests {
    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testFindAllPublicEventsSuccessfully() {

        var allEvents = eventRepository.findAll();
        List<Event> actualPublicEvents = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getIsPublic()) {
                actualPublicEvents.add(event);
            }
        }

        var repositoryPublicEvents = eventRepository.findAllPublicEvents();


        assertEquals( 36, allEvents.size(), "There should be 36 events in the repository");
        assertFalse(repositoryPublicEvents.isEmpty(), "Repository should return non-empty list of public events");
        assertEquals(29, actualPublicEvents.size(), "Public events should contain 29 events");
        assertEquals(actualPublicEvents.size(),repositoryPublicEvents.size(), "Public events should also contain 29 events");
    }

    @Test
    void testGetRecommendedProductCategoriesSuccessfully() {
        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
        var event = eventRepository.findById(eventId);
        assertFalse(event.isEmpty(), "Event should exist in the repository");
        var actualEvent = event.get();
        var actualEventType = eventTypeRepository.findById(actualEvent.getEventType().getId());
        assertTrue(actualEventType.isPresent(), "Event type should exist in the repository");
        var actualRecommendedCategories = actualEventType.get().getRecommendedProductCategories();


        var recommendedProductCategories = eventTypeRepository.getRecommendedProductCategoriesByEventTypeId(actualEvent.getEventType().getId());

        assertEquals("a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832", actualEvent.getEventType().getId().toString(), "Event type ID should match the expected value");
        assertFalse(recommendedProductCategories.isEmpty(), "Recommended product categories should exist in the repository");
        assertEquals(actualRecommendedCategories.size(), recommendedProductCategories.size(), "Recommended product categories should match the event type's recommended categories");
        assertTrue(recommendedProductCategories.containsAll(actualEventType.get().getRecommendedProductCategories()), "Recommended product categories should match the event type's recommended categories");
    }



    @Test
    void testGetRecommendedServiceCategoriesSuccessfully() {
        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
        var event = eventRepository.findById(eventId);
        assertFalse(event.isEmpty(), "Event should exist in the repository");
        var actualEvent = event.get();
        var actualEventType = eventTypeRepository.findById(actualEvent.getEventType().getId());
        assertTrue(actualEventType.isPresent(), "Event type should exist in the repository");
        var actualRecommendedServiceCategories = actualEventType.get().getRecommendedServiceCategories();

        var recommendedServiceCategories = eventTypeRepository.getRecommendedServiceCategoriesByEventTypeId(actualEvent.getEventType().getId());

        assertEquals("a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832", actualEvent.getEventType().getId().toString(), "Event type ID should match the expected value");
        assertFalse(recommendedServiceCategories.isEmpty(), "Recommended service categories should exist in the repository");
        assertEquals(actualRecommendedServiceCategories.size(), recommendedServiceCategories.size(), "Recommended service categories should match the event type's recommended categories");
        assertTrue(recommendedServiceCategories.containsAll(actualRecommendedServiceCategories), "Recommended service categories should match the event type's recommended categories");
    }

}
