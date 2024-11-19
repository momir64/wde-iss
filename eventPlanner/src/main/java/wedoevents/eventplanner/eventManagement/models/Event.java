package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String city;
    private Boolean isPublic;
    private String name;
    private String description;
    private LocalDate date;
    private int guestCount;

    @ManyToOne(optional = false)
    private EventType eventType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<ProductBudgetItem> productBudgetItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<ServiceBudgetItem> serviceBudgetItems;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<EventActivity> eventActivities;
}