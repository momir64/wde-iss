package wedoevents.eventplanner.serviceManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, UUID> {
    List<ServiceCategory> findAllByIsPendingTrue();
    List<ServiceCategory> findAllByIsPendingFalse();
}
