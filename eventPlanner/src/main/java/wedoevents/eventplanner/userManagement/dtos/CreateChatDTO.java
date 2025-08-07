package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.util.UUID;

@Getter
@Setter
public class CreateChatDTO {
    private ListingType listingType;
    private UUID listingId;
    private Integer listingVersion;
    private UUID chatter1Id;
    private UUID chatter2Id;
}
