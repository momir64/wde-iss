package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.UserType;

import java.util.UUID;

@Getter
@Setter
public class UserAccessDTO {
    private UUID profileId;
    private String jwt;
    private UserType userType;
}
