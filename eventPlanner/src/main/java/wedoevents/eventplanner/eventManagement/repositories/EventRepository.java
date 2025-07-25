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
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    boolean existsEventById(UUID id);

    @Query("""
               select e from Event e
               where (:searchTerms is null or lower(e.name) like concat('%', lower(cast(:searchTerms as string)), '%'))
               and (:city is null or e.city.name = :city)
               and (:eventTypeId is null or e.eventType.id = :eventTypeId)
               and (cast(:dateRangeStart as date) is null or e.date >= :dateRangeStart)
               and (cast(:dateRangeEnd as date) is null or e.date <= :dateRangeEnd)
               and (:minRating is null or coalesce((select avg(r.grade) from EventReview r where r.event = e and r.pendingStatus = wedoevents.eventplanner.userManagement.models.PendingStatus.APPROVED), 0) >= :minRating)
               and (:maxRating is null or coalesce((select avg(r.grade) from EventReview r where r.event = e and r.pendingStatus = wedoevents.eventplanner.userManagement.models.PendingStatus.APPROVED), 0) <= :maxRating)
               order by
                    case when :sortBy = 'name' and :order = 'asc' then e.name end asc,
                    case when :sortBy = 'name' and :order = 'desc' then e.name end desc,
                    case when :sortBy = 'date' and :order = 'asc' then e.date end asc,
                    case when :sortBy = 'date' and :order = 'desc' then e.date end desc,
                    case when :sortBy = 'rating' and :order = 'asc' then coalesce((select avg(r.grade) from EventReview r where r.event = e and r.pendingStatus = wedoevents.eventplanner.userManagement.models.PendingStatus.APPROVED), 0) end asc,
                    case when :sortBy = 'rating' and :order = 'desc' then coalesce((select avg(r.grade) from EventReview r where r.event = e and r.pendingStatus = wedoevents.eventplanner.userManagement.models.PendingStatus.APPROVED), 0) end desc,
                    e.id
           """)
    Page<Event> searchEvents(@Param("searchTerms") String searchTerms,
                             @Param("city") String city,
                             @Param("eventTypeId") UUID eventTypeId,
                             @Param("minRating") Double minRating,
                             @Param("maxRating") Double maxRating,
                             @Param("dateRangeStart") LocalDate dateRangeStart,
                             @Param("dateRangeEnd") LocalDate dateRangeEnd,
                             @Param("sortBy") String sortBy,
                             @Param("order") String order,
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

    @Query(value = """
        SELECT CAST(COUNT(DISTINCT guest_id) AS INTEGER) FROM (
            SELECT id AS guest_id FROM guest_invited_events WHERE event_id = :eventId
            UNION
            SELECT id AS guest_id FROM guest_accepted_events WHERE event_id = :eventId
        ) AS combined
        """, nativeQuery = true)
    int countPossibleGuestsByEventId(@Param("eventId") UUID eventId);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.eventActivities WHERE e.id = :id")
    Optional<Event> findByIdWithActivities(@Param("id") UUID id);

}