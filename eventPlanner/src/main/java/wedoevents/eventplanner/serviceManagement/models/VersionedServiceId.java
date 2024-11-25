package wedoevents.eventplanner.serviceManagement.models;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VersionedServiceId implements Serializable {
    @Column(name = "static_service_id", columnDefinition = "uuid")
    private UUID staticServiceId;
    private Integer version;
}
