package wedoevents.eventplanner.userManagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.ChatDTO;
import wedoevents.eventplanner.userManagement.dtos.ChatMessageDTO;
import wedoevents.eventplanner.userManagement.dtos.CreateChatDTO;
import wedoevents.eventplanner.userManagement.dtos.NewChatMessageDTO;
import wedoevents.eventplanner.userManagement.services.ChatService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    private final ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<List<ChatDTO>> getChatsFromProfile(@PathVariable UUID profileId) {
        try {
            return ResponseEntity.ok(chatService.getChatsFromProfile(profileId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{profileId}/{chatId}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable UUID profileId, @PathVariable UUID chatId) {
        try {
            return ResponseEntity.ok(chatService.getChat(profileId, chatId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<ChatDTO> createChat(@RequestBody CreateChatDTO createChatDTO) {
        try {
            return ResponseEntity.ok(chatService.createChat(createChatDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<ChatMessageDTO>> getAllMessagesFromChat(@PathVariable UUID chatId) {
        try {
            return ResponseEntity.ok(chatService.getChatMessagesFromChat(chatId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/seeMessage/{chatId}/{profileId}")
    public ResponseEntity<Void> makeMessageSeen(@PathVariable UUID chatId,
                                                                @PathVariable UUID profileId) {
        try {
            chatService.makeMessageSeen(chatId, profileId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @MessageMapping("/send-message")
    public ChatMessageDTO broadcastChatMessage(String message) {
        NewChatMessageDTO messageConverted = parseMessage(message);
        ChatMessageDTO messageResponse = chatService.createMessage(messageConverted);
        messagingTemplate.convertAndSend("/chat-socket-publisher/" + messageConverted.getChatId(), messageResponse);
        return messageResponse;
    }

    private NewChatMessageDTO parseMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        NewChatMessageDTO retVal;
        try {
            retVal = mapper.readValue(message, NewChatMessageDTO.class);
        } catch (IOException e) {
            retVal = null;
        }
        return retVal;
    }
}