package wedoevents.eventplanner.listingManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.CreateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.UpdateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ListingCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    public List<ListingCategoryDTO> getAllActiveListingCategories() {
        List<ListingCategoryDTO> listingCategories = new ArrayList<>();

        List<ProductCategory> productCategories = productCategoryRepository.findAllByIsPendingFalse();
        for (ProductCategory productCategory : productCategories) {
            ListingCategoryDTO dto = ListingCategoryDTO.fromProductCategory(productCategory);
            listingCategories.add(dto);
        }

        List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAllByIsPendingFalse();
        for (ServiceCategory serviceCategory : serviceCategories) {
            ListingCategoryDTO dto = ListingCategoryDTO.fromServiceCategory(serviceCategory);
            listingCategories.add(dto);
        }

        return listingCategories;
    }

    public List<ListingCategoryDTO> getAllPendingListingCategories() {
        List<ListingCategoryDTO> listingCategories = new ArrayList<>();

        List<ProductCategory> productCategories = productCategoryRepository.findAllByIsPendingTrue();
        for (ProductCategory productCategory : productCategories) {
            ListingCategoryDTO dto = ListingCategoryDTO.fromProductCategory(productCategory);
            listingCategories.add(dto);
        }

        List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAllByIsPendingTrue();
        for (ServiceCategory serviceCategory : serviceCategories) {
            ListingCategoryDTO dto = ListingCategoryDTO.fromServiceCategory(serviceCategory);
            listingCategories.add(dto);
        }

        return listingCategories;
    }

    public ListingCategoryDTO updateListingCategory(UUID id, UpdateListingCategoryDTO updateListingCategoryDTO) {
        ProductCategory foundProductCategory = productCategoryRepository.findById(id).orElse(null);
        ServiceCategory foundServiceCategory = serviceCategoryRepository.findById(id).orElse(null);

        ListingCategoryDTO newDTO;

        if (foundServiceCategory != null) {
            foundServiceCategory.setDescription(updateListingCategoryDTO.getDescription());
            foundServiceCategory.setName(updateListingCategoryDTO.getName());
            foundServiceCategory.setIsPending(updateListingCategoryDTO.getIsPending());

            newDTO = ListingCategoryDTO.fromServiceCategory(serviceCategoryRepository.save(foundServiceCategory));
        } else if (foundProductCategory != null) {
            foundProductCategory.setDescription(updateListingCategoryDTO.getDescription());
            foundProductCategory.setName(updateListingCategoryDTO.getName());
            foundProductCategory.setIsPending(updateListingCategoryDTO.getIsPending());

            newDTO = ListingCategoryDTO.fromProductCategory(productCategoryRepository.save(foundProductCategory));
        } else {
            throw new EntityNotFoundException();
        }

        return newDTO;
    }
    
    public ListingCategoryDTO createListingCategory(CreateListingCategoryDTO createListingCategoryDTO) {
        if (createListingCategoryDTO.getListingType() == ListingType.PRODUCT) {
            ProductCategory newProductCategory = new ProductCategory();
            
            newProductCategory.setDescription(createListingCategoryDTO.getDescription());
            newProductCategory.setName(createListingCategoryDTO.getName());
            newProductCategory.setIsPending(createListingCategoryDTO.getIsPending());
            newProductCategory.setIsDeleted(false);
            
            return ListingCategoryDTO.fromProductCategory(productCategoryRepository.save(newProductCategory));
        } else {
            ServiceCategory newServiceCategory = new ServiceCategory();

            newServiceCategory.setDescription(createListingCategoryDTO.getDescription());
            newServiceCategory.setName(createListingCategoryDTO.getName());
            newServiceCategory.setIsPending(createListingCategoryDTO.getIsPending());
            newServiceCategory.setIsDeleted(false);

            return ListingCategoryDTO.fromServiceCategory(serviceCategoryRepository.save(newServiceCategory));
        }
    }
}
