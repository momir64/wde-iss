package wedoevents.eventplanner.productManagement.services;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.services.EventTypeService;
import wedoevents.eventplanner.productManagement.dtos.CreateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.UpdateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductDTO;
import wedoevents.eventplanner.productManagement.models.*;
import wedoevents.eventplanner.productManagement.repositories.StaticProductRepository;
import wedoevents.eventplanner.productManagement.repositories.VersionedProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private VersionedProductRepository versionedProductRepository;

    @Autowired
    private StaticProductRepository staticProductRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EventTypeService eventTypeService;

    private VersionedProduct incrementProductVersionAndSave(VersionedProduct versionedProduct) {
        entityManager.detach(versionedProduct);
        versionedProduct.incrementVersion();

        return versionedProductRepository.save(versionedProduct);
    }

    public List<VersionedProductDTO> getAllProductsWithLatestVersions() {
        return versionedProductRepository.getAllVersionedProductsWithMaxVersions().stream().map(
                VersionedProductDTO::toDto
        ).toList();
    }

    public VersionedProductDTO createProduct(CreateVersionedProductDTO createProductDTO) {
        // todo backend check if the static product id exists
        VersionedProduct newVersionedProduct;
        ProductCategory matchingProductCategory = productCategoryService.getProductCategoryById(
                createProductDTO.getProductCategoryId()
        );

        StaticProduct newMatchingStaticProduct = new StaticProduct();
        newMatchingStaticProduct.setProductCategory(matchingProductCategory);
        staticProductRepository.save(newMatchingStaticProduct);

        newVersionedProduct = new VersionedProduct();
        newVersionedProduct.setStaticProduct(newMatchingStaticProduct);
        newVersionedProduct.setStaticProductId(newMatchingStaticProduct.getStaticProductId());
        newVersionedProduct.setVersion(1);
        newVersionedProduct.setIsLastVersion(true);

        newVersionedProduct.setName(createProductDTO.getName());
        newVersionedProduct.setImages(createProductDTO.getImages());
        newVersionedProduct.setSalePercentage(createProductDTO.getSalePercentage());
        newVersionedProduct.setPrice(createProductDTO.getPrice());
        newVersionedProduct.setIsActive(createProductDTO.getIsActive());
        newVersionedProduct.setIsAvailable(createProductDTO.getIsAvailable());
        newVersionedProduct.setIsPrivate(createProductDTO.getIsPrivate());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : createProductDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedProduct.setAvailableEventTypes(eventTypes);

        // todo: do backend checks on UUIDs of event types
        return VersionedProductDTO.toDto(versionedProductRepository.save(newVersionedProduct));
    }

    public VersionedProductDTO updateVersionedProduct(UpdateVersionedProductDTO updateVersionedProductDTO) {
        // todo backend check if the static product id exists
        VersionedProduct oldVersionOfVersionedProduct = versionedProductRepository.
                getVersionedProductByStaticProductIdAndLatestVersion(updateVersionedProductDTO.getStaticProductId());

        oldVersionOfVersionedProduct.setIsLastVersion(false);

        VersionedProduct newVersionedProduct = versionedProductRepository.save(oldVersionOfVersionedProduct);

        newVersionedProduct.setIsLastVersion(true);
        newVersionedProduct.setName(updateVersionedProductDTO.getName());
        newVersionedProduct.setImages(updateVersionedProductDTO.getImages());
        newVersionedProduct.setSalePercentage(updateVersionedProductDTO.getSalePercentage());
        newVersionedProduct.setPrice(updateVersionedProductDTO.getPrice());
        newVersionedProduct.setIsActive(updateVersionedProductDTO.getIsActive());
        newVersionedProduct.setIsAvailable(updateVersionedProductDTO.getIsAvailable());
        newVersionedProduct.setIsPrivate(updateVersionedProductDTO.getIsPrivate());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : updateVersionedProductDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedProduct.setAvailableEventTypes(eventTypes);

        // todo: do backend checks on UUIDs of event types
        return VersionedProductDTO.toDto(incrementProductVersionAndSave(newVersionedProduct));
    }

    public VersionedProductDTO getVersionedProductById(UUID staticProductId) {
        // todo: null check
        return VersionedProductDTO.toDto(versionedProductRepository.getVersionedProductByStaticProductIdAndLatestVersion(staticProductId));
    }

    public VersionedProductDTO getVersionedProductByStaticProductIdAndVersion(Integer version, UUID staticProductId) {
        // todo: null check
        return VersionedProductDTO.toDto(versionedProductRepository.getVersionedProductByStaticProductIdAndVersion(staticProductId, version));
    }

    public void deactivateProduct(UUID staticProductId) {
        // todo backend check if the static product id exists
        VersionedProduct newVersionedProduct = versionedProductRepository.
                getVersionedProductByStaticProductIdAndLatestVersion(staticProductId);

        newVersionedProduct.setIsAvailable(false);

        incrementProductVersionAndSave(newVersionedProduct);
    }
}