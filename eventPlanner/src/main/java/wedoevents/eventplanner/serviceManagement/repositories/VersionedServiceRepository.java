package wedoevents.eventplanner.serviceManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.models.VersionedServiceId;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VersionedServiceRepository extends JpaRepository<VersionedService, VersionedServiceId> {
    @Query("SELECT vs FROM VersionedService vs WHERE " +
            "vs.isLastVersion AND " +
            "vs.staticService.staticServiceId = ?1")
    Optional<VersionedService> getLatestByStaticServiceId(UUID staticServiceId);

    @Query("SELECT vs FROM VersionedService vs INNER JOIN " +
            "(SELECT vs2.staticServiceId AS max_version_id, MAX(vs2.version) AS max_version " +
                "FROM VersionedService vs2 " +
                "GROUP BY max_version_id) AS vs_max_versions " +
            "ON vs.staticServiceId = vs_max_versions.max_version_id AND " +
                "vs.version = vs_max_versions.max_version")
    Collection<VersionedService> getAllVersionedServicesWithMaxVersions();

    @Query(value =
            "SELECT vs.* " +
            "FROM static_service ss INNER JOIN versioned_service vs ON vs.static_service_id = ss.static_service_id " +
            "WHERE ss.seller_id = ?1",
            nativeQuery = true)
    Collection<VersionedService> getAllVersionedServicesWithMaxVersionsFromSeller(UUID sellerId);

    VersionedService getVersionedServiceByStaticServiceIdAndVersion(UUID staticServiceId, Integer version);
}