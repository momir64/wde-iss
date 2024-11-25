package wedoevents.eventplanner.productManagement.models;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VersionedProductId implements Serializable {
    @Column(name = "static_product_id", columnDefinition = "uuid")
    private UUID staticProductId;
    private Integer version;
}
