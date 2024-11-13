package wedoevents.eventplanner.notificationManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.userManagement.models.Profile;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue
    private UUID id;

    private boolean isSeen;
    private LocalDate date;
    private String message;
    private String title;
    private String webRedirect;
    private String mobileRedirect;

    @ManyToOne(optional = false)
    private Profile user;

    // only one of these three will be non-null

    @ManyToOne()
    private ServiceCategory serviceCategory;
    @ManyToOne()
    private ProductCategory productCategory;
    @ManyToOne()
    private Event event;
}
