package wedoevents.eventplanner.productManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class VersionedProductForSellerDTO {
    private UUID staticProductId;
    private Integer version;
    private UUID productCategoryId;

    private String name;
    private Double salePercentage;
    private List<byte[]> images;
    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private Boolean isActive;
    private Double price;

    private List<UUID> availableEventTypeIds;

    public static VersionedProductForSellerDTO toDto(VersionedProduct versionedProduct, ImageService imageService) throws IOException {
        List<byte[]> images = new ArrayList<>();

        for (String image : versionedProduct.getImages()) {
            images.add(imageService.getImage(image, new ImageLocationConfiguration(
                    "product", versionedProduct.getStaticProductId(), versionedProduct.getVersion())
            ).get());
        }

        return new VersionedProductForSellerDTO(
                versionedProduct.getStaticProductId(),
                versionedProduct.getVersion(),
                versionedProduct.getStaticProduct().getProductCategory().getId(),
                versionedProduct.getName(),
                versionedProduct.getSalePercentage(),
                images,
                versionedProduct.getDescription(),
                versionedProduct.getIsPrivate(),
                versionedProduct.getIsAvailable(),
                versionedProduct.getIsActive(),
                versionedProduct.getPrice(),
                versionedProduct.getAvailableEventTypes().stream().map(EventType::getId).toList()
        );
    }
}
