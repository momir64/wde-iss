package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import wedoevents.eventplanner.userManagement.models.Profile;
import java.util.Optional;
public interface SellerRepository extends JpaRepository<Seller, UUID> {
    void deleteByProfile(Profile profile);
    Optional<Seller> findByProfile(Profile profile);

    @Query("SELECT s FROM Seller s WHERE s.profile.id = :profileId")
    Optional<Seller> findByProfileId(UUID profileId);

    @Query("SELECT s FROM Seller s JOIN s.myServices ms WHERE ms.staticServiceId = :serviceId")
    Optional<Seller> findByServiceId(@Param("serviceId") UUID serviceId);

    @Query("SELECT s FROM Seller s JOIN s.myProducts mp WHERE mp.staticProductId = :productId")
    Optional<Seller> findByProductId(@Param("productId") UUID productId);

}
