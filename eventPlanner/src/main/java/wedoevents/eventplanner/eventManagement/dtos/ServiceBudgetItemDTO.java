package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ServiceBudgetItemDTO {
    private UUID id;
    private UUID serviceCategoryId;
    private Double maxPrice;
    private UUID serviceId;
    private Integer serviceVersion;

    public static ServiceBudgetItemDTO toDto(ServiceBudgetItem serviceBudgetItem) {
        return new ServiceBudgetItemDTO (
                serviceBudgetItem.getId(),
                serviceBudgetItem.getServiceCategory().getId(),
                serviceBudgetItem.getMaxPrice(),
                serviceBudgetItem.getService() == null ? null : serviceBudgetItem.getService().getStaticServiceId(),
                serviceBudgetItem.getService() == null ? null : serviceBudgetItem.getService().getVersion()
        );
    }
}
