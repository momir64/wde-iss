package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.userTypes.Admin;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

}
