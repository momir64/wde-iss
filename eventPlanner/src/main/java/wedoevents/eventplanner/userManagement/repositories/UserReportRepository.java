package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wedoevents.eventplanner.userManagement.models.UserReport;

public interface UserReportRepository extends JpaRepository<UserReport, UUID> {
}