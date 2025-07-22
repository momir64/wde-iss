package wedoevents.eventplanner.eventManagement.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EventActivitiesDTO {
    @NotNull @Size(min = 1, message = "At least one activity is required")
    private List<@Valid EventActivityDTO> eventActivities;

    private UUID eventId;
}
