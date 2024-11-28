package wedoevents.eventplanner.productManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;

    private UUID price;

    @ElementCollection
    private List<String> images;

    private double sale;
    private Boolean isActive;
    private Boolean isAvailable;
    private Boolean isPrivate;
    private int version;

    @ManyToOne
    private ProductCategory productCategory;

    @ManyToMany
    @JoinTable(
            name = "product_eventtype",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "eventtype_id")
    )
    private List<EventType> availableEventTypes;
}