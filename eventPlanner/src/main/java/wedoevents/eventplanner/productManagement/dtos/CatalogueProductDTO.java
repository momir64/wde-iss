package wedoevents.eventplanner.productManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogueProductDTO {
    private UUID productId;
    private String name;
    private Double price;
    private Double salePercentage;

    public static CatalogueProductDTO toDto(VersionedProduct versionedProduct) {
        return new CatalogueProductDTO(
                versionedProduct.getStaticProductId(),
                versionedProduct.getName(),
                versionedProduct.getPrice(),
                versionedProduct.getSalePercentage()
        );
    }
}
