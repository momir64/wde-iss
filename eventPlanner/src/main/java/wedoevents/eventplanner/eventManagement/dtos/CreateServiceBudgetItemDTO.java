package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreateServiceBudgetItemDTO {
    private UUID serviceCategoryId;
    private Double maxPrice;
}