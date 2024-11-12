package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Entity
public class EventType {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "eventtype_productcategory",
            joinColumns = @JoinColumn(name = "eventtype_id"),
            inverseJoinColumns = @JoinColumn(name = "productcategory_id")
    )
    private List<ProductCategory> recommendedProductCategories;

    @ManyToMany
    @JoinTable(
            name = "eventtype_servicecategory",
            joinColumns = @JoinColumn(name = "eventtype_id"),
            inverseJoinColumns = @JoinColumn(name = "servicecategory_id")
    )
    private List<ServiceCategory> recommendedServiceCategories;


}