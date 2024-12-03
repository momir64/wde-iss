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
public class ListingDTO {
    private ListingType  type;
    private UUID         id;
    private Integer      version;
    private String       name;
    private String       description;
    private Double       price;
    private Double       rating;
    private List<String> images;
    private Double       salePercentage;
    private Boolean      isAvailable;
}
