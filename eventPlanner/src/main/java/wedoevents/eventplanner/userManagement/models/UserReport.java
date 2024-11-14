package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reportDateTime;

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