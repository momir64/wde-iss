package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EventNameDTO {
    private UUID id;
    private String name;

    public EventNameDTO(Event event) {
        id = event.getId();
        name = event.getName();
    }
}
