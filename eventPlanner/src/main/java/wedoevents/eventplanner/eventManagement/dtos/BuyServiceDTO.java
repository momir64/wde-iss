package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BuyServiceDTO {
    private UUID eventId;
    private UUID serviceBudgetItemId;
    private UUID serviceId;

    // todo parameters for service reservation like duration, ...
    // version isn't needed because you can only buy the latest version
}
