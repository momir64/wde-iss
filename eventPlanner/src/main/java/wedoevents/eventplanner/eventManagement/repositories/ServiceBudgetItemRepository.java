package wedoevents.eventplanner.eventManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;

import java.util.UUID;

@Repository
public interface ServiceBudgetItemRepository extends JpaRepository<ServiceBudgetItem, UUID> {
    @Query(value = "DELETE FROM service_budget_item " +
            "WHERE " +
            "event_id = ?1 AND " +
            "service_category_id = ?2 AND " +
            "versioned_service_static_service_id IS NOT NULL", nativeQuery = true)
    @Modifying
    @Transactional
    int removeEventEmptyServiceCategory(UUID eventId, UUID serviceCategoryId);
}