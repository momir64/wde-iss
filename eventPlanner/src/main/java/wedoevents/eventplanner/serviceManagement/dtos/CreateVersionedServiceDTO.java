package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateVersionedServiceDTO {
    private UUID serviceCategoryId;

    private String name;
    private Double salePercentage;
    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private Integer cancellationDeadline;
    private Integer reservationDeadline;
    private Boolean isActive;
    private Boolean isConfirmationManual;
    private Double price;
    private Integer minimumDuration;
    private Integer maximumDuration;

    private List<UUID> availableEventTypeIds;
}
