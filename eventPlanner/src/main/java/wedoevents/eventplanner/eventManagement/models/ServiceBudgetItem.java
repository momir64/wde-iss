package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceEntity;

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
    private ServiceEntity service; // nullable Service
}