package wedoevents.eventplanner.userManagement.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
public class UserReport {

    @Id
    @GeneratedValue()
    private UUID id;

    private LocalDateTime reportDateTime;

    @Nullable
    private LocalDateTime banStartDateTime;

    @Enumerated(EnumType.STRING)
    private PendingStatus status;

    @ManyToOne
    @JoinColumn(name = "to_profile_id", nullable = false)
    private Profile to;

    @ManyToOne
    @JoinColumn(name = "from_profile_id", nullable = false)
    private Profile from;
}