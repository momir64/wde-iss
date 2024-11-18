package wedoevents.eventplanner.userManagement.dtos.userTypes;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AdminDTO {
    private UUID id;
    private UUID userProfileId;
}
