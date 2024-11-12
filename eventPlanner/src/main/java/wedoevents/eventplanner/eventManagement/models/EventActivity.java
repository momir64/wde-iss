package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@Entity
public class EventActivity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

}