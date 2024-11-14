package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;

import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {

}
