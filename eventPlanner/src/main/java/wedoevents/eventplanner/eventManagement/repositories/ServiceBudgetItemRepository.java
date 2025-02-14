package wedoevents.eventplanner.eventManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceBudgetItemRepository extends JpaRepository<ServiceBudgetItem, UUID> {
    @Query(value = "DELETE FROM service_budget_item " +
                   "WHERE " +
                   "event_id = ?1 AND " +
                   "service_category_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void removeEventEmptyServiceCategory(UUID eventId, UUID serviceCategoryId);

    @Query(value = "SELECT COUNT(*) > 0 FROM service_budget_item " +
            "WHERE " +
            "event_id = ?1 AND " +
            "service_category_id = ?2 AND " +
            "service_budget_item.versioned_service_static_service_id IS NOT NULL", nativeQuery = true)
    boolean hasBoughtServiceByEventIdAndServiceCategoryId(UUID eventId, UUID serviceCategoryId);

    @Query("""
            select sbi from ServiceBudgetItem sbi
            where sbi.service.staticServiceId = :serviceId
            and function('date', sbi.startTime) = :eventDay
            order by sbi.startTime asc
           """)
    List<ServiceBudgetItem> getForServiceAndDay(@Param("serviceId") UUID serviceId, @Param("eventDay") LocalDate eventDay);

    @Query("""
            select case when count(sbi) > 0 then true else false end
            from ServiceBudgetItem sbi
            where sbi.service.staticServiceId = :serviceId
            and ((sbi.startTime <= :startTime and :startTime <= sbi.endTime)
            or (sbi.startTime <= :endTime and :endTime <= sbi.endTime))
           """)
    boolean doesOverlap(@Param("serviceId") UUID serviceId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}