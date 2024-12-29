package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;


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
    private Double oldPrice;
    private List<String> images;
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

    private Double rating;


    public static VersionedServiceDTO toDto(VersionedService versionedService) {
        return toDto(versionedService, 0.0);
    }

    public static VersionedServiceDTO toDto(VersionedService versionedService, Double rating) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();

        return new VersionedServiceDTO(
                versionedService.getStaticServiceId(),
                versionedService.getVersion(),
                versionedService.getStaticService().getServiceCategory().getId(),
                versionedService.getName(),
                versionedService.getSalePercentage() != null ? versionedService.getPrice() : null,
                versionedService.getImages()
                        .stream()
                        .map(uniqName -> String.format("%s/api/v1/services/%s/%d/images/%s", baseUrl, versionedService.getStaticServiceId(), versionedService.getVersion(), uniqName))
                        .sorted()
                        .toList(),
                versionedService.getDescription(),
                versionedService.getIsPrivate(),
                versionedService.getIsAvailable(),
                versionedService.getCancellationDeadline(),
                versionedService.getReservationDeadline(),
                versionedService.getIsActive(),
                versionedService.getIsConfirmationManual(),
                versionedService.getSalePercentage() != null ? (1 - versionedService.getSalePercentage()) * versionedService.getPrice() : null,
                versionedService.getMinimumDuration(),
                versionedService.getMaximumDuration(),
                versionedService.getAvailableEventTypes().stream().map(EventType::getId).toList(),
                rating
        );
    }
}
