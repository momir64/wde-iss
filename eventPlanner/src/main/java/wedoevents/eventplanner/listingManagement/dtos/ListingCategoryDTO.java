package wedoevents.eventplanner.listingManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.util.UUID;

@Getter
@Setter
public class ListingCategoryDTO {
    private UUID id;

    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;
    private ListingType listingType;
}
