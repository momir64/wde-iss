package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserBlockDTO {
    private UUID fromId;
    private UUID toId;
}
