package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Location {
    private double longitude;
    private double latitude;
}