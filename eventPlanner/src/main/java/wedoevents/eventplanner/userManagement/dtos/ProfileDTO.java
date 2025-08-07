package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProfileDTO {
    private UUID id;

    private String email;
    private String password;
    private boolean isActive;
    private boolean areNotificationsMuted;
    private boolean isVerified;

    private List<UUID> blockedUserIds;
    private List<UUID> favouriteEventIds;
    private List<UUID> favouriteProductIds;
    private List<UUID> favouriteServiceIds;
}
