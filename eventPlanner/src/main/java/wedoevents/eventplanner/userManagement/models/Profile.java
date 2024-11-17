package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.productManagement.models.Product;
import wedoevents.eventplanner.serviceManagement.models.ServiceEntity;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> favouriteProducts;

    @ManyToMany
    @JoinTable(
            name = "profile_favourite_services",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<ServiceEntity> favouriteServices;


    public void BuildProfile(String email, String password, boolean isActive, boolean areNotificationsMuted, boolean isVerified) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.areNotificationsMuted = areNotificationsMuted;
        this.isVerified = isVerified;
    }

}