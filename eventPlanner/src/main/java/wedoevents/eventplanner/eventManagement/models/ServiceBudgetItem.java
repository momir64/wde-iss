package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;

import java.util.UUID;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class ServiceBudgetItem {
    @Id
    @GeneratedValue
    private UUID id;

    private double maxPrice;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    private ServiceCategory serviceCategory;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "versioned_service_static_service_id"),
            @JoinColumn(name = "versioned_service_version")
    })
    private VersionedService service; // nullable Service
}