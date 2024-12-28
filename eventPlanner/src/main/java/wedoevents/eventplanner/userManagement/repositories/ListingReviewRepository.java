package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.ListingReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wedoevents.eventplanner.userManagement.models.PendingStatus;

import java.util.List;
import java.util.UUID;

public interface ListingReviewRepository extends JpaRepository<ListingReview, UUID> {
    @Query("SELECT lr FROM ListingReview lr WHERE lr.service.id = :serviceId AND lr.pendingStatus = :status")
    List<ListingReview> findByServiceIdAndPendingStatus(@Param("serviceId") UUID serviceId, @Param("status") PendingStatus status);


    @Query("SELECT lr FROM ListingReview lr WHERE lr.product.id = :productId AND lr.pendingStatus = :status")
    List<ListingReview> findByProductIdAndPendingStatus(@Param("productId") UUID productId, @Param("status") PendingStatus status);

    @Query("SELECT lr FROM ListingReview lr WHERE lr.pendingStatus = :status")
    List<ListingReview> findByPendingStatus(@Param("status") PendingStatus status);
}