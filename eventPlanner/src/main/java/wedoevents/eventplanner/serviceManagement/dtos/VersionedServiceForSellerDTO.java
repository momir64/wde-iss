package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.models.VersionedServiceImage;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class VersionedServiceForSellerDTO {
    private UUID staticServiceId;
    private Integer version;
    private UUID serviceCategoryId;

    private String name;
    private Double salePercentage;
    private List<byte[]> images;
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

    public static VersionedServiceForSellerDTO toDto(VersionedService versionedService, ImageService imageService) throws IOException {
        List<byte[]> images = new ArrayList<>();

        for (String image : versionedService.getImages()) {
            images.add(imageService.getImage(image, new ImageLocationConfiguration(
                    "service", versionedService.getStaticServiceId(), versionedService.getVersion())
            ).get());
        }

        return new VersionedServiceForSellerDTO(
                versionedService.getStaticServiceId(),
                versionedService.getVersion(),
                versionedService.getStaticService().getServiceCategory().getId(),
                versionedService.getName(),
                versionedService.getSalePercentage(),
                images,
                versionedService.getDescription(),
                versionedService.getIsPrivate(),
                versionedService.getIsAvailable(),
                versionedService.getCancellationDeadline(),
                versionedService.getReservationDeadline(),
                versionedService.getIsActive(),
                versionedService.getIsConfirmationManual(),
                versionedService.getPrice(),
                versionedService.getMinimumDuration(),
                versionedService.getMaximumDuration(),
                versionedService.getAvailableEventTypes().stream().map(EventType::getId).toList()
        );
    }
}
