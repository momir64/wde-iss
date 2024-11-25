package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.ListingReview;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ListingReview, UUID> {
}