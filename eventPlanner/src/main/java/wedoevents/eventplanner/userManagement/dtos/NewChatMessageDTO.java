package wedoevents.eventplanner.userManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewChatMessageDTO {
    private String message;
    private UUID chatId;
    private UUID toProfileId;
}
