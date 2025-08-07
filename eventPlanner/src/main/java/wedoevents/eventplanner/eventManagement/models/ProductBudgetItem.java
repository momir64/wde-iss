package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
public class ProductBudgetItem {

    @Id
    @GeneratedValue()
    private UUID id;

    private double maxPrice;

    @ManyToOne(optional = false)
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "versioned_product_static_product_id"),
            @JoinColumn(name = "versioned_product_version")
    })
    private VersionedProduct product; // nullable product
}