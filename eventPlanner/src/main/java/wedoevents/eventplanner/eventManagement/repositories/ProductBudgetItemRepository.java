package wedoevents.eventplanner.eventManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;

import java.util.UUID;

@Repository
public interface ProductBudgetItemRepository extends JpaRepository<ProductBudgetItem, UUID> {
    @Query(value = "DELETE FROM product_budget_item " +
            "WHERE " +
            "event_id = ?1 AND " +
            "product_category_id = ?2 AND " +
            "versioned_product_static_product_id IS NULL", nativeQuery = true)
    @Modifying
    @Transactional
    int removeEventEmptyProductCategory(UUID eventId, UUID productCategoryId);
}