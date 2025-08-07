package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.models.UserReport;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, UUID> {
    @Query("SELECT ur FROM UserReport ur WHERE ur.to.id = :profileId AND ur.banStartDateTime IS NOT NULL ORDER BY ur.banStartDateTime DESC")
    Optional<UserReport> findMostRecentBanForProfile(@Param("profileId") UUID profileId);

    List<UserReport> findAllByStatus(PendingStatus status);
}