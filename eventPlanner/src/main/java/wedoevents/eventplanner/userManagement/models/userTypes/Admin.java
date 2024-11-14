package wedoevents.eventplanner.userManagement.models.userTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Admin {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    private Profile profile;
}
