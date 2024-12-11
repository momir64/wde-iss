package wedoevents.eventplanner.userManagement.models.userTypes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class EventOrganizer {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String surname;
    private String city;
    private String address;
    private String telephoneNumber;

    @OneToOne(optional = false)
    private Profile profile;


    @ManyToMany
    @JoinTable(
            name = "event_organizer_favourite_products",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "static_product_id")

    )
    private List<StaticProduct> favouriteProducts;

    @ManyToMany
    @JoinTable(
            name = "event_organizer_favourite_services",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "static_service_id")
    )
    private List<StaticService> favouriteServices;

    @OneToMany
    @JoinColumn(name = "event_organizer_id")
    private List<Event> myEvents;
}