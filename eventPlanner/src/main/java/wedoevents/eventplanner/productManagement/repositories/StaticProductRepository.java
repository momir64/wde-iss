package wedoevents.eventplanner.productManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.models.StaticProduct;

import java.util.UUID;

public interface StaticProductRepository extends JpaRepository<StaticProduct, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE StaticProduct sp SET sp.productCategory = :replacing WHERE sp.productCategory = :toBeReplaced")
    void replacePendingProductCategory(ProductCategory toBeReplaced, ProductCategory replacing);
}
