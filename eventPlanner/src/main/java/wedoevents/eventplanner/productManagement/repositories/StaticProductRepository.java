package wedoevents.eventplanner.productManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.productManagement.models.StaticProduct;

import java.util.UUID;

public interface StaticProductRepository extends JpaRepository<StaticProduct, UUID> {

}
