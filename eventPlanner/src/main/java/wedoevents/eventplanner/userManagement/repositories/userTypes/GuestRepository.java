package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import java.util.UUID;

public interface GuestRepository extends JpaRepository<Guest, UUID> {
    void deleteByProfile(Profile profile);
}
