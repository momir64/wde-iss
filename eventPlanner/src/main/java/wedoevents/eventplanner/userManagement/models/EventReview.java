package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import java.util.UUID;

@Entity
@Getter
@Setter
public class EventReview {
    @Id
    @GeneratedValue
    private UUID id;

    private Integer grade;
    private String comment;

    @Enumerated(EnumType.STRING)
    private PendingStatus pendingStatus;

    @ManyToOne(optional = false)
    private Event event;

    @ManyToOne(optional = false)
    private Guest guest;
}
