package wedoevents.eventplanner.serviceManagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VersionedServiceImage {
    @Id
    @GeneratedValue
    private UUID imageId;

    private String imageLocation;

    public VersionedServiceImage(String imageLocation) {
        this.imageLocation = imageLocation;
    }
}
