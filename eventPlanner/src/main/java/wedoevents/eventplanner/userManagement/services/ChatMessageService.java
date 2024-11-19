package wedoevents.eventplanner.userManagement.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.ChatMessage;
import wedoevents.eventplanner.userManagement.repositories.ChatMessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public Optional<ChatMessage> getChatMessageById(UUID id) {
        return chatMessageRepository.findById(id);
    }

    public List<ChatMessage> getMessagesToProfile(UUID toProfileId) {
        return chatMessageRepository.findByToId(toProfileId);
    }

    public List<ChatMessage> getMessagesFromProfile(UUID fromProfileId) {
        return chatMessageRepository.findByFromId(fromProfileId);
    }

    public void deleteChatMessage(UUID id) {
        chatMessageRepository.deleteById(id);
    }
}