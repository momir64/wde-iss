package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.PendingStatus;

import java.util.UUID;

@Getter
@Setter
public class ReviewDTO {
    private UUID id;

    private int grade;
    private String comment;
    private PendingStatus pendingStatus;

    // one of these two will be -1 or null

    private UUID serviceId;
    private UUID productId;

    private UUID eventOrganizerId;
}
