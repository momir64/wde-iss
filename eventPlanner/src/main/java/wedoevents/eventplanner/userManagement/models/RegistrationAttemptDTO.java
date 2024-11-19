package wedoevents.eventplanner.userManagement.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class RegistrationAttemptDTO {
    private UUID id;

    private UUID profileId;
}
