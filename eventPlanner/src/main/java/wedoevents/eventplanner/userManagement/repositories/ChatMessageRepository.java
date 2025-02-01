package wedoevents.eventplanner.userManagement.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import wedoevents.eventplanner.userManagement.models.Chat;
import wedoevents.eventplanner.userManagement.models.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    @Query(value = "SELECT * FROM chat_message cm WHERE cm.chat = ?1 ORDER BY cm.time", nativeQuery = true)
    List<ChatMessage> getAllChatMessagesFromChat(UUID chatId);

    @Query(value = "SELECT * FROM chat_message cm WHERE cm.chat = ?1 ORDER BY cm.time DESC LIMIT 1", nativeQuery = true)
    ChatMessage getLastMessageFromChat(UUID chatId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO chat_message (id, message, time, to_profile_id, chat) VALUES " +
            "((SELECT gen_random_uuid()), ?1, ?2, ?3, ?4)", nativeQuery = true)
    int addMessageToChat(String message, LocalDateTime time, UUID toProfileId, UUID chatId);
}