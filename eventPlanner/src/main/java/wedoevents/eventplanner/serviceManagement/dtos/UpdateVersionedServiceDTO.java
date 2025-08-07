package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UpdateVersionedServiceDTO {
    private UUID staticServiceId;

    private String name;
    private Double salePercentage;
    private String description;
    private Boolean isPrivate;
    private Boolean isAvailable;
    private Integer cancellationDeadline;
    private Integer reservationDeadline;
    private Boolean isConfirmationManual;
    private Double price;
    private Integer minimumDuration;
    private Integer maximumDuration;

    private List<UUID> availableEventTypeIds;
}
