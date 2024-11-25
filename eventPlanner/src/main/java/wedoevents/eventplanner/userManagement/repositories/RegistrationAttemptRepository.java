package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;

import java.util.UUID;

public interface RegistrationAttemptRepository extends JpaRepository<RegistrationAttempt, UUID> {
}