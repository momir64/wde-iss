package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreateProductBudgetItemDTO {
    private UUID productCategoryId;
    private Double maxPrice;
}
