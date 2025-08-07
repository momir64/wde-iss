package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;

import java.util.List;
import java.util.UUID;
@Setter
@Getter
public class ExtendedEventTypeDTO {
    private UUID id;
    private String name;
    private String description;
    private Boolean isActive;
    private List<ListingCategoryDTO> listingCategories;
}
