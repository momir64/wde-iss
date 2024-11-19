package wedoevents.eventplanner.serviceManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class ServiceCategory {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;
}
