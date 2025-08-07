package wedoevents.eventplanner.serviceManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, UUID> {
    List<ServiceCategory> findAllByIsPendingTrue();
    List<ServiceCategory> findAllByIsPendingFalse();

    @Query("SELECT COUNT(*) > 0 FROM ServiceCategory sc INNER JOIN StaticService ss ON sc = ss.serviceCategory" +
            " WHERE sc.id = :id")
    boolean hasAssociatedServices(UUID id);

    @Transactional
    void removeServiceCategoryById(UUID id);

    @Transactional
    @Modifying
    @Query("UPDATE StaticService ss SET ss.pending = false WHERE ss.serviceCategory = :sc")
    void setAssociatedServiceToPendingFalse(ServiceCategory sc);
}
