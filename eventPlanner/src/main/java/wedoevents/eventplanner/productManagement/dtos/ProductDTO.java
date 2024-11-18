package wedoevents.eventplanner.productManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDTO {
    private UUID id;

    private String name;
    private UUID price;
    private List<String> images;
    private double sale;
    private Boolean isActive;
    private Boolean isAvailable;
    private Boolean isPrivate;
    private int version;

    private UUID productCategoryId;

    private List<UUID> availableEventTypesIds;
}
