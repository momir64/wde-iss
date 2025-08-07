package wedoevents.eventplanner.serviceManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;

    public ServiceCategory(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ServiceCategory() {}
}
