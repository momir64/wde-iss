package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class Location {

    @Id
    @GeneratedValue
    private UUID id;

    private double longitude;
    private double latitude;
}