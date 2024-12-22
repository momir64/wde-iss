package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogueServiceDTO {
    private UUID serviceId;
    private String name;
    private Double price;
    private Double salePercentage;

    public static CatalogueServiceDTO toDto(VersionedService versionedService) {
        return new CatalogueServiceDTO(
                versionedService.getStaticServiceId(),
                versionedService.getName(),
                versionedService.getPrice(),
                versionedService.getSalePercentage()
        );
    }
}
