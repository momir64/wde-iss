package wedoevents.eventplanner.productManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductCategoryDTO {
    private UUID id;

    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;
}
