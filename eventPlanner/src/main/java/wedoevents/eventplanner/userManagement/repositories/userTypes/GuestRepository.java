package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import wedoevents.eventplanner.userManagement.models.Profile;
import java.util.Optional;
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    void deleteByProfile(Profile profile);
    Optional<Guest> findByProfile(Profile profile);

    @Query("SELECT g FROM Guest g WHERE g.profile.email = ?1")
    Optional<Guest> findByEmail(String email);

    @Query("SELECT g FROM Guest g  WHERE g.profile.id = :profileId")
    Optional<Guest> findByProfileId(UUID profileId);

    @Query("SELECT COUNT(g) FROM Guest g JOIN g.acceptedEvents e WHERE e.id = :eventId")
    long countGuestsByAcceptedEventId(@Param("eventId") UUID eventId);

    @Query("SELECT g FROM Guest g JOIN g.invitedEvents e WHERE e.id = :eventId")
    List<Guest> findGuestsByInvitedEventId(@Param("eventId") UUID eventId);

    @Query("SELECT g FROM Guest g JOIN g.acceptedEvents e WHERE e.id = :eventId")
    List<Guest> findGuestsByAcceptedEventId(@Param("eventId") UUID eventId);

}
