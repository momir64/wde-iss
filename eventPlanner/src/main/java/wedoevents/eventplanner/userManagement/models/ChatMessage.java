package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue()
    private UUID id;

    private LocalDateTime time;

    private String message;

    private Boolean isSeen;

    @ManyToOne
    @JoinColumn(name = "to_profile_id", nullable = false)
    private Profile to;

    public ChatMessage(LocalDateTime time, String message, boolean isSeen, Profile to) {
        this.time = time;
        this.message = message;
        this.isSeen = isSeen;
        this.to = to;
    }
}