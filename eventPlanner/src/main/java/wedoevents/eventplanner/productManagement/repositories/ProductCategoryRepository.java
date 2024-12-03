package wedoevents.eventplanner.productManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.productManagement.models.ProductCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
    List<ProductCategory> findAllByIsPendingTrue();
    List<ProductCategory> findAllByIsPendingFalse();
}