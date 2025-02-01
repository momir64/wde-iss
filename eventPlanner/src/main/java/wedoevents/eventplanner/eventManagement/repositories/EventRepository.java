package wedoevents.eventplanner.eventManagement.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wedoevents.eventplanner.eventManagement.models.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    boolean existsEventById(UUID id);

    @Query("""
               select e from Event e
               where (:searchTerms is null or lower(e.name) like concat('%', lower(cast(:searchTerms as string)), '%'))
               and (:city is null or e.city.name = :city)
               and (:eventTypeId is null or e.eventType.id = :eventTypeId)
               and (:dateRangeStart is null or e.date >= :dateRangeStart)
               and (:dateRangeEnd is null or e.date <= :dateRangeEnd)
               and (:minRating is null or coalesce((select avg(r.grade) from EventReview r where r.event = e), 0) >= :minRating)
               and (:maxRating is null or coalesce((select avg(r.grade) from EventReview r where r.event = e), 0) <= :maxRating)
           """)
    Page<Event> searchEvents(@Param("searchTerms") String searchTerms,
                             @Param("city") String city,
                             @Param("eventTypeId") UUID eventTypeId,
                             @Param("minRating") Double minRating,
                             @Param("maxRating") Double maxRating,
                             @Param("dateRangeStart") LocalDate dateRangeStart,
                             @Param("dateRangeEnd") LocalDate dateRangeEnd,
                             Pageable pageable);

    @Query("""
    select e from Event e
    where :city is null or e.city.name = :city
    order by coalesce((select avg(r.grade) from EventReview r where r.event = e), 0) desc
    limit 5
""")
    List<Event> getTopEvents(@Param("city") String city);

    @Query("SELECT e FROM Event e WHERE e.isPublic = true")
    List<Event> findAllPublicEvents();
}