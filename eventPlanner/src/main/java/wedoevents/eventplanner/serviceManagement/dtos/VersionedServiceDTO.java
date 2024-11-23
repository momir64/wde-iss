package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class VersionedServiceDTO {
    private UUID staticServiceId;
    private Integer version;
    private UUID serviceCategoryId;

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

    public static VersionedServiceDTO toDto(VersionedService versionedService) {
        return new VersionedServiceDTO(
                versionedService.getStaticServiceId(),
                versionedService.getVersion(),
                versionedService.getStaticService().getServiceCategory().getId(),
                versionedService.getName(),
                versionedService.getSalePercentage(),
                versionedService.getImages(),
                versionedService.getDescription(),
                versionedService.getIsPrivate(),
                versionedService.getIsAvailable(),
                versionedService.getDuration(),
                versionedService.getCancellationDeadline(),
                versionedService.getReservationDeadline(),
                versionedService.getIsActive(),
                versionedService.getIsConfirmationManual(),
                versionedService.getPrice(),
                versionedService.getAvailableEventTypes().stream().map(EventType::getId).toList()
        );
    }
}
