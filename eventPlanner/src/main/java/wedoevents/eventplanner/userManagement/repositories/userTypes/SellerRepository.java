package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;

import java.util.UUID;
import wedoevents.eventplanner.userManagement.models.Profile;
import java.util.Optional;
public interface SellerRepository extends JpaRepository<Seller, UUID> {
    void deleteByProfile(Profile profile);
    Optional<Seller> findByProfile(Profile profile);

    @Query("SELECT s FROM Seller s WHERE s.profile.id = :profileId")
    Optional<Seller> findByProfileId(UUID profileId);
}
