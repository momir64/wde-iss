package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.EventReview;

import java.util.UUID;

public interface EventReviewRepository extends JpaRepository<EventReview, UUID> {
}