package wedoevents.eventplanner.listingManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

@Getter
@Setter
public class CreateListingCategoryDTO {
    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;
    private ListingType listingType;
}
