package wedoevents.eventplanner.listingManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.CreateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.ReplacingListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.UpdateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;
import wedoevents.eventplanner.productManagement.repositories.StaticProductRepository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;
import wedoevents.eventplanner.serviceManagement.repositories.StaticServiceRepository;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ListingCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private StaticServiceRepository staticServiceRepository;

    @Autowired
    private StaticProductRepository staticProductRepository;

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
            if (foundServiceCategory.getIsDeleted()) {
                throw new EntityNotFoundException();
            }

            foundServiceCategory.setDescription(updateListingCategoryDTO.getDescription());
            foundServiceCategory.setName(updateListingCategoryDTO.getName());

            newDTO = ListingCategoryDTO.fromServiceCategory(serviceCategoryRepository.save(foundServiceCategory));
        } else if (foundProductCategory != null) {
            if (foundProductCategory.getIsDeleted()) {
                throw new EntityNotFoundException();
            }

            foundProductCategory.setDescription(updateListingCategoryDTO.getDescription());
            foundProductCategory.setName(updateListingCategoryDTO.getName());

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

    public void deleteListingCategory(UUID id) {
        ProductCategory foundProductCategory = productCategoryRepository.findById(id).orElse(null);
        ServiceCategory foundServiceCategory = serviceCategoryRepository.findById(id).orElse(null);

        if (foundProductCategory != null) {
            if (productCategoryRepository.hasAssociatedProducts(foundProductCategory.getId())) {
                throw new EntityCannotBeDeletedException();
            }
            foundProductCategory.setIsDeleted(true);
            productCategoryRepository.save(foundProductCategory);
        } else if (foundServiceCategory != null) {
            if (serviceCategoryRepository.hasAssociatedServices(foundServiceCategory.getId())) {
                throw new EntityCannotBeDeletedException();
            }
            foundServiceCategory.setIsDeleted(true);
            serviceCategoryRepository.save(foundServiceCategory);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public ListingCategoryDTO approveListingCategory(UUID id) {
        ProductCategory foundProductCategory = productCategoryRepository.findById(id).orElse(null);
        ServiceCategory foundServiceCategory = serviceCategoryRepository.findById(id).orElse(null);

        if (foundProductCategory != null) {
            foundProductCategory.setIsPending(false);
            productCategoryRepository.setAssociatedProductToPendingFalse(foundProductCategory);
            return ListingCategoryDTO.fromProductCategory(productCategoryRepository.save(foundProductCategory));
        } else if (foundServiceCategory != null) {
            foundServiceCategory.setIsPending(false);
            serviceCategoryRepository.setAssociatedServiceToPendingFalse(foundServiceCategory);
            return ListingCategoryDTO.fromServiceCategory(serviceCategoryRepository.save(foundServiceCategory));
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void replaceListingCategory(ReplacingListingCategoryDTO replacingListingCategoryDTO) {
        if (replacingListingCategoryDTO.getListingType() == ListingType.SERVICE) {
            ServiceCategory foundToBeReplacedServiceCategory = serviceCategoryRepository.findById(replacingListingCategoryDTO.getToBeReplacedId()).orElse(null);
            ServiceCategory foundReplacingServiceCategory = serviceCategoryRepository.findById(replacingListingCategoryDTO.getReplacingId()).orElse(null);

            if (foundReplacingServiceCategory == null || foundToBeReplacedServiceCategory == null) {
                throw new EntityNotFoundException();
            }

            if (foundReplacingServiceCategory.getIsDeleted()) {
                throw new EntityNotFoundException();
            }

            // put all services that have toBeReplaced service category to replacing service category
            // put all services' pending status to false
            // physically remove toBeReplaced service category
            staticServiceRepository.replacePendingServiceCategory(foundToBeReplacedServiceCategory,
                                                                  foundReplacingServiceCategory);
            serviceCategoryRepository.setAssociatedServiceToPendingFalse(foundReplacingServiceCategory);
            serviceCategoryRepository.removeServiceCategoryById(replacingListingCategoryDTO.getToBeReplacedId());
        } else {
            ProductCategory foundToBeReplacedProductCategory = productCategoryRepository.findById(replacingListingCategoryDTO.getToBeReplacedId()).orElse(null);
            ProductCategory foundReplacingProductCategory = productCategoryRepository.findById(replacingListingCategoryDTO.getReplacingId()).orElse(null);

            if (foundReplacingProductCategory == null || foundToBeReplacedProductCategory == null) {
                throw new EntityNotFoundException();
            }

            if (foundReplacingProductCategory.getIsDeleted()) {
                throw new EntityNotFoundException();
            }

            // put all products that have toBeReplaced product category to replacing product category
            // put all products' pending status to false
            // physically remove toBeReplaced product category

            staticProductRepository.replacePendingProductCategory(foundToBeReplacedProductCategory,
                                                                  foundReplacingProductCategory);
            productCategoryRepository.setAssociatedProductToPendingFalse(foundReplacingProductCategory);
            productCategoryRepository.removeProductCategoryById(replacingListingCategoryDTO.getToBeReplacedId());
        }
    }
}
