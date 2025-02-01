package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JoinEventDTO {
    private UUID guestId;
    private UUID eventId;
}
