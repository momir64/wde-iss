package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EventActivitiesDTO {
    private List<EventActivityDTO> eventActivities;
    private UUID eventId;
}
