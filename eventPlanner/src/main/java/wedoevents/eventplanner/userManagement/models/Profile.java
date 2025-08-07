package wedoevents.eventplanner.userManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Profile implements UserDetails {

    @Id
    @GeneratedValue()
    private UUID id;

    private String email;

    private String password;

    private boolean isActive;

    private boolean areNotificationsMuted;

    private String imageName;

    private boolean isVerified;

    @ManyToMany
    @JoinTable(
            name = "profile_blocked_users",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id")
    )
    private List<Profile> blockedUsers;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    public void BuildProfile(String email, String password, boolean isActive, boolean areNotificationsMuted, boolean isVerified, Role role) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.areNotificationsMuted = areNotificationsMuted;
        this.isVerified = isVerified;
        this.role = role;
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }




    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}