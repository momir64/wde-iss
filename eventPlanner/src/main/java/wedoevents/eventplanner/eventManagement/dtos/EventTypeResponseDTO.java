package wedoevents.eventplanner.eventManagement.dtos;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.productManagement.dtos.ProductCategoryDTO;
import wedoevents.eventplanner.serviceManagement.dtos.ServiceCategoryDTO;

import java.util.List;
import java.util.UUID;
@Setter
@Getter
public class EventTypeResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Boolean isActive;
    private List<ListingCategoryDTO> listingCategories;
}
