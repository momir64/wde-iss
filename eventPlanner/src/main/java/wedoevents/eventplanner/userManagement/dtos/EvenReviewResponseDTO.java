package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.PendingStatus;

import java.util.UUID;

@Getter
@Setter
public class EvenReviewResponseDTO {
    private UUID id;
    private int           grade;
    private String        comment;
    private PendingStatus pendingStatus;
    private UUID          eventId;
    private UUID          guestId;
    private String        guestName;
    private String        guestSurname;
}
