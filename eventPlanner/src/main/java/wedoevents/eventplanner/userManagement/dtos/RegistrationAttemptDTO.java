package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistrationAttemptDTO {
    private UUID id;
    private LocalDateTime time;
    private UUID userProfileId;
}
