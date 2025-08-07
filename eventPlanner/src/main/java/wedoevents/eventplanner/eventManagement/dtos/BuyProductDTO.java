package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductDTO {
    private UUID eventId;
    private UUID productBudgetItemId;
    private UUID productId;
    // version isn't needed because you can only buy the latest version
}
