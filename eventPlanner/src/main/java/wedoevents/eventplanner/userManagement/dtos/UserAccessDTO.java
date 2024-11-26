package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserAccessDTO {
    private UUID profileId;
    private String jwt;
    //TODO user type
}
