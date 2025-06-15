package wedoevents.eventplanner.productManagement.services;

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
import wedoevents.eventplanner.userManagement.models.ListingReview;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.repositories.ListingReviewRepository;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ListingReviewRepository listingReviewRepository;

    @Autowired
    private VersionedProductRepository versionedProductRepository;

    @Autowired
    private StaticProductRepository staticProductRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SellerRepository sellerRepository;

    private VersionedProduct incrementProductVersionAndSave(VersionedProduct versionedProduct) {
        versionedProduct.incrementVersion();

        return versionedProductRepository.save(versionedProduct);
    }

    public List<VersionedProductDTO> getAllProductsWithLatestVersions() {
        return versionedProductRepository.getAllVersionedProductsWithMaxVersions().stream().map(vp ->
                {
                    UUID sellerId = staticProductRepository.getIdOfSeller(vp.getStaticProductId());
                    Seller productSeller = sellerRepository.findById(sellerId).get();

                    return VersionedProductDTO.toDto(vp, productSeller);
                }
        ).toList();
    }

    public List<CatalogueProductDTO> getAllProductsLatestVersionsFromSellerForCatalogue(UUID sellerId) {
        return versionedProductRepository.getAllVersionedProductsWithMaxVersionsFromSeller(sellerId).stream().map(
                CatalogueProductDTO::toDto
        ).toList();
    }

    public VersionedProductDTO createProduct(CreateVersionedProductDTO createVersionedProductDTO, MultipartFile[] images) throws IOException {
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

        newVersionedProduct = versionedProductRepository.save(newVersionedProduct);

        return VersionedProductDTO.toDto(newVersionedProduct, seller);
    }

    public VersionedProductDTO updateVersionedProduct(UpdateVersionedProductDTO updateVersionedProductDTO, MultipartFile[] images) throws IOException {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository
                .getLatestByStaticProductIdAndLatestVersion(updateVersionedProductDTO.getStaticProductId());
        
        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        if (!versionedProductMaybe.get().getIsActive()) {
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

        newVersionedProduct = versionedProductRepository.save(newVersionedProduct);

        UUID sellerId = staticProductRepository.getIdOfSeller(newVersionedProduct.getStaticProductId());
        Seller productSeller = sellerRepository.findById(sellerId).get();

        return VersionedProductDTO.toDto(newVersionedProduct, productSeller);
    }

    public VersionedProductDTO getVersionedProductById(UUID staticProductId) {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getLatestByStaticProductIdAndLatestVersion(staticProductId);

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        if (!versionedProductMaybe.get().getIsActive()) {
            throw new EntityNotFoundException();
        }

        if (versionedProductMaybe.get().getIsPrivate()) {
            // todo prevent regular users from getting private services
        }

        if (!versionedProductMaybe.get().getIsAvailable()) {
            // todo prevent regular users from getting unavailable services
        }

        UUID sellerId = staticProductRepository.getIdOfSeller(staticProductId);
        Seller productSeller = sellerRepository.findById(sellerId).get();
        
        VersionedProductDTO product = VersionedProductDTO.toDto(versionedProductMaybe.get(), productSeller);
        List<ListingReview> reviews = listingReviewRepository.findByProductId(staticProductId);
        double averageRating = reviews.stream()
                .mapToInt(ListingReview::getGrade)
                .average()
                .orElse(0.0);
        product.setRating(averageRating);
        return product;
    }

    public VersionedProductForSellerDTO getVersionedProductEditableById(UUID staticProductId) throws IOException {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getLatestByStaticProductIdAndLatestVersion(staticProductId);

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        if (!versionedProductMaybe.get().getIsActive()) {
            throw new EntityNotFoundException();
        }

        return VersionedProductForSellerDTO.toDto(versionedProductMaybe.get(), imageService);
    }

    public VersionedProductDTO getVersionedProductByStaticProductIdAndVersion(Integer version, UUID staticProductId) {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getVersionedProductByStaticProductIdAndVersion(staticProductId, version);

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        if (versionedProductMaybe.get().getIsPrivate()) {
            throw new EntityNotFoundException();
        }

        UUID sellerId = staticProductRepository.getIdOfSeller(staticProductId);
        Seller productSeller = sellerRepository.findById(sellerId).get();

        return VersionedProductDTO.toDto(versionedProductMaybe.get(), productSeller);
    }

    public void deactivateProduct(UUID staticProductId) {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getLatestByStaticProductIdAndLatestVersion(staticProductId);

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        VersionedProduct oldVersionedProduct = versionedProductMaybe.get();
        oldVersionedProduct.setIsLastVersion(false);
        versionedProductRepository.save(oldVersionedProduct);

        VersionedProduct newVersionedProduct = new VersionedProduct(oldVersionedProduct);
        newVersionedProduct.setIsLastVersion(true);
        newVersionedProduct.setIsActive(false);

        incrementProductVersionAndSave(newVersionedProduct);
    }

    public void updateCataloguePrices(UUID sellerId, ToBeUpdatedProductsCatalogueDTO toBeUpdatedProductsCatalogueDTO) throws IOException {
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

            VersionedProduct oldPriceVersionedProduct = matchingVersionedProductMaybe.get();

            if (!oldPriceVersionedProduct.getPrice().equals(newPrice.getPrice()) ||
                    !oldPriceVersionedProduct.getSalePercentage().equals(newPrice.getSalePercentage())) {
                oldPriceVersionedProduct.setIsLastVersion(false);
                versionedProductRepository.save(oldPriceVersionedProduct);
                
                VersionedProduct newPriceVersionedProduct = new VersionedProduct(oldPriceVersionedProduct);
                
                newPriceVersionedProduct.setPrice(newPrice.getPrice());
                newPriceVersionedProduct.setSalePercentage(newPrice.getSalePercentage());
                newPriceVersionedProduct.setIsLastVersion(true);

                newPriceVersionedProduct.setImages(imageService.copyImagesToNewVersion(
                        new ImageLocationConfiguration("product",
                                oldPriceVersionedProduct.getStaticProductId(),
                                oldPriceVersionedProduct.getVersion())
                ));

                incrementProductVersionAndSave(newPriceVersionedProduct);
            }
        }
    }

    public Optional<StaticProduct> getStaticProductById(UUID staticProductId) {
        return staticProductRepository.findById(staticProductId);
    }
}