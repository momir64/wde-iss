package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDTO {
    private String email;
    private boolean areNotificationsMuted;
    private String name;
    private String surname;
    private String city;
    private String address;
    private String telephoneNumber;
    private String description;
    private String password;
}
