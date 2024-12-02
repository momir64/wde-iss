package wedoevents.eventplanner.listingManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    public List<ListingCategoryDTO> getAllListingCategories() {
        List<ListingCategoryDTO> listingCategories = new ArrayList<>();


        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        for (ProductCategory productCategory : productCategories) {
            ListingCategoryDTO dto = mapToListingCategoryDTO(productCategory, ListingType.PRODUCT);
            listingCategories.add(dto);
        }

        List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll();
        for (ServiceCategory serviceCategory : serviceCategories) {
            ListingCategoryDTO dto = mapToListingCategoryDTO(serviceCategory, ListingType.SERVICE);
            listingCategories.add(dto);
        }

        return listingCategories;
    }
    private ListingCategoryDTO mapToListingCategoryDTO(Object category, ListingType listingType) {
        ListingCategoryDTO dto = new ListingCategoryDTO();

        if (category instanceof ProductCategory) {
            ProductCategory productCategory = (ProductCategory) category;
            dto.setId(productCategory.getId());
            dto.setName(productCategory.getName());
            dto.setIsPending(productCategory.getIsPending());
            dto.setDescription(productCategory.getDescription());
            dto.setIsDeleted(productCategory.getIsDeleted());
        } else if (category instanceof ServiceCategory) {
            ServiceCategory serviceCategory = (ServiceCategory) category;
            dto.setId(serviceCategory.getId());
            dto.setName(serviceCategory.getName());
            dto.setIsPending(serviceCategory.getIsPending());
            dto.setDescription(serviceCategory.getDescription());
            dto.setIsDeleted(serviceCategory.getIsDeleted());
        }

        dto.setListingType(listingType); // Set the type (PRODUCT or SERVICE)
        return dto;
    }
}
