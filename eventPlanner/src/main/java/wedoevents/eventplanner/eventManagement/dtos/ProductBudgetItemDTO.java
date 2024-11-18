package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ProductBudgetItemDTO {
    private UUID id;

    private double maxPrice;
    private int productVersion;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private UUID productCategoryId;
    private UUID productId;
}
