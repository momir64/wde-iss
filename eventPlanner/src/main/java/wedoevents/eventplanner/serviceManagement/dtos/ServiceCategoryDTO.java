package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ServiceCategoryDTO {
    private UUID id;

    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;
}
