package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RegistrationAttemptDTO {
    private UUID id;
    private LocalDateTime time;
    private UUID userProfileId;
}
