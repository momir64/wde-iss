package wedoevents.eventplanner.userManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatMessageDTO {
    private UUID id;
    private LocalDateTime time;
    private String message;
    private boolean isSeen;
    private UUID userProfileTo;
    private UUID userProfileFrom;
}
