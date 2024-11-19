package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;


import java.util.Optional;
import java.util.UUID;

public interface RegistrationAttemptRepository extends JpaRepository<RegistrationAttempt, UUID> {
    @Query("SELECT r FROM RegistrationAttempt r WHERE r.profile.id = :profileId ORDER BY r.time DESC")
    Optional<RegistrationAttempt> findTopByProfileIdOrderByTimeDesc(UUID profileId);
}