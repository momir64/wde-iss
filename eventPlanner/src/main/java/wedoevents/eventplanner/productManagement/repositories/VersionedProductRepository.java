package wedoevents.eventplanner.productManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.productManagement.models.VersionedProductId;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VersionedProductRepository extends JpaRepository<VersionedProduct, VersionedProductId> {
    @Query("SELECT vp FROM VersionedProduct vp WHERE " +
            "vp.isLastVersion AND " +
            "vp.staticProduct.staticProductId = ?1")
    Optional<VersionedProduct> getVersionedProductByStaticProductIdAndLatestVersion(UUID staticProductId);

    @Query("SELECT vp FROM VersionedProduct vp INNER JOIN " +
            "(SELECT vp2.staticProductId AS max_version_id, MAX(vp2.version) AS max_version " +
                "FROM VersionedProduct vp2 " +
                "GROUP BY max_version_id) AS vp_max_versions " +
            "ON vp.staticProductId = vp_max_versions.max_version_id AND " +
                "vp.version = vp_max_versions.max_version")
    Collection<VersionedProduct> getAllVersionedProductsWithMaxVersions();

    @Query(value =
            "SELECT vp.* " +
            "FROM static_product sp INNER JOIN versioned_product vp ON sp.static_product_id = vp.static_product_id " +
            "WHERE sp.seller_id = ?1 AND vp.is_last_version AND vp.is_active",
            nativeQuery = true)
    Collection<VersionedProduct> getAllVersionedProductsWithMaxVersionsFromSeller(UUID sellerId);

    Optional<VersionedProduct> getVersionedProductByStaticProductIdAndVersion(UUID staticProductId, Integer version);
}