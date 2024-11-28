package wedoevents.eventplanner.serviceManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.serviceManagement.models.StaticService;

import java.util.UUID;

@Repository
public interface StaticServiceRepository extends JpaRepository<StaticService, UUID> {
}