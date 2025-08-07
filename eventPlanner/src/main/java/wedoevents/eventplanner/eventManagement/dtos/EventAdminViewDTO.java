package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EventAdminViewDTO {
    UUID id;
    String name;
    String city;
    long attendance;
    Double rating;
}
