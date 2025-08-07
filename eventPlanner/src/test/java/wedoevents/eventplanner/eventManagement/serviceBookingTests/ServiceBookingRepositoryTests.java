package wedoevents.eventplanner.eventManagement.serviceBookingTests;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql("classpath:entity-insertion.sql")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class ServiceBookingRepositoryTests {
    @Autowired
    VersionedServiceRepository versionedServiceRepository;

    @Autowired
    ServiceBudgetItemRepository serviceBudgetItemRepository;

    @Autowired
    EventOrganizerRepository eventOrganizerRepository;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    EntityManager em;

    private static final UUID ORG_WITH_EVENTS = UUID.fromString("b38d716b-4d2a-4fd3-b18c-bfa128f24b99");
    private static final UUID ORG_WITHOUT_EVENTS = UUID.fromString("1109388b-c4ad-4ce2-b99e-57890d8e8ddd");
    private static final UUID MUSIC_STATIC_ID = UUID.fromString("daa22294-5377-487a-aa3f-7cd5a42cc568");
    private static final UUID INACTIVE_STATIC_ID = UUID.fromString("db3fc51a-0775-44ba-b031-955503ed74d1");
    private static final UUID EVENT_ID_SMITH_WEDDING = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
    private static final LocalDate EVENT_DAY_2025_05_15 = LocalDate.of(2025, 5, 15);
    private static final LocalDateTime EXISTING_START = LocalDateTime.of(2025, 5, 15, 15, 30);
    private static final LocalDateTime EXISTING_END = LocalDateTime.of(2025, 5, 15, 16, 30);

    @Test
    void returnsAllEventsForOrganiser() {
        List<Event> result = eventOrganizerRepository.getMyEventsById(ORG_WITH_EVENTS);

        Integer expected = jdbc.queryForObject("SELECT COUNT(*) FROM event WHERE event_organizer_id = ?", Integer.class, ORG_WITH_EVENTS);
        assertThat(result).hasSize(expected);

        assertThat(result).allSatisfy(e -> {
            boolean ownerMatch = jdbc.queryForObject("SELECT COUNT(*) FROM event WHERE id = ? AND event_organizer_id = ?", Integer.class, e.getId(), ORG_WITH_EVENTS) == 1;
            assertThat(ownerMatch).as("event %s belongs to organiser", e.getId()).isTrue();
        });

        assertThat(result).extracting(Event::getId).doesNotHaveDuplicates();
    }

    @Test
    void organiserExistsButHasNoEvents() {
        List<Event> result = eventOrganizerRepository.getMyEventsById(ORG_WITHOUT_EVENTS);
        assertThat(result).isEmpty();
    }

    @Test
    void organiserIdUnknown() {
        UUID unknown = UUID.randomUUID();
        List<Event> result = eventOrganizerRepository.getMyEventsById(unknown);
        assertThat(result).isEmpty();
    }

    @Test
    void nullIdReturnsEmptyList() {
        List<Event> result = eventOrganizerRepository.getMyEventsById(null);
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    void seesEventsPersistedInSameTest() {
        UUID newEventTypeId = UUID.randomUUID();
        jdbc.update("INSERT INTO event_type(id, name, description, is_active) VALUES (?,?,?,?)", newEventTypeId, "TEMP", "", true);

        EventType eventType = em.getReference(EventType.class, newEventTypeId);
        City beograd = em.getReference(City.class, "Beograd"); // already in seed data
        EventOrganizer organiser = em.find(EventOrganizer.class, ORG_WITH_EVENTS);

        Event e = new Event();
        e.setName("In-mem test event");
        e.setDate(java.time.LocalDate.of(2026, 1, 1));
        e.setTime(java.time.LocalTime.of(12, 0));
        e.setAddress("Here");
        e.setCity(beograd);
        e.setGuestCount(1);
        e.setIsPublic(true);
        e.setEventType(eventType);

        organiser.getMyEvents().add(e);

        em.persist(e);
        em.flush();
        UUID generatedId = e.getId();

        List<Event> events = eventOrganizerRepository.getMyEventsById(ORG_WITH_EVENTS);
        assertThat(events).extracting(Event::getId).contains(generatedId);

        UUID fk = jdbc.queryForObject("SELECT event_organizer_id FROM event WHERE id = ?", UUID.class, generatedId);
        assertThat(fk).isEqualTo(ORG_WITH_EVENTS);
    }

    @Test
    void neverLeaksEventsFromOtherOrganisers() {
        UUID OTHER_ORG = UUID.fromString("47c5fa7c-0d12-48e2-a4ed-9e4f441b383f");

        List<Event> eventsForA = eventOrganizerRepository.getMyEventsById(ORG_WITH_EVENTS);
        List<Event> eventsForB = eventOrganizerRepository.getMyEventsById(OTHER_ORG);

        assertThat(eventsForA).extracting(Event::getId).doesNotContainAnyElementsOf(eventsForB.stream().map(Event::getId).toList());

        assertOwnerMatches(eventsForA, ORG_WITH_EVENTS);
        assertOwnerMatches(eventsForB, OTHER_ORG);
    }

    private void assertOwnerMatches(List<Event> events, UUID expectedOwnerId) {
        events.forEach(ev -> {
            UUID actualOwner = jdbc.queryForObject("SELECT event_organizer_id FROM event WHERE id = ?", UUID.class, ev.getId());
            assertThat(actualOwner).as("Event %s belongs to organiser %s", ev.getId(), expectedOwnerId).isEqualTo(expectedOwnerId);
        });
    }

    @Test
    void returnsLatestVersionForStaticService() {
        Optional<VersionedService> opt = versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(MUSIC_STATIC_ID);

        assertThat(opt).isPresent().get()
                       .returns(1, VersionedService::getVersion)
                       .returns(MUSIC_STATIC_ID, VersionedService::getStaticServiceId)
                       .returns(true, VersionedService::getIsLastVersion);
    }

    @Test
    void returnsEvenIfInactive() {
        Optional<VersionedService> opt = versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(INACTIVE_STATIC_ID);

        assertThat(opt).isPresent();
        Boolean activeFlag = jdbc.queryForObject("SELECT is_active FROM versioned_service WHERE static_service_id = ? AND version = 1", Boolean.class, INACTIVE_STATIC_ID);
        assertThat(activeFlag).isFalse();
    }

    @Test
    void unknownStaticServiceIdReturnsEmpty() {
        Optional<VersionedService> opt = versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(UUID.randomUUID());
        assertThat(opt).isNotPresent();
    }

    @Test
    void nullParameterReturnsEmptyOptional() {
        assertThat(versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(null)).isNotPresent();
    }

    @Test
    void noRowMarkedLatestReturnsEmpty() {
        jdbc.update("UPDATE versioned_service SET is_last_version = false WHERE static_service_id = ?", MUSIC_STATIC_ID);
        Optional<VersionedService> opt = versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(MUSIC_STATIC_ID);
        assertThat(opt).isNotPresent();
    }

    @Test
    @Transactional
    void seesNewlyPersistedLatestVersionSameTx() {
        jdbc.update("UPDATE versioned_service SET is_last_version = false WHERE static_service_id = ?", MUSIC_STATIC_ID);

        VersionedService v2 = new VersionedService();
        v2.setStaticServiceId(MUSIC_STATIC_ID);
        v2.setVersion(2);
        v2.setName("AUTO-GEN v2");
        v2.setDescription("generated by test");
        v2.setIsLastVersion(true);
        v2.setIsActive(true);
        v2.setIsAvailable(true);
        v2.setIsConfirmationManual(false);
        v2.setIsPrivate(false);
        v2.setPrice(999.0);
        v2.setCancellationDeadline(5);
        v2.setReservationDeadline(10);
        v2.setMaximumDuration(60);
        v2.setMinimumDuration(30);
        em.persist(v2);
        em.flush();

        Optional<VersionedService> opt = versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(MUSIC_STATIC_ID);

        assertThat(opt).isPresent().get()
                       .returns(2, VersionedService::getVersion)
                       .returns(true, VersionedService::getIsLastVersion);
    }

    @Test
    void duplicateLatestFlagsThrowException() {
        VersionedService v99 = new VersionedService();
        v99.setStaticServiceId(MUSIC_STATIC_ID);
        v99.setVersion(99);
        v99.setName("conflict");
        v99.setDescription("conflict");
        v99.setIsLastVersion(true);
        v99.setIsActive(true);
        v99.setIsAvailable(true);
        v99.setIsConfirmationManual(false);
        v99.setIsPrivate(false);
        v99.setPrice(1.0);
        v99.setCancellationDeadline(1);
        v99.setReservationDeadline(1);
        v99.setMaximumDuration(1);
        v99.setMinimumDuration(1);
        em.persist(v99);
        em.flush();

        assertThatThrownBy(() -> versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(MUSIC_STATIC_ID))
                .isInstanceOf(org.springframework.dao.IncorrectResultSizeDataAccessException.class);
    }

    @Test
    void returnsItemsForServiceAndDaySorted() {
        jdbc.update("""
                    INSERT INTO service_budget_item (
                        id, start_time, end_time, max_price,
                        versioned_service_static_service_id, versioned_service_version,
                        service_category_id, event_id )
                    VALUES (?,?,?,?,?,?,?,?)
                    """,
                    UUID.randomUUID(),
                    LocalDateTime.of(2025, 5, 15, 15, 0),
                    LocalDateTime.of(2025, 5, 15, 16, 0),
                    450,
                    MUSIC_STATIC_ID,
                    1,
                    "a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3",
                    EVENT_ID_SMITH_WEDDING);

        List<ServiceBudgetItem> items = serviceBudgetItemRepository.getForServiceAndDay(MUSIC_STATIC_ID, EVENT_DAY_2025_05_15);

        assertThat(items).hasSize(2);
        assertThat(items).isSortedAccordingTo(Comparator.comparing(ServiceBudgetItem::getStartTime));
        items.forEach(i -> {
            assertThat(i.getService().getStaticServiceId()).isEqualTo(MUSIC_STATIC_ID);
            assertThat(i.getStartTime().toLocalDate()).isEqualTo(EVENT_DAY_2025_05_15);
        });
    }

    @Test
    void serviceHasNoItemsOnDayReturnsEmpty() {
        LocalDate otherDay = LocalDate.of(2025, 5, 16);
        List<ServiceBudgetItem> result = serviceBudgetItemRepository.getForServiceAndDay(MUSIC_STATIC_ID, otherDay);
        assertThat(result).isEmpty();
    }

    @Test
    void unknownServiceIdReturnsEmpty() {
        UUID unknown = UUID.randomUUID();
        List<ServiceBudgetItem> result = serviceBudgetItemRepository.getForServiceAndDay(unknown, EVENT_DAY_2025_05_15);
        assertThat(result).isEmpty();
    }

    @Test
    void rowsWithNullStartTimeAreExcluded() {
        jdbc.update("""
                    INSERT INTO service_budget_item (
                        id, start_time, end_time, max_price,
                        versioned_service_static_service_id, versioned_service_version,
                        service_category_id, event_id )
                    VALUES (?,?,?,?,?,?,?,?)
                    """,
                    UUID.randomUUID(),
                    null,
                    null,
                    999,
                    MUSIC_STATIC_ID,
                    1,
                    "a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3",
                    EVENT_ID_SMITH_WEDDING);

        List<ServiceBudgetItem> result = serviceBudgetItemRepository.getForServiceAndDay(MUSIC_STATIC_ID, EVENT_DAY_2025_05_15);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStartTime()).isNotNull();
    }

    @Test
    @Transactional
    void seesNewlyPersistedItemSameTransaction() {
        UUID newItemId = UUID.randomUUID();
        LocalDateTime newStart = LocalDateTime.of(2025, 5, 15, 17, 0);

        jdbc.update("""
                    INSERT INTO service_budget_item (
                        id, start_time, end_time, max_price,
                        versioned_service_static_service_id, versioned_service_version,
                        service_category_id, event_id )
                    VALUES (?,?,?,?,?,?,?,?)
                    """,
                    newItemId,
                    newStart,
                    newStart.plusHours(1),
                    200,
                    MUSIC_STATIC_ID,
                    1,
                    "a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3",
                    EVENT_ID_SMITH_WEDDING);

        List<ServiceBudgetItem> items = serviceBudgetItemRepository.getForServiceAndDay(MUSIC_STATIC_ID, EVENT_DAY_2025_05_15);

        assertThat(items).extracting(ServiceBudgetItem::getId).contains(newItemId);
    }

    @Test
    void neverLeaksItemsFromOtherServices() {
        UUID otherService = UUID.fromString("deca359b-9bfb-4b6f-bc24-3e509f595da4"); // Catering

        List<ServiceBudgetItem> cateringItems = serviceBudgetItemRepository.getForServiceAndDay(otherService, EVENT_DAY_2025_05_15);
        List<ServiceBudgetItem> musicItems = serviceBudgetItemRepository.getForServiceAndDay(MUSIC_STATIC_ID, EVENT_DAY_2025_05_15);

        assertThat(musicItems).noneMatch(i -> i.getService().getStaticServiceId().equals(otherService));
        assertThat(cateringItems).noneMatch(i -> i.getService().getStaticServiceId().equals(MUSIC_STATIC_ID));
    }

    @Test
    void nullParameterReturnsEmpty() {
        assertThat(serviceBudgetItemRepository.getForServiceAndDay(null, EVENT_DAY_2025_05_15)).isEmpty();
        assertThat(serviceBudgetItemRepository.getForServiceAndDay(MUSIC_STATIC_ID, null)).isEmpty();
    }

    @Test
    void overlapExactSameInterval() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_START, EXISTING_END);
        assertThat(r).isTrue();
    }

    @Test
    void overlapInsideInterval() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_START.plusMinutes(10), EXISTING_END.minusMinutes(10));
        assertThat(r).isTrue();
    }

    @Test
    void overlapLeftEdgeTouching() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_START, EXISTING_START.plusMinutes(5));
        assertThat(r).isTrue();
    }

    @Test
    void overlapRightEdgeTouching() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_END.minusMinutes(5), EXISTING_END);
        assertThat(r).isTrue();
    }

    @Test
    void overlapByStartOnly() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_START.minusMinutes(5), EXISTING_START.minusMinutes(1));
        assertThat(r).isFalse();
    }

    @Test
    void overlapByEndOnly() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_END.plusMinutes(1), EXISTING_END.plusMinutes(5));
        assertThat(r).isFalse();
    }

    @Test
    void intervalEnclosesExistingReturnsFalse() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_START.minusMinutes(30), EXISTING_END.plusMinutes(30));
        assertThat(r).isFalse();
    }

    @Test
    void intervalBeforeExisting() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_START.minusHours(2), EXISTING_START.minusHours(1));
        assertThat(r).isFalse();
    }

    @Test
    void intervalAfterExisting() {
        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, EXISTING_END.plusHours(1), EXISTING_END.plusHours(2));
        assertThat(r).isFalse();
    }

    @Test
    void unknownServiceReturnsFalse() {
        boolean r = serviceBudgetItemRepository.doesOverlap(UUID.randomUUID(), EXISTING_START, EXISTING_END);
        assertThat(r).isFalse();
    }

    @Test
    @Transactional
    void multipleRowsOverlapIfAnyMatches() {
        jdbc.update("""
                    INSERT INTO service_budget_item (
                        id, start_time, end_time, max_price,
                        versioned_service_static_service_id, versioned_service_version,
                        service_category_id, event_id )
                    VALUES (?,?,?,?,?,?,?,?)
                    """,
                    UUID.randomUUID(),
                    LocalDateTime.of(2025, 5, 15, 18, 0),
                    LocalDateTime.of(2025, 5, 15, 19, 0),
                    123,
                    MUSIC_STATIC_ID,
                    1,
                    "a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3",
                    EVENT_ID_SMITH_WEDDING);

        boolean r = serviceBudgetItemRepository.doesOverlap(MUSIC_STATIC_ID, LocalDateTime.of(2025, 5, 15, 18, 30), LocalDateTime.of(2025, 5, 15, 18, 45));
        assertThat(r).isTrue();
    }

}
