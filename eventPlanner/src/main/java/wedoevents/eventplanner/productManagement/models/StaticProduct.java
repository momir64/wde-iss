package wedoevents.eventplanner.productManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
@Entity
public class StaticProduct {

    @Id
    @GeneratedValue()
    @Column(name = "static_product_id")
    private UUID staticProductId;

    @ManyToOne
    private ProductCategory productCategory;
}