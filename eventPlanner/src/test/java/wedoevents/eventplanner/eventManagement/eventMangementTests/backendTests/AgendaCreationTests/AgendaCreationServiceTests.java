package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests.AgendaCreationTests;
import org.junit.jupiter.api.*;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import wedoevents.eventplanner.eventManagement.dtos.EventActivitiesDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventActivityDTO;
import wedoevents.eventplanner.eventManagement.models.EventActivity;
import wedoevents.eventplanner.eventManagement.repositories.EventActivityRepository;
import wedoevents.eventplanner.eventManagement.services.EventService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AgendaCreationServiceTests {
    @Mock
    private EventActivityRepository repository;

    @InjectMocks
    private EventService service;

    private UUID id1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private UUID id2 = UUID.fromString("22222222-2222-2222-2222-222222222222");


    @BeforeAll
    public void setUpClass() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        // stub .save(...) to assign IDs in order of invocation
        when(repository.save(any(EventActivity.class)))
                .thenAnswer(invocation -> {
                    EventActivity entity = invocation.getArgument(0);
                    // pick ID based on startTime to simulate two different saves
                    if (entity.getStartTime().equals(LocalTime.of(9, 0))) {
                        entity.setId(id1);
                    } else {
                        entity.setId(id2);
                    }
                    return entity;
                });
    }

    @Test
    void  testAgendaCreationWithUnsortedActivities() {
        EventActivityDTO second = new EventActivityDTO();
        second.setName("Session B");
        second.setDescription("Desc B");
        second.setLocation("Room 2");
        second.setStartTime(LocalTime.of(10, 0));
        second.setEndTime(LocalTime.of(11, 0));

        EventActivityDTO first = new EventActivityDTO();
        first.setName("Session A");
        first.setDescription("Desc A");
        first.setLocation("Room 1");
        first.setStartTime(LocalTime.of(9, 0)); // earlier time
        first.setEndTime(LocalTime.of(10, 0));

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(Arrays.asList(second, first)); // reversed order

        List<UUID> result = service.createAgenda(dto);


        // verify repository.save was called twice, in correct order
        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository).save(argThat(e -> e.getStartTime().equals(LocalTime.of(9, 0))));
        inOrder.verify(repository).save(argThat(e -> e.getStartTime().equals(LocalTime.of(10, 0))));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void testAgendaCreationSameStartAndEndTime() {
        EventActivityDTO bad = new EventActivityDTO();
        bad.setName("Bad session");
        bad.setDescription("Desc");
        bad.setLocation("Room X");
        bad.setStartTime(LocalTime.of(12, 0));
        bad.setEndTime(LocalTime.of(12, 0)); // same as start

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(List.of(bad));

        assertThatThrownBy(() -> service.createAgenda(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not before end time");

        verify(repository, never()).save(any());
    }

    @Test
    void testAgendaCreationEndTimeBeforeStartTime() {
        EventActivityDTO bad = new EventActivityDTO();
        bad.setName("Bad session");
        bad.setDescription("Desc");
        bad.setLocation("Room X");
        bad.setStartTime(LocalTime.of(12, 0));
        bad.setEndTime(LocalTime.of(11, 0)); // before start

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(List.of(bad));

        assertThatThrownBy(() -> service.createAgenda(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not before end time");

        verify(repository, never()).save(any());
    }

    @Test
    void testAgendaCreationWithGaps() {
        EventActivityDTO a1 = new EventActivityDTO();
        a1.setName("Morning");
        a1.setDescription("Desc");
        a1.setLocation("A");
        a1.setStartTime(LocalTime.of(8, 0));
        a1.setEndTime(LocalTime.of(9, 0));

        EventActivityDTO a2 = new EventActivityDTO();
        a2.setName("Gap");
        a2.setDescription("Desc");
        a2.setLocation("B");
        a2.setStartTime(LocalTime.of(9, 15));  // gap of 15 mins
        a2.setEndTime(LocalTime.of(10, 15));

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(List.of(a1, a2));

        assertThatThrownBy(() -> service.createAgenda(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("must start at");
    }

    @Test
    void testAgendaCreationWithEmptyActivityList() {
        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(Collections.emptyList());

        List<UUID> result = service.createAgenda(dto);

        assertTrue("Result should be empty", result.isEmpty());
        verify(repository, never()).save(any());
    }

    @Test
    void testAgendaCreationWithSingleActivity() {
        EventActivityDTO activity = new EventActivityDTO();
        activity.setName("Single Activity");
        activity.setDescription("Desc");
        activity.setLocation("Room X");
        activity.setStartTime(LocalTime.of(9, 0));
        activity.setEndTime(LocalTime.of(10, 0));

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(List.of(activity));

        List<UUID> result = service.createAgenda(dto);

        assertEquals(1, result.size());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testAgendaCreationWithOverlappingActivities() {
        EventActivityDTO first = new EventActivityDTO();
        first.setName("Morning Session");
        first.setDescription("Desc A");
        first.setLocation("Room 1");
        first.setStartTime(LocalTime.of(9, 0));
        first.setEndTime(LocalTime.of(10, 30));  // overlaps with next

        EventActivityDTO second = new EventActivityDTO();
        second.setName("Workshop");
        second.setDescription("Desc B");
        second.setLocation("Room 2");
        second.setStartTime(LocalTime.of(10, 0));  // starts before first ends
        second.setEndTime(LocalTime.of(11, 0));

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(List.of(first, second));

        assertThatThrownBy(() -> service.createAgenda(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("must start at");
    }

    @Test
    void testAgendaCreationUnorderedActivities() {
        EventActivityDTO second = new EventActivityDTO();
        second.setName("Session B");
        second.setDescription("Desc B");
        second.setLocation("Room 2");
        second.setStartTime(LocalTime.of(10, 0));
        second.setEndTime(LocalTime.of(11, 0));

        EventActivityDTO first = new EventActivityDTO();
        first.setName("Session A");
        first.setDescription("Desc A");
        first.setLocation("Room 1");
        first.setStartTime(LocalTime.of(9, 0));
        first.setEndTime(LocalTime.of(10, 0));

        EventActivityDTO third = new EventActivityDTO();
        third.setName("Session C");
        third.setDescription("Desc C");
        third.setLocation("Room 3");
        third.setStartTime(LocalTime.of(11, 0));
        third.setEndTime(LocalTime.of(12, 0));

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(Arrays.asList(second, third, first));  // Out of order

        List<UUID> result = service.createAgenda(dto);

        // Verify three IDs returned
        assertEquals(3, result.size());

        // Verify save order matches sorted start times
        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository).save(argThat(e ->
                e.getStartTime().equals(LocalTime.of(9, 0))));
        inOrder.verify(repository).save(argThat(e ->
                e.getStartTime().equals(LocalTime.of(10, 0))));
        inOrder.verify(repository).save(argThat(e ->
                e.getStartTime().equals(LocalTime.of(11, 0))));
    }

    @Test
    void testAgendaCreationWithNullValues() {
        EventActivityDTO invalid = new EventActivityDTO();
        invalid.setName(null);
        invalid.setDescription("Desc");
        invalid.setLocation(null);
        invalid.setStartTime(null);
        invalid.setEndTime(LocalTime.of(10, 0));

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(List.of(invalid));

        assertThatThrownBy(() -> service.createAgenda(dto))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testAgendaCreationWhenActivitiesSpanTwoDays() {
        EventActivityDTO lateNight = new EventActivityDTO();
        lateNight.setName("Night Session");
        lateNight.setDescription("Desc");
        lateNight.setLocation("Main Hall");
        lateNight.setStartTime(LocalTime.of(23, 0));
        lateNight.setEndTime(LocalTime.of(1, 0));  // Next day

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setEventActivities(List.of(lateNight));

        // Should fail validation since start is after end
        assertThatThrownBy(() -> service.createAgenda(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not before end time");
    }

    @AfterEach
    void tearDown() {
        reset(repository);
    }
}
