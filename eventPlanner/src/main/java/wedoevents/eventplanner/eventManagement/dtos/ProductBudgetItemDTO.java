package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ProductBudgetItemDTO {
    private UUID id;
    private UUID eventId;

    private UUID productCategoryId;
    private Double maxPrice;

    private UUID productId;
    private Double productVersion;
}
