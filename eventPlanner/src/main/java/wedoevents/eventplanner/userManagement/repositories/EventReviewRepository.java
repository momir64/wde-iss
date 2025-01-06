package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wedoevents.eventplanner.userManagement.models.EventReview;
import wedoevents.eventplanner.userManagement.models.PendingStatus;

import java.util.List;
import java.util.UUID;

public interface EventReviewRepository extends JpaRepository<EventReview, UUID> {
    @Query("SELECT er FROM EventReview er WHERE er.event.id = :eventId AND er.pendingStatus = :status")
    List<EventReview> findByEventIdAndPendingStatus(@Param("eventId") UUID eventId, @Param("status") PendingStatus status);

    @Query("SELECT er FROM EventReview er WHERE er.pendingStatus = :status")
    List<EventReview> findByPendingStatus(PendingStatus status);

    @Query("SELECT COUNT(er) > 0 FROM EventReview er WHERE er.event.id = :eventId AND er.guest.id = :guestId")
    boolean existsByEventIdAndGuestId(@Param("eventId") UUID eventId, @Param("guestId") UUID guestId);


    @Query("SELECT AVG(er.grade) FROM EventReview er WHERE er.event.id = :eventId AND er.pendingStatus = 'APPROVED'")
    Double findAverageRatingByEventId(@Param("eventId") UUID eventId);

}