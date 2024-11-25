package wedoevents.eventplanner.productManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@IdClass(VersionedProductId.class)
public class VersionedProduct {
    @Id
    private UUID staticProductId;

    @Id
    private Integer version;

    private String name;

    private Double price;

    @ElementCollection
    private List<String> images;

    private Double salePercentage;
    private Boolean isActive;
    private Boolean isAvailable;
    private Boolean isPrivate;
    private Boolean isLastVersion;

    @MapsId("static_product_id")
    @JoinColumns({
            @JoinColumn(name = "static_product_id", referencedColumnName = "static_product_id")
    })
    @ManyToOne()
    private StaticProduct staticProduct;

    @ManyToMany
    @JoinTable(
            name = "versioned_product_eventtype",
            joinColumns = {@JoinColumn(name = "versioned_product_static_product_id"), @JoinColumn(name = "versioned_product_version")},
            inverseJoinColumns = @JoinColumn(name = "eventtype_id")
    )
    private List<EventType> availableEventTypes;

    public void incrementVersion() {
        this.version += 1;
    }
}
