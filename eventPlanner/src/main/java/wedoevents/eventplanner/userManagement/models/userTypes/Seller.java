package wedoevents.eventplanner.userManagement.models.userTypes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.shared.models.City;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;
    private String surname;
    private String address;
    private String telephoneNumber;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city", referencedColumnName = "name")
    private City city;

    @OneToOne(optional = false)
    private Profile profile;

    @OneToMany
    @JoinColumn(name = "seller_id")
    private List<StaticProduct> myProducts;

    @OneToMany
    @JoinColumn(name = "seller_id")
    private List<StaticService> myServices;
}
