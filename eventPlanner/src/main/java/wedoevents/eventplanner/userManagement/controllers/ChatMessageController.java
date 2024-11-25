package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.models.ChatMessage;
import wedoevents.eventplanner.userManagement.services.ChatMessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chatMessages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping
    public ResponseEntity<ChatMessage> createChatMessage(@RequestBody ChatMessage chatMessage) {
        ChatMessage savedMessage = chatMessageService.saveChatMessage(chatMessage);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatMessage> getChatMessageById(@PathVariable UUID id) {
        return chatMessageService.getChatMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/to/{toProfileId}")
    public ResponseEntity<List<ChatMessage>> getMessagesToProfile(@PathVariable UUID toProfileId) {
        return ResponseEntity.ok(chatMessageService.getMessagesToProfile(toProfileId));
    }

    @GetMapping("/from/{fromProfileId}")
    public ResponseEntity<List<ChatMessage>> getMessagesFromProfile(@PathVariable UUID fromProfileId) {
        return ResponseEntity.ok(chatMessageService.getMessagesFromProfile(fromProfileId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatMessage(@PathVariable UUID id) {
        chatMessageService.deleteChatMessage(id);
        return ResponseEntity.noContent().build();
    }
}