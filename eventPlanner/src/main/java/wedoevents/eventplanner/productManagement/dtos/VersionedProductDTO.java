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
    private UUID productCategoryId;

    private String name;
    private Double oldPrice;
    private List<String> images;
    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private Boolean isActive;
    private Double price;
    private List<UUID> availableEventTypeIds;
    private Double rating;

    public static VersionedProductDTO toDto(VersionedProduct versionedProduct) {
        return toDto(versionedProduct, 0.0);
    }

    public static VersionedProductDTO toDto(VersionedProduct versionedProduct, Double rating) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();
        return new VersionedProductDTO(
                versionedProduct.getStaticProductId(),
                versionedProduct.getVersion(),
                versionedProduct.getStaticProduct().getProductCategory().getId(),
                versionedProduct.getName(),
                versionedProduct.getSalePercentage() != null ? versionedProduct.getPrice() : null,
                versionedProduct.getImages()
                        .stream()
                        .map(uniqName -> String.format("%s/api/v1/products/%s/%d/images/%s", baseUrl, versionedProduct.getStaticProductId(), versionedProduct.getVersion(), uniqName))
                        .sorted()
                        .toList(),
                versionedProduct.getDescription(),
                versionedProduct.getIsPrivate(),
                versionedProduct.getIsAvailable(),
                versionedProduct.getIsActive(),
                versionedProduct.getSalePercentage() != null ? (1 - versionedProduct.getSalePercentage()) * versionedProduct.getPrice() : null,
                versionedProduct.getAvailableEventTypes().stream().map(EventType::getId).toList(),
                rating
        );
    }
}
