package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.util.UUID;

@Getter
@Setter
public class FavoriteListingDTO {
    private ListingType type;
    private UUID listingId;
}