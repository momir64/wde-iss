package wedoevents.eventplanner.eventManagement.eventMangementTests.backendTests.AgendaEditingTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import wedoevents.eventplanner.eventManagement.dtos.EventActivitiesDTO;
import wedoevents.eventplanner.eventManagement.dtos.EventActivityDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;
import wedoevents.eventplanner.eventManagement.repositories.EventActivityRepository;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.services.EventService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AgendaEditingServiceTests {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventActivityRepository eventActivityRepository;

    @InjectMocks
    private EventService service;

    private UUID eventId = UUID.randomUUID();
    private Event existingEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup existing event with activities
        existingEvent = new Event();
        existingEvent.setId(eventId);
        existingEvent.setTime(LocalTime.of(9, 0));

        EventActivity existingActivity1 = new EventActivity();
        existingActivity1.setId(UUID.randomUUID());
        existingActivity1.setStartTime(LocalTime.of(9, 0));
        existingActivity1.setEndTime(LocalTime.of(10, 0));

        EventActivity existingActivity2 = new EventActivity();
        existingActivity2.setId(UUID.randomUUID());
        existingActivity2.setStartTime(LocalTime.of(10, 0));
        existingActivity2.setEndTime(LocalTime.of(11, 0));

        existingEvent.setEventActivities(Arrays.asList(existingActivity1, existingActivity2));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Stub activity saving to return new UUIDs
        when(eventActivityRepository.save(any(EventActivity.class)))
                .thenAnswer(invocation -> {
                    EventActivity ea = invocation.getArgument(0);
                    ea.setId(UUID.randomUUID());
                    return ea;
                });
    }

    @Test
    void whenEventNotFound_thenReturnsFalse() {
        UUID nonExistentId = UUID.randomUUID();
        when(eventRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(nonExistentId);

        assertFalse(service.updateAgenda(dto));
        verify(eventRepository, never()).save(any());
        verify(eventActivityRepository, never()).save(any());
    }

    @Test
    void whenInvalidActivityTimes_thenThrowsAndPreservesOldActivities() {
        EventActivityDTO invalid = createActivityDTO("Invalid", LocalTime.of(12,0), LocalTime.of(11,0));
        EventActivitiesDTO dto = createAgendaDTO(eventId, invalid);

        assertThatThrownBy(() -> service.updateAgenda(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not before end time");

        // Verify no changes persisted
        assertEquals(2, existingEvent.getEventActivities().size());
        verify(eventRepository, never()).save(any());
        verify(eventActivityRepository, never()).save(any());
    }

    @Test
    void whenNonSequentialActivities_thenThrowsAndPreservesState() {
        EventActivityDTO a1 = createActivityDTO("A1", LocalTime.of(13,0), LocalTime.of(14,0));
        EventActivityDTO a2 = createActivityDTO("A2", LocalTime.of(14,15), LocalTime.of(15,0)); // Gap
        EventActivitiesDTO dto = createAgendaDTO(eventId, a1, a2);

        assertThatThrownBy(() -> service.updateAgenda(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("must start at");

        assertEquals(2, existingEvent.getEventActivities().size());
        verify(eventRepository, never()).save(any());
    }



    @Test
    void whenActivitySaveFails_thenRollsBackTransaction() {
        EventActivityDTO valid = createActivityDTO("Valid", LocalTime.of(13,0), LocalTime.of(14,0));
        EventActivitiesDTO dto = createAgendaDTO(eventId, valid);

        // Fail on second save attempt
        doThrow(new RuntimeException("DB error"))
                .when(eventActivityRepository).save(any(EventActivity.class));

        assertThrows(RuntimeException.class, () -> service.updateAgenda(dto));

        // Verify partial changes were rolled back
        assertEquals(2, existingEvent.getEventActivities().size());
        verify(eventRepository, never()).save(any());
    }

    // Helper methods
    private EventActivityDTO createActivityDTO(String name, LocalTime start, LocalTime end) {
        EventActivityDTO dto = new EventActivityDTO();
        dto.setName(name);
        dto.setDescription("Desc " + name);
        dto.setLocation("Loc " + name);
        dto.setStartTime(start);
        dto.setEndTime(end);
        return dto;
    }

    private EventActivitiesDTO createAgendaDTO(UUID eventId, EventActivityDTO... activities) {
        EventActivitiesDTO dto = new EventActivitiesDTO();
        dto.setEventId(eventId);
        dto.setEventActivities(Arrays.asList(activities));
        return dto;
    }

}
