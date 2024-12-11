package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import java.util.UUID;
import wedoevents.eventplanner.userManagement.models.Profile;
import java.util.Optional;
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    void deleteByProfile(Profile profile);
    Optional<Guest> findByProfile(Profile profile);
}
