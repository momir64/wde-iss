package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class CreateServiceBudgetItemDTO {
    private UUID eventId;
    private UUID serviceCategoryId;
    private Double maxPrice;
}