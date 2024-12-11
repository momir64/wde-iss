package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class CreateProductBudgetItemDTO {
    private UUID eventId;
    private UUID productCategoryId;
    private Double maxPrice;
}
