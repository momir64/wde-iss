package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.UserType;
@Setter
@Getter
public class RegistrationDTO {
    private String email;
    private String password;
    private boolean isActive;
    private boolean areNotificationsMuted;
    private boolean isVerified;
    private String name;
    private String surname;
    private String city;
    private String address;
    private String postalNumber;
    private String telephoneNumber;
    private String profileImage;
    private UserType userType;

}
