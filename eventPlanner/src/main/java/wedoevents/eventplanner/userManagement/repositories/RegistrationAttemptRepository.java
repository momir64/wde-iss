package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.RegistrationAttempt;

public interface RegistrationAttemptRepository extends JpaRepository<RegistrationAttempt, Long> {
}