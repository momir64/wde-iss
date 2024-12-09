package wedoevents.eventplanner.eventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.Event;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query(value = "DELETE FROM product_budget_item " +
            "WHERE " +
            "event_id = ?1 AND " +
            "product_category_id = ?2 AND " +
            "versioned_product_static_product_id IS NOT NULL", nativeQuery = true)
    @Modifying
    int removeEventEmptyProductCategory(UUID eventId, UUID productCategoryId);

    @Query(value = "DELETE FROM service_budget_item " +
            "WHERE " +
            "event_id = ?1 AND " +
            "service_category_id = ?2 AND " +
            "versioned_service_static_service_id IS NOT NULL", nativeQuery = true)
    @Modifying
    int removeEventEmptyServiceCategory(UUID eventId, UUID serviceCategoryId);

    boolean existsEventById(UUID id);
}