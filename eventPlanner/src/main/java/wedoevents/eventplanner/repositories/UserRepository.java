package wedoevents.eventplanner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Additional query methods can go here, if needed
}