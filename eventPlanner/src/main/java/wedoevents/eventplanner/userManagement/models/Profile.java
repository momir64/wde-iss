package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Profile {

    @Id
    @GeneratedValue()
    private UUID id;

    private String email;

    private String password;

    private boolean isActive;

    private boolean areNotificationsMuted;

    private boolean isVerified;

    @ManyToMany
    @JoinTable(
            name = "profile_blocked_users",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id")
    )
    private List<Profile> blockedUsers;

    @ManyToMany
    @JoinTable(
            name = "profile_favourite_events",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> favouriteEvents;

    @ManyToMany
    @JoinTable(
            name = "profile_favourite_products",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "static_product_id")

    )
    private List<StaticProduct> favouriteProducts;

    @ManyToMany
    @JoinTable(
            name = "profile_favourite_services",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "static_service_id")
    )
    private List<StaticService> favouriteServices;
}