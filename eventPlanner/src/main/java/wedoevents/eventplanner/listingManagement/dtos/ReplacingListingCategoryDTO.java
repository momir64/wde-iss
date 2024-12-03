package wedoevents.eventplanner.listingManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.util.UUID;

@Getter
@Setter
public class ReplacingListingCategoryDTO {
    private UUID toBeReplacedId;
    private UUID replacingId;
    private ListingType listingType;
}
