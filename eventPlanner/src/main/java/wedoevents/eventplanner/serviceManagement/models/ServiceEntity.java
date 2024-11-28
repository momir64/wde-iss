package wedoevents.eventplanner.serviceManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;
    private Boolean sale;

    @ElementCollection
    private List<String> images;

    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private int duration;

    private LocalDate cancellationDeadline;
    private LocalDate reservationDeadline;

    private Boolean isActive;
    private Boolean isConfirmationManual;

    private double price;
    private int version;

    private LocalTime openingTime;
    private LocalTime closingTime;

    @ManyToOne
    private ServiceCategory serviceCategory;

    @ManyToMany
    @JoinTable(
            name = "service_eventtype",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "eventtype_id")
    )
    private List<EventType> availableEventTypes;
}