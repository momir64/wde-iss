package wedoevents.eventplanner.userManagement.models.userTypes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;
    private String name;
    private String surname;

    @OneToOne(optional = false)
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "guest_favourite_events",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> favouriteEvents;

    @ManyToMany
    @JoinTable(
            name = "guest_invited_events",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> invitedEvents;

    @ManyToMany
    @JoinTable(
            name = "guest_accepted_events",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> acceptedEvents;
}
