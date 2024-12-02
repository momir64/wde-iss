package wedoevents.eventplanner.productManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;

    public ProductCategory(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductCategory(){}
}
