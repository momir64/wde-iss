package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByEmail(String email);
}