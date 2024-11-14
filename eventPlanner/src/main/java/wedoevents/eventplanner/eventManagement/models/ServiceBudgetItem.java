package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceEntity;

import java.util.UUID;

@Setter
@Getter
@Entity
public class ServiceBudgetItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private ServiceEntity service; // nullable Service

    @ManyToOne(optional = false)
    private ServiceCategory serviceCategory;

    private Long maxPrice;
    private Integer serviceVersion;
}