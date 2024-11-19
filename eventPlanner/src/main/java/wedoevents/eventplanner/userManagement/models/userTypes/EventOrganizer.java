package wedoevents.eventplanner.userManagement.models.userTypes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class EventOrganizer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;
    private String surname;
    private String city;
    private String address;
    private String postalNumber;
    private String telephoneNumber;

    @OneToOne(optional = false)
    private Profile profile;

    private String image;
}
