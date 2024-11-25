package wedoevents.eventplanner.productManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateVersionedProductDTO {
    private UUID productCategoryId;
    private String name;
    private List<String> images;
    private Double price;
    private Double salePercentage;
    private Boolean isActive;
    private Boolean isAvailable;
    private Boolean isPrivate;
    private List<UUID> availableEventTypeIds;
}
