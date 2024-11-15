package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class EventTypeDTO {
    private UUID id;

    private String name;
    private String description;
    private Boolean isActive;
    private List<UUID> recommendedProductCategoryIds;
    private List<UUID> recommendedServiceCategoryIds;
}
