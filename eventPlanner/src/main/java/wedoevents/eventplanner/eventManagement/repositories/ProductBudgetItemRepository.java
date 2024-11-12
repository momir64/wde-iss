package wedoevents.eventplanner.eventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;

import java.util.UUID;

@Repository
public interface ProductBudgetItemRepository extends JpaRepository<ProductBudgetItem, UUID> {
}