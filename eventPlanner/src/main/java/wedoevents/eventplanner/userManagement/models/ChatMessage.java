package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private LocalDateTime time;

    private String message;

    private boolean isSeen;

    @ManyToOne
    @JoinColumn(name = "to_profile_id", nullable = false)
    private Profile to;

    @ManyToOne
    @JoinColumn(name = "from_profile_id", nullable = false)
    private Profile from;
}