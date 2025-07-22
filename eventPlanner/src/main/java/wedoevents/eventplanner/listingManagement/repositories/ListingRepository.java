package wedoevents.eventplanner.listingManagement.repositories;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import wedoevents.eventplanner.listingManagement.dtos.ListingDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.productManagement.models.VersionedProductId;

import java.util.List;
import java.util.UUID;

@Repository
public interface ListingRepository extends JpaRepository<VersionedProduct, VersionedProductId> {
    @Query(value = """
        select type, id, version, name, description, oldPrice, price, is_available, images
        from (
            select *, dense_rank() over (order by avg_rating desc, id) as rank
            from (
                (
                    select 
                        'SERVICE' as type, 
                        vs.static_service_id as id, 
                        version, 
                        vs.name, 
                        vs.description, 
                        price as oldPrice, 
                        (price * (1 - sale_percentage)) as price, 
                        is_available, 
                        is_active, 
                        is_private, 
                        city, 
                        images, 
                        vs.is_last_version,
                        -- Calculate average rating for services
                        coalesce((
                            select avg(lr.grade) 
                            from listing_review lr 
                            where lr.service_static_service_id = ss.static_service_id
                        ), 0) as avg_rating
                    from versioned_service vs
                    inner join static_service ss on ss.static_service_id = vs.static_service_id
                    inner join seller s on s.id = ss.seller_id
                    inner join versioned_service_images vsi 
                        on vsi.versioned_service_static_service_id = vs.static_service_id 
                        and vsi.versioned_service_version = vs.version
                    where is_last_version
                )
                union all
                (
                    select 
                        'PRODUCT' as type, 
                        vp.static_product_id as id, 
                        version, 
                        vp.name, 
                        vp.description, 
                        price as oldPrice, 
                        (price * (1 - sale_percentage)) as price, 
                        is_available, 
                        is_active, 
                        is_private, 
                        city, 
                        images, 
                        vp.is_last_version,
                        -- Calculate average rating for products
                        coalesce((
                            select avg(lr.grade) 
                            from listing_review lr 
                            where lr.product_static_product_id = sp.static_product_id
                        ), 0) as avg_rating
                    from versioned_product vp
                    inner join static_product sp on sp.static_product_id = vp.static_product_id
                    inner join seller s on s.id = sp.seller_id
                    inner join versioned_product_images vpi 
                        on vpi.versioned_product_static_product_id = vp.static_product_id 
                        and vpi.versioned_product_version = vp.version
                    where is_last_version
                )
            ) as combined
            where 
                (:city is null or city = :city)
                and is_available
                and is_last_version
                and is_active
                and not is_private
        ) as ranked
        where rank <= 5
""", nativeQuery = true)
    List<Object[]> getTopListings(@Param("city") String city);

    @Query(value = """
    with ranked as (
        select *, 
            dense_rank() over (order by
                case when :sortBy = 'price' and :order = 'asc' then price end asc,
                case when :sortBy = 'price' and :order = 'desc' then price end desc,
                case when :sortBy = 'name' and :order = 'asc' then name end asc,
                case when :sortBy = 'name' and :order = 'desc' then name end desc,
                case when :sortBy = 'rating' and :order = 'asc' then avg_rating end asc,
                case when :sortBy = 'rating' and :order = 'desc' then avg_rating end desc,
                id
            ) as rank
        from (
            (
                select 
                    'SERVICE' as type, 
                    vs.static_service_id as id, 
                    version, 
                    vs.name, 
                    vs.description, 
                    price as oldPrice, 
                    (price * (1 - sale_percentage)) as price, 
                    is_available, 
                    is_active, 
                    is_private, 
                    images, 
                    ss.service_category_id as category, 
                    vs.is_last_version,
                    -- Average rating for services
                    coalesce((
                        select avg(lr.grade) 
                        from listing_review lr 
                        where lr.service_static_service_id = ss.static_service_id
                    ), 0) as avg_rating
                from versioned_service vs
                inner join static_service ss on ss.static_service_id = vs.static_service_id
                inner join versioned_service_images vsi 
                    on vsi.versioned_service_static_service_id = vs.static_service_id 
                    and vsi.versioned_service_version = vs.version
                where is_last_version
            )
            union all
            (
                select 
                    'PRODUCT' as type, 
                    vp.static_product_id as id, 
                    version, 
                    vp.name, 
                    vp.description, 
                    price as oldPrice, 
                    (price * (1 - sale_percentage)) as price, 
                    is_available, 
                    is_active, 
                    is_private, 
                    images, 
                    sp.product_category_id as category, 
                    vp.is_last_version,
                    -- Average rating for products
                    coalesce((
                        select avg(lr.grade) 
                        from listing_review lr 
                        where lr.product_static_product_id = sp.static_product_id
                    ), 0) as avg_rating
                from versioned_product vp
                inner join static_product sp on sp.static_product_id = vp.static_product_id
                inner join versioned_product_images vpi 
                    on vpi.versioned_product_static_product_id = vp.static_product_id 
                    and vpi.versioned_product_version = vp.version
                where is_last_version
            )
        ) as combined
        where 
            (:searchTerms is null or lower(name) like concat('%', lower(:searchTerms), '%'))
            and (:type is null or type = :type)
            and (:category is null or category = :category)
            and (:minPrice is null or price >= :minPrice)
            and (:maxPrice is null or price <= :maxPrice)
            and (:minRating is null or avg_rating >= :minRating)  -- Rating filter
            and (:maxRating is null or avg_rating <= :maxRating)  -- Rating filter
            and is_available
            and is_active
            and not is_private
    )
    select type, id, version, name, description, oldPrice, price, is_available, images, count
    from ranked
    join (select max(rank) as count from ranked) subquery on true
    where rank > :page * :size and rank <= (:page + 1) * :size
""", nativeQuery = true)
    List<Object[]> getListings(@Param("searchTerms") String searchTerms,
                               @Param("type") String type,
                               @Param("category") UUID category,
                               @Param("minPrice") Double minPrice,
                               @Param("maxPrice") Double maxPrice,
                               @Param("minRating") Double minRating,
                               @Param("maxRating") Double maxRating,
                               @Param("sortBy") String sortBy,
                               @Param("order") String order,
                               @Param("page") int page,
                               @Param("size") int size);

    @Query(value = """
    with ranked_from_seller as (
        select *, dense_rank() over (order by
            case when :sortBy = 'price' and :order = 'asc' then price end asc,
            case when :sortBy = 'price' and :order = 'desc' then price end desc,
            case when :sortBy = 'name' and :order = 'asc' then name end asc,
            case when :sortBy = 'name' and :order = 'desc' then name end desc,
            case when :sortBy = 'rating' and :order = 'asc' then avg_rating end asc,
            case when :sortBy = 'rating' and :order = 'desc' then avg_rating end desc,
            id
        ) as rank
        from (
            (select 
                'SERVICE' as type, 
                vs.static_service_id as id, 
                version, 
                vs.name, 
                vs.description, 
                price as oldPrice, 
                (price * (1 - sale_percentage)) as price, 
                is_available, 
                is_active, 
                images, 
                ss.service_category_id as category, 
                vs.is_last_version,
                coalesce((
                    select avg(lr.grade) 
                    from listing_review lr 
                    where lr.service_static_service_id = ss.static_service_id
                ), 0) as avg_rating
            from versioned_service vs
            inner join static_service ss on ss.static_service_id = vs.static_service_id
            inner join versioned_service_images vsi 
                on vsi.versioned_service_static_service_id = vs.static_service_id 
                and vsi.versioned_service_version = vs.version
            where ss.seller_id = :sellerId and is_last_version)
            
            union all
            
            (select 
                'PRODUCT' as type, 
                vp.static_product_id as id, 
                version, 
                vp.name, 
                vp.description, 
                price as oldPrice, 
                (price * (1 - sale_percentage)) as price, 
                is_available, 
                is_active, 
                images, 
                sp.product_category_id as category, 
                vp.is_last_version,
                coalesce((
                    select avg(lr.grade) 
                    from listing_review lr 
                    where lr.product_static_product_id = sp.static_product_id
                ), 0) as avg_rating
            from versioned_product vp
            inner join static_product sp on sp.static_product_id = vp.static_product_id
            inner join versioned_product_images vpi 
                on vpi.versioned_product_static_product_id = vp.static_product_id 
                and vpi.versioned_product_version = vp.version
            where sp.seller_id = :sellerId and is_last_version)
        ) as combined
        where (:searchTerms is null or lower(name) like concat('%', lower(:searchTerms), '%'))
          and (:type is null or type = :type)
          and (:category is null or category = :category)
          and (:minPrice is null or price >= :minPrice)
          and (:maxPrice is null or price <= :maxPrice)
          and (:minRating is null or avg_rating >= :minRating)
          and (:maxRating is null or avg_rating <= :maxRating)
          and is_active
          and is_last_version
    )
    select type, id, version, name, description, oldPrice, price, is_active, images, count
    from ranked_from_seller
    join (select max(rank) as count from ranked_from_seller) subquery on true
    where rank > :page * :size and rank <= (:page + 1) * :size
""", nativeQuery = true)
    List<Object[]> getListingsFromSeller(@Param("sellerId") UUID sellerId,
                                         @Param("searchTerms") String searchTerms,
                                         @Param("type") String type,
                                         @Param("category") UUID category,
                                         @Param("minPrice") Double minPrice,
                                         @Param("maxPrice") Double maxPrice,
                                         @Param("minRating") Double minRating,
                                         @Param("maxRating") Double maxRating,
                                         @Param("sortBy") String sortBy,
                                         @Param("order") String order,
                                         @Param("page") int page,
                                         @Param("size") int size);
}

