package wedoevents.eventplanner.userManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.productManagement.models.Product;
import wedoevents.eventplanner.serviceManagement.models.ServiceEntity;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    private UUID id;

    private int grade;
    private String comment;

    @Enumerated(EnumType.STRING)
    private PendingStatus pendingStatus;

    // one of these two will always be non-null

    @ManyToOne()
    private ServiceEntity service;
    @ManyToOne()
    private Product product;

    @ManyToOne(optional = false)
    private EventOrganizer eventOrganizer;
}
