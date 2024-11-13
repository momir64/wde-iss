package wedoevents.eventplanner.userManagement.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.ChatMessage;
import wedoevents.eventplanner.userManagement.repositories.ChatMessageRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<ChatMessage> getChatMessageById(Long id) {
        return chatMessageRepository.findById(id);
    }

    public List<ChatMessage> getMessagesToProfile(Long toProfileId) {
        return chatMessageRepository.findByToId(toProfileId);
    }

    public List<ChatMessage> getMessagesFromProfile(Long fromProfileId) {
        return chatMessageRepository.findByFromId(fromProfileId);
    }

    public void deleteChatMessage(Long id) {
        chatMessageRepository.deleteById(id);
    }
}