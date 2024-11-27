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
public class Seller {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String surname;
    private String city;
    private String address;
    private String postalNumber;
    private String telephoneNumber;
    private String description;

    @OneToOne(optional = false)
    private Profile profile;

    private String profileImage;
}
