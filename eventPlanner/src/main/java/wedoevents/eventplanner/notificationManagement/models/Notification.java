package wedoevents.eventplanner.notificationManagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private boolean isSeen;
    private LocalDateTime time;
    private String title;
    private String message;
    private String webRedirect;

    private NotificationType type;
    private UUID entityId;

    @ManyToOne(optional = false)
    private Profile profile;
}
