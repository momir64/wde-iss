package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import wedoevents.eventplanner.userManagement.models.Profile;
import java.util.Optional;
public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, UUID> {
    Optional<EventOrganizer> findByProfile(Profile profile);
    void deleteByProfile(Profile profile);

    @Query("SELECT eo.myEvents FROM EventOrganizer eo WHERE eo.id = :id")
    List<Event> getMyEventsById(UUID id);

    @Query("SELECT eo.myEvents FROM EventOrganizer eo WHERE eo.profile.id = :id")
    List<Event> getMyEventsByProfileId(UUID id);

    @Query("SELECT eo FROM EventOrganizer eo WHERE eo.profile.id = :profileId")
    Optional<EventOrganizer> findByProfileId(UUID profileId);

    @Query("SELECT eo FROM EventOrganizer eo JOIN eo.myEvents e WHERE e.id = :eventId")
    Optional<EventOrganizer> findByEventId(@Param("eventId") UUID eventId);

    @Query("SELECT o FROM EventOrganizer o LEFT JOIN FETCH o.myEvents WHERE o.profile.id = :profileId")
    Optional<EventOrganizer> findByProfileIdWithEvents(@Param("profileId") UUID profileId);
}
