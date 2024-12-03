package wedoevents.eventplanner.productManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.productManagement.models.ProductCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
    List<ProductCategory> findAllByIsPendingTrue();
    List<ProductCategory> findAllByIsPendingFalse();

    @Query("SELECT COUNT(*) > 0 FROM ProductCategory pc INNER JOIN StaticProduct sp ON pc = sp.productCategory" +
            " WHERE pc.id = :id")
    boolean hasAssociatedProducts(UUID id);

    @Transactional
    void removeProductCategoryById(UUID id);

    @Transactional
    @Modifying
    @Query("UPDATE StaticProduct sp SET sp.pending = false WHERE sp.productCategory = :pc")
    void setAssociatedProductToPendingFalse(ProductCategory pc);
}