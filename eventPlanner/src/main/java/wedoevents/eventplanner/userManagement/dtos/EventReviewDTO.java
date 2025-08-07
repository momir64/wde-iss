package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.PendingStatus;

import java.util.UUID;

@Getter
@Setter
public class EventReviewDTO {
    private UUID          id;
    private int           grade;
    private String        comment;
    private PendingStatus pendingStatus;
    private UUID          eventId;
    private UUID          guestId;
}
