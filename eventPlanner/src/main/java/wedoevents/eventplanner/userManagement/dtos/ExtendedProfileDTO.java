package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExtendedProfileDTO {
    private UUID profileId;
    private UUID userId;

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


    private List<UUID> blockedUserIds;
    private List<UUID> favouriteEventIds;
    private List<UUID> favouriteProductIds;
    private List<UUID> favouriteServiceIds;
}
