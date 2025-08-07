package wedoevents.eventplanner.userManagement.dtos.userTypes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EventOrganizerDTO {
    private UUID id;
    private UUID userProfileId;
    private String name;
    private String surname;
    private String city;
    private String address;
    private String postalNumber;
    private String telephoneNumber;
    private String profileImage;
}
