package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.PendingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserReportDTO {
    private UUID id;

    private LocalDateTime reportDateTime;
    private LocalDateTime banStartDateTime;
    private PendingStatus status;
    private UUID userProfileToId;
    private UUID userProfileFromId;
}
