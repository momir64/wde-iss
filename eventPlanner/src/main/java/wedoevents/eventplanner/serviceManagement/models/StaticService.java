package wedoevents.eventplanner.serviceManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class StaticService {

    @Id
    @GeneratedValue()
    @Column(name = "static_service_id")
    private UUID staticServiceId;

    @ManyToOne
    private ServiceCategory serviceCategory;

    private Boolean pending; // todo pending on detailed view pages
}
