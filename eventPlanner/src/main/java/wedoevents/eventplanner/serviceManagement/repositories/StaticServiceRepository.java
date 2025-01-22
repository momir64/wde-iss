package wedoevents.eventplanner.serviceManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.StaticService;

import java.util.UUID;

@Repository
public interface StaticServiceRepository extends JpaRepository<StaticService, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE StaticService ss SET ss.serviceCategory = :replacing WHERE ss.serviceCategory = :toBeReplaced")
    void replacePendingServiceCategory(ServiceCategory toBeReplaced, ServiceCategory replacing);

    @Query(value = "SELECT seller_id FROM static_service WHERE static_service_id = ?1", nativeQuery = true)
    UUID getIdOfSeller(UUID staticSellerId);
}