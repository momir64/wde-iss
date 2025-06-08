package wedoevents.eventplanner.eventManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.shared.models.City;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city", referencedColumnName = "name")
    private City city;

    private String address;
    private Boolean isPublic;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private Integer guestCount;

    @ManyToOne(optional = false)
    private EventType eventType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<ProductBudgetItem> productBudgetItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<ServiceBudgetItem> serviceBudgetItems;

    @Embedded
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<EventActivity> eventActivities;

    @ElementCollection
    private List<String> images;

    public List<ServiceBudgetItem> getNonEmptyServiceBudgetItems(){
        return this.serviceBudgetItems.stream()
                .filter(item -> item.getService() != null)
                .collect(Collectors.toList());
    }
    public List<ProductBudgetItem> getNonEmptyProductBudgetItems(){
        return this.productBudgetItems.stream()
                .filter(item -> item.getProduct() != null)
                .collect(Collectors.toList());
    }
}