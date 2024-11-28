package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateVersionedServiceDTO {
    private UUID staticServiceId;

    private String name;
    private Double salePercentage;
    private List<String> images;
    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private Integer duration;
    private Integer cancellationDeadline;
    private Integer reservationDeadline;
    private Boolean isActive;
    private Boolean isConfirmationManual;
    private Double price;

    private List<UUID> availableEventTypeIds;
}
