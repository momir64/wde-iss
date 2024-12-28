package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class FastRegistrationDTO {

    private String name;
    private String surname;
    private UUID profileId;
    private String password;
}
