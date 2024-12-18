package wedoevents.eventplanner.productManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class VersionedProductDTO {
    private UUID staticProductId;
    private Integer version;
    private String name;
    private String description;
    private Double price;
    private List<String> images;
    private Double oldPrice;
    private Boolean isActive;
    private Boolean isAvailable;
    private Boolean isPrivate;
    private UUID productCategoryId;
    private List<UUID> availableEventTypesIds;

    public static VersionedProductDTO toDto(VersionedProduct versionedProduct) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();

        return new VersionedProductDTO(
                versionedProduct.getStaticProductId(),
                versionedProduct.getVersion(),
                versionedProduct.getName(),
                versionedProduct.getDescription(),
                versionedProduct.getSalePercentage() != null ? (1 - versionedProduct.getSalePercentage()) * versionedProduct.getPrice() : null,
                versionedProduct.getImages()
                        .stream()
                        .map(uniqName -> String.format("%s/api/v1/products/%s/%d/images/%s", baseUrl, versionedProduct.getStaticProductId(), versionedProduct.getVersion(), uniqName))
                        .sorted()
                        .toList(),
                versionedProduct.getSalePercentage() != null ? versionedProduct.getPrice() : null,
                versionedProduct.getIsActive(),
                versionedProduct.getIsAvailable(),
                versionedProduct.getIsPrivate(),
                versionedProduct.getStaticProduct().getProductCategory().getId(),
                versionedProduct.getAvailableEventTypes().stream().map(EventType::getId).toList()
        );
    }
}
