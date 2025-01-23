package wedoevents.eventplanner.userManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.ChatMessage;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageDTO {
    private String message;
    private UUID toProfileId;

    public static ChatMessageDTO toDto(ChatMessage chatMessage) {
        return new ChatMessageDTO(
                chatMessage.getMessage(),
                chatMessage.getTo().getId()
        );
    }
}
