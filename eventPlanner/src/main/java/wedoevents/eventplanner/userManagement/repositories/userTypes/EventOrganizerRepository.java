package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.eventManagement.dtos.EventComplexViewDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, UUID> {
    Optional<EventOrganizer> findByProfile(Profile profile);
    void deleteByProfile(Profile profile);

    @Query("SELECT eo.myEvents FROM EventOrganizer eo WHERE eo.id = :id")
    List<Event> getMyEventsById(UUID id);
}
