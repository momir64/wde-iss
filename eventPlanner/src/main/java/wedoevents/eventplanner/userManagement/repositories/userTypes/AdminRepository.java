package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.userTypes.Admin;

import java.util.UUID;
import wedoevents.eventplanner.userManagement.models.Profile;
import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByProfile(Profile profile);
}
