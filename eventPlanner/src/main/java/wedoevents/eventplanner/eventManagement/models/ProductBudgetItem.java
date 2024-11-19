package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.productManagement.models.Product;
import wedoevents.eventplanner.productManagement.models.ProductCategory;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
public class ProductBudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private double maxPrice;
    private int productVersion;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    private ProductCategory productCategory;

    @ManyToOne
    private Product product; // nullable product
}