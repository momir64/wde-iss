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
           """)
    Page<Event> searchEvents(@Param("searchTerms") String searchTerms,
                             @Param("city") String city,
                             @Param("eventTypeId") UUID eventTypeId,
//                             @Param("minRating") Double minRating,
//                             @Param("maxRating") Double maxRating,
                             @Param("dateRangeStart") LocalDate dateRangeStart,
                             @Param("dateRangeEnd") LocalDate dateRangeEnd,
                             Pageable pageable);  // todo: rating

    @Query("""
               select e from Event e
               where :city is null or e.city.name = :city
               order by e.date desc
               limit 5
           """)
    List<Event> getTopEvents(@Param("city") String city);  // todo: sort by rating





}