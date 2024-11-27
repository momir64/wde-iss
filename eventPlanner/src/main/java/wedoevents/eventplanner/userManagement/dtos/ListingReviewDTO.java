package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.userManagement.models.PendingStatus;

import java.util.UUID;

@Getter
@Setter
public class ListingReviewDTO {
    private UUID          id;
    private int           grade;
    private String        comment;
    private PendingStatus pendingStatus;
    private ListingType   listingType;
    private UUID          listingId;
    private UUID          eventOrganizerId;
}
