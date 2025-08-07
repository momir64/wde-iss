package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.util.UUID;

@Getter
@Setter
public class FavoritesRequestDTO {
    private UUID userId;
    private UUID favoriteItemId;
    private ListingType listingType;

}
