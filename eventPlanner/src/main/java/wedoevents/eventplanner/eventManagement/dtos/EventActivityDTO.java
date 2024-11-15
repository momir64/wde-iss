package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class EventActivityDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
}
