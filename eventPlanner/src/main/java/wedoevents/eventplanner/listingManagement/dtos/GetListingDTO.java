package wedoevents.eventplanner.listingManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetListingDTO {
    private ListingType  type;
    private UUID         id;
    private Integer      version;
    private String       name;
    private String       description;
    private Double       price;
    private Double       salePercentage;
    private List<String> images;
    private Boolean      isAvailable;
}
