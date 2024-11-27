package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ServiceBudgetItemDTO {
    private UUID id;
    private UUID eventId;

    private UUID serviceCategoryId;
    private double maxPrice;

    private UUID serviceId;
    private int serviceVersion;
}