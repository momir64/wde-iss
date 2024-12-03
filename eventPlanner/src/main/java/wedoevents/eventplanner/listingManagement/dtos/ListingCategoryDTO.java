package wedoevents.eventplanner.listingManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ListingCategoryDTO {
    private UUID id;

    private String name;
    private Boolean isPending;
    private String description;
    private Boolean isDeleted;
    private ListingType listingType;
    
    public static ListingCategoryDTO fromServiceCategory(ServiceCategory serviceCategory) {
        return new ListingCategoryDTO(
                serviceCategory.getId(),
                serviceCategory.getName(),
                serviceCategory.getIsPending(),
                serviceCategory.getDescription(),
                serviceCategory.getIsDeleted(),
                ListingType.SERVICE
        );
    }

    public static ListingCategoryDTO fromProductCategory(ProductCategory productCategory) {
        return new ListingCategoryDTO(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getIsPending(),
                productCategory.getDescription(),
                productCategory.getIsDeleted(),
                ListingType.PRODUCT
        );
    }
}
