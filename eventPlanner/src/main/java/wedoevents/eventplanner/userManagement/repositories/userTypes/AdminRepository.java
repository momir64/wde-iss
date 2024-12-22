package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.userTypes.Admin;

import java.util.UUID;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByProfile(Profile profile);

    @Query("SELECT a FROM Admin a WHERE a.profile.id = :profileId")
    Optional<Admin> findByProfileId(UUID profileId);
}
