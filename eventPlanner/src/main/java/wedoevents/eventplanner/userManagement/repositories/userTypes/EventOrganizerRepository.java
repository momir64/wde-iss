package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;

import java.util.Optional;
import java.util.UUID;

public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, UUID> {
    Optional<EventOrganizer> findByProfile(Profile profile);
    void deleteByProfile(Profile profile);
}
