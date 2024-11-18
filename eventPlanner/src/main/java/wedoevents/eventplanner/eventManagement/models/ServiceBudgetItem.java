package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue
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