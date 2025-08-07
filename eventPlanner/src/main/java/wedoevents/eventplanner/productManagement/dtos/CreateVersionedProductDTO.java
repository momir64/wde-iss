package wedoevents.eventplanner.productManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateVersionedProductDTO {
    private UUID productCategoryId;
    private String suggestedCategory;
    private String suggestedCategoryDescription;

    private UUID sellerId;

    private String name;
    private Double salePercentage;
    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private Double price;

    private List<UUID> availableEventTypeIds;
}
