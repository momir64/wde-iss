package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecommendedCategoriesDTO {
    private List<ListingCategoryDTO> serviceCategories;
    private List<ListingCategoryDTO> productCategories;
}
