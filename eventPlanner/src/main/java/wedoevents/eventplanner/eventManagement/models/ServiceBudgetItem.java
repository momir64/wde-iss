package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
public class ServiceBudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private double maxPrice;
    private int serviceVersion;
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