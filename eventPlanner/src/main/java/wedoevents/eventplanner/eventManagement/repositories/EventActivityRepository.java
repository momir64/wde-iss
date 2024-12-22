package wedoevents.eventplanner.eventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;

import java.util.UUID;

@Repository
public interface EventActivityRepository extends JpaRepository<EventActivity, UUID> {
}
