package wedoevents.eventplanner.userManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.Chat;
import wedoevents.eventplanner.userManagement.models.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query("SELECT c FROM Chat c WHERE c.chatter1.id = :chatter_profile_id OR c.chatter2.id = :chatter_profile_id")
    List<Chat> findChatsFromProfile(UUID chatter_profile_id);
}