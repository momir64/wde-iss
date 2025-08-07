package wedoevents.eventplanner.userManagement.repositories.userTypes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, UUID> {
    Optional<EventOrganizer> findByProfile(Profile profile);

    void deleteByProfile(Profile profile);

    @Query("SELECT eo.myEvents FROM EventOrganizer eo WHERE eo.id = :id")
    List<Event> getMyEventsById(UUID id);

    @Query("""
               select e from EventOrganizer eo join eo.myEvents e where eo.profile.id = :profileId
               and (:searchTerms is null or lower(e.name) like concat('%', lower(cast(:searchTerms as string)), '%'))
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
    Page<Event> searchMyEvents(@Param("profileId") UUID profileId,
                               @Param("searchTerms") String searchTerms,
                               @Param("city") String city,
                               @Param("eventTypeId") UUID eventTypeId,
                               @Param("minRating") Double minRating,
                               @Param("maxRating") Double maxRating,
                               @Param("dateRangeStart") LocalDate dateRangeStart,
                               @Param("dateRangeEnd") LocalDate dateRangeEnd,
                               @Param("sortBy") String sortBy,
                               @Param("order") String order,
                               Pageable pageable);

    @Query("SELECT eo FROM EventOrganizer eo WHERE eo.profile.id = :profileId")
    Optional<EventOrganizer> findByProfileId(UUID profileId);

    @Query("SELECT eo FROM EventOrganizer eo JOIN eo.myEvents e WHERE e.id = :eventId")
    Optional<EventOrganizer> findByEventId(@Param("eventId") UUID eventId);

    @Query("SELECT o FROM EventOrganizer o LEFT JOIN FETCH o.myEvents WHERE o.profile.id = :profileId")
    Optional<EventOrganizer> findByProfileIdWithEvents(@Param("profileId") UUID profileId);
}
