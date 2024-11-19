package wedoevents.eventplanner.userManagement.models.userTypes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.Profile;

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
}
