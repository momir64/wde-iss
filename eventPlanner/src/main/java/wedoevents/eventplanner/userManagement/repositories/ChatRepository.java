package wedoevents.eventplanner.userManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.Chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query("SELECT c FROM Chat c WHERE c.chatter1.id = :chatter_profile_id OR c.chatter2.id = :chatter_profile_id")
    List<Chat> findChatsFromProfile(UUID chatter_profile_id);

    @Query(value = "SELECT * FROM chat c " +
            "WHERE ((c.chatter1_profile_id = ?1 AND c.chatter2_profile_id = ?2) OR " +
            "(c.chatter2_profile_id = ?1 AND c.chatter1_profile_id = ?2)) AND " +
            "(c.event_id = ?3 OR " +
            "(c.product_static_product_id = ?3 AND c.product_version = ?4) OR " +
            "(c.service_static_service_id = ?3 AND c.service_version = ?4))",
            nativeQuery = true)
    Optional<Chat> findExistingChat(UUID chatter1Id, UUID chatter2Id, UUID listingId, Integer listingVersion);
}