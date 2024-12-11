package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import java.util.Optional;
import java.util.UUID;

public interface GuestRepository extends JpaRepository<Guest, UUID> {
    void deleteByProfile(Profile profile);

    Optional<Guest> findByProfile(Profile profile);

    @Query("SELECT g FROM Guest g WHERE g.profile.email = ?1")
    Optional<Guest> findByEmail(String email);
}
