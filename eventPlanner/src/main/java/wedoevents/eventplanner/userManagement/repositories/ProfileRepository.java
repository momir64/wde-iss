package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByEmail(String email);

    @Query("SELECT p FROM Profile p JOIN p.blockedUsers b WHERE b.id = :profileId")
    List<Profile> findAllBlockingProfiles(@Param("profileId") UUID profileId);

}