package wedoevents.eventplanner.eventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {
    @Query("SELECT et.recommendedProductCategories FROM EventType et WHERE et.id = :id")
    List<ProductCategory> getRecommendedProductCategoriesByEventTypeId(UUID id);
    @Query("SELECT et.recommendedServiceCategories FROM EventType et WHERE et.id = :id")
    List<ServiceCategory> getRecommendedServiceCategoriesByEventTypeId(UUID id);
}