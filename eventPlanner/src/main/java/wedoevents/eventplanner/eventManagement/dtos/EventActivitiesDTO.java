package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventActivitiesDTO {
    private List<EventActivityDTO> eventActivities;
}
