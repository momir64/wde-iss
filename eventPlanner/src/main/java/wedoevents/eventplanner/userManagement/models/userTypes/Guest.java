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

    @OneToOne(optional = false)
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "guest_favourite_events",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> favouriteEvents;
}
