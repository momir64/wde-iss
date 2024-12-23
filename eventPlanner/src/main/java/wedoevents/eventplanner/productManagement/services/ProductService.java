package wedoevents.eventplanner.productManagement.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.services.EventTypeService;
import wedoevents.eventplanner.productManagement.dtos.*;
import wedoevents.eventplanner.productManagement.models.*;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;
import wedoevents.eventplanner.productManagement.repositories.StaticProductRepository;
import wedoevents.eventplanner.productManagement.repositories.VersionedProductRepository;
import wedoevents.eventplanner.shared.Exceptions.UpdatePriceException;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private VersionedProductRepository versionedProductRepository;

    @Autowired
    private StaticProductRepository staticProductRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SellerRepository sellerRepository;

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

    public List<CatalogueProductDTO> getAllProductsLatestVersionsFromSellerForCatalogue(UUID sellerId) {
        return versionedProductRepository.getAllVersionedProductsWithMaxVersionsFromSeller(sellerId).stream().map(
                CatalogueProductDTO::toDto
        ).toList();
    }

    public VersionedProductDTO createProduct(CreateVersionedProductDTO createVersionedProductDTO, MultipartFile[] images) throws IOException {
        // todo check for fields that must be non-null

        Optional<Seller> sellerMaybe = sellerRepository.findById(createVersionedProductDTO.getSellerId());

        if (sellerMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Seller seller = sellerMaybe.get();

        ProductCategory matchingProductCategory;
        if (createVersionedProductDTO.getSuggestedCategory() == null) {
            Optional<ProductCategory> productCategoryMaybe = productCategoryRepository.findById(
                    createVersionedProductDTO.getProductCategoryId()
            );

            if (productCategoryMaybe.isEmpty()) {
                throw new EntityNotFoundException();
            }

            matchingProductCategory = productCategoryMaybe.get();
        } else {
            matchingProductCategory = new ProductCategory();
            matchingProductCategory.setName(createVersionedProductDTO.getSuggestedCategory());
            matchingProductCategory.setDescription(createVersionedProductDTO.getSuggestedCategoryDescription());
            matchingProductCategory.setIsPending(true);
            matchingProductCategory.setIsDeleted(false);

            matchingProductCategory = productCategoryRepository.save(matchingProductCategory);
        }

        StaticProduct newMatchingStaticProduct = new StaticProduct();
        
        newMatchingStaticProduct.setProductCategory(matchingProductCategory);
        newMatchingStaticProduct.setPending(matchingProductCategory.getIsPending());
        newMatchingStaticProduct = staticProductRepository.save(newMatchingStaticProduct);

        seller.getMyProducts().add(newMatchingStaticProduct);
        sellerRepository.save(seller);

        VersionedProduct newVersionedProduct = new VersionedProduct();
        newVersionedProduct.setStaticProduct(newMatchingStaticProduct);
        newVersionedProduct.setStaticProductId(newMatchingStaticProduct.getStaticProductId());
        newVersionedProduct.setVersion(1);
        newVersionedProduct.setIsLastVersion(true);

        newVersionedProduct.setName(createVersionedProductDTO.getName());
        newVersionedProduct.setDescription(createVersionedProductDTO.getDescription());
        newVersionedProduct.setSalePercentage(createVersionedProductDTO.getSalePercentage());
        newVersionedProduct.setPrice(createVersionedProductDTO.getPrice());
        newVersionedProduct.setIsActive(true);
        newVersionedProduct.setIsAvailable(createVersionedProductDTO.getIsAvailable());
        newVersionedProduct.setIsPrivate(createVersionedProductDTO.getIsPrivate());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : createVersionedProductDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedProduct.setAvailableEventTypes(eventTypes);

        newVersionedProduct = versionedProductRepository.save(newVersionedProduct);

        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : images) {
            imageNames.add(imageService.saveImageToStorage(imageFile, new ImageLocationConfiguration(
                            "product",
                            newMatchingStaticProduct.getStaticProductId(),
                            1
                    )
            ));
        }
        
        newVersionedProduct.setImages(imageNames);

        // todo: do backend checks on UUIDs of event types
        return VersionedProductDTO.toDto(versionedProductRepository.save(newVersionedProduct));
    }

    public VersionedProductDTO updateVersionedProduct(UpdateVersionedProductDTO updateVersionedProductDTO, MultipartFile[] images) throws IOException {
        // todo check for fields that must be non-null

        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository
                .getVersionedProductByStaticProductIdAndLatestVersion(updateVersionedProductDTO.getStaticProductId());

        // todo prevent editing deleted services

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        VersionedProduct oldVersionOfVersionedProduct = versionedProductMaybe.get();

        oldVersionOfVersionedProduct.setIsLastVersion(false);

        oldVersionOfVersionedProduct = versionedProductRepository.save(oldVersionOfVersionedProduct);

        VersionedProduct newVersionedProduct = new VersionedProduct(oldVersionOfVersionedProduct);

        newVersionedProduct.setIsLastVersion(true);
        newVersionedProduct.setName(updateVersionedProductDTO.getName());
        newVersionedProduct.setDescription(updateVersionedProductDTO.getDescription());
        newVersionedProduct.setSalePercentage(updateVersionedProductDTO.getSalePercentage());
        newVersionedProduct.setPrice(updateVersionedProductDTO.getPrice());
        newVersionedProduct.setIsActive(true);
        newVersionedProduct.setIsAvailable(updateVersionedProductDTO.getIsAvailable());
        newVersionedProduct.setIsPrivate(updateVersionedProductDTO.getIsPrivate());

        List<EventType> eventTypes = new ArrayList<>();
        for (UUID eventTypeId : updateVersionedProductDTO.getAvailableEventTypeIds()) {
            eventTypes.add(eventTypeService.getEventTypeById(eventTypeId));
        }
        newVersionedProduct.setAvailableEventTypes(eventTypes);
        
        newVersionedProduct = incrementProductVersionAndSave(newVersionedProduct);

        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : images) {
            imageNames.add(imageService.saveImageToStorage(imageFile, new ImageLocationConfiguration(
                            "product",
                            updateVersionedProductDTO.getStaticProductId(),
                    oldVersionOfVersionedProduct.getVersion() + 1
                    )
            ));
        }
        newVersionedProduct.setImages(imageNames);

        // todo: do backend checks on UUIDs of event types
        return VersionedProductDTO.toDto(versionedProductRepository.save(newVersionedProduct));
    }

    public VersionedProductDTO getVersionedProductById(UUID staticProductId) {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getVersionedProductByStaticProductIdAndLatestVersion(staticProductId);

        // todo prevent getting deleted products and prevent regular users from getting private products
        
        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return VersionedProductDTO.toDto(versionedProductMaybe.get());
    }

    public VersionedProductForSellerDTO getVersionedProductEditableById(UUID staticProductId) throws IOException {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getVersionedProductByStaticProductIdAndLatestVersion(staticProductId);

        // todo prevent getting deleted services

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return VersionedProductForSellerDTO.toDto(versionedProductMaybe.get(), imageService);
    }

    public VersionedProductDTO getVersionedProductByStaticProductIdAndVersion(Integer version, UUID staticProductId) {
        // todo: null check
        return VersionedProductDTO.toDto(versionedProductRepository.getVersionedProductByStaticProductIdAndVersion(staticProductId, version));
    }

    public void deactivateProduct(UUID staticProductId) {
        // todo pull all versions to deleted, so that no one can access the latest non deleted version
        // todo do the same for private
        // todo potentially add to static product
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getVersionedProductByStaticProductIdAndLatestVersion(staticProductId);

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        VersionedProduct newVersionedProduct = versionedProductMaybe.get();

        newVersionedProduct.setIsAvailable(false);

        incrementProductVersionAndSave(newVersionedProduct);
    }

    public void updateCataloguePrices(UUID sellerId, ToBeUpdatedProductsCatalogueDTO toBeUpdatedProductsCatalogueDTO) {
        Collection<VersionedProduct> productsFromSeller = this.versionedProductRepository.getAllVersionedProductsWithMaxVersionsFromSeller(sellerId);

        for (CatalogueProductDTO newPrice : toBeUpdatedProductsCatalogueDTO.getToBeUpdated()) {
            Optional<VersionedProduct> matchingVersionedProductMaybe =
                    productsFromSeller
                            .stream()
                            .filter(s -> s.getStaticProductId().equals(newPrice.getProductId()))
                            .findFirst();

            if (matchingVersionedProductMaybe.isEmpty()) {
                // todo unauthorized exception
                throw new EntityNotFoundException();
            }

            if (newPrice.getPrice() == null) {
                throw new UpdatePriceException("Price must be set");
            }

            VersionedProduct matchingVersionedProduct = matchingVersionedProductMaybe.get();

            if (!matchingVersionedProduct.getPrice().equals(newPrice.getPrice()) ||
                    !matchingVersionedProduct.getSalePercentage().equals(newPrice.getSalePercentage())) {
                matchingVersionedProduct.setPrice(newPrice.getPrice());
                matchingVersionedProduct.setSalePercentage(newPrice.getSalePercentage());

                // TODO BIG: ACTUAL UPDATE USING updateVersionedProduct FUNCTION
                // TODO BIG: FIX updateVersionedProduct FUNCTION TO ACCEPT EVERY FIELD AS OPTIONAL
                versionedProductRepository.save(matchingVersionedProduct);
            }
        }
    }
}