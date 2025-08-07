package wedoevents.eventplanner.serviceManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@IdClass(VersionedServiceId.class)
@NoArgsConstructor
public class VersionedService {

    @Id
    private UUID staticServiceId;

    @Id
    private Integer version;

    private String name;
    private String description;

    private Double price;
    private Double salePercentage;

    @ElementCollection
    private List<String> images;

    private Boolean isPrivate;
    private Boolean isAvailable;

    private Integer cancellationDeadline;
    private Integer reservationDeadline;

    private Boolean isActive;
    private Boolean isConfirmationManual;
    private Boolean isLastVersion;

    private Integer minimumDuration;
    private Integer maximumDuration;

    @MapsId("static_service_id")
    @JoinColumns({
            @JoinColumn(name = "static_service_id", referencedColumnName = "static_service_id")
    })
    @ManyToOne
    private StaticService staticService;

    @ManyToMany
    @JoinTable(
            name = "versioned_service_eventtype",
            joinColumns = {@JoinColumn(name = "versioned_service_static_service_id"), @JoinColumn(name = "versioned_service_version")},
            inverseJoinColumns = @JoinColumn(name = "eventtype_id")
    )
    private List<EventType> availableEventTypes;

    public void incrementVersion() {
        this.version += 1;
    }

    public VersionedService(VersionedService from) {
        this.staticServiceId = from.staticServiceId;
        this.staticService = from.staticService;
        this.version = from.version;
        this.cancellationDeadline = from.cancellationDeadline;
        this.reservationDeadline = from.reservationDeadline;
        this.description = from.description;
        this.name = from.name;
        this.isActive = from.isActive;
        this.isAvailable = from.isAvailable;
        this.isConfirmationManual = from.isConfirmationManual;
        this.isPrivate = from.isPrivate;
        this.minimumDuration = from.minimumDuration;
        this.maximumDuration = from.maximumDuration;
        this.price = from.price;
        this.salePercentage = from.salePercentage;
        this.isLastVersion = from.isLastVersion;
        this.availableEventTypes = from.availableEventTypes;

        // images shouldn't be copied because storing them is based per version
        this.images = new ArrayList<>();
    }
}