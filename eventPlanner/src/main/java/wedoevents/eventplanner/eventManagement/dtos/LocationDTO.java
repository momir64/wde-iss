package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LocationDTO {
    private UUID id;

    private double longitude;
    private double latitude;
}
