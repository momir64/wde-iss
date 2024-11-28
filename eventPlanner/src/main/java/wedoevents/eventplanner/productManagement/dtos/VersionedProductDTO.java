package wedoevents.eventplanner.productManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class VersionedProductDTO {
    private UUID staticProductId;
    private Integer version;
    private String name;
    private Double price;
    private List<String> images;
    private Double salePercentage;
    private Boolean isActive;
    private Boolean isAvailable;
    private Boolean isPrivate;
    private UUID productCategoryId;
    private List<UUID> availableEventTypesIds;

    public static VersionedProductDTO toDto(VersionedProduct versionedProduct) {
        return new VersionedProductDTO(
                versionedProduct.getStaticProductId(),
                versionedProduct.getVersion(),
                versionedProduct.getName(),
                versionedProduct.getPrice(),
                versionedProduct.getImages(),
                versionedProduct.getSalePercentage(),
                versionedProduct.getIsActive(),
                versionedProduct.getIsAvailable(),
                versionedProduct.getIsPrivate(),
                versionedProduct.getStaticProduct().getProductCategory().getId(),
                versionedProduct.getAvailableEventTypes().stream().map(EventType::getId).toList()
        );
    }
}
