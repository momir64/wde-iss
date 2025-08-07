package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.UserType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExtendedProfileDTO {
    private UUID profileId;
    private UUID userId;

    private String email;
    private boolean areNotificationsMuted;
    private String name;
    private String surname;
    private String city;
    private String address;
    private String telephoneNumber;
    private String description;
    private UserType userType;
    private String password;
}
