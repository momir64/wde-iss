package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name; // e.g., "ROLE_USER", "ROLE_ADMIN"

    public String getRoleName() {
        if (name != null && name.startsWith("ROLE_")) {
            return name.substring(5);
        }
        return name;
    }
}
