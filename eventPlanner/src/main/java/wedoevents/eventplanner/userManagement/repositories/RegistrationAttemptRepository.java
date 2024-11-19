package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;


import java.util.Optional;
import java.util.UUID;

public interface RegistrationAttemptRepository extends JpaRepository<RegistrationAttempt, UUID> {
    Optional<RegistrationAttempt> findFirstByProfileIdOrderByTimeDesc(UUID profileId);
}