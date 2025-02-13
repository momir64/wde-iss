package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.BuyProductDTO;
import wedoevents.eventplanner.eventManagement.dtos.CreateProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.ProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.ProductBudgetItemRepository;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;
import wedoevents.eventplanner.productManagement.repositories.VersionedProductRepository;
import wedoevents.eventplanner.shared.Exceptions.BuyProductException;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductBudgetItemService {

    private final ProductBudgetItemRepository productBudgetItemRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final EventRepository eventRepository;
    private final VersionedProductRepository versionedProductRepository;

    @Autowired
    public ProductBudgetItemService(ProductBudgetItemRepository productBudgetItemRepository,
                                    ProductCategoryRepository productCategoryRepository,
                                    EventRepository eventRepository, VersionedProductRepository versionedProductRepository) {
        this.productBudgetItemRepository = productBudgetItemRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.eventRepository = eventRepository;
        this.versionedProductRepository = versionedProductRepository;
    }

    public ProductBudgetItemDTO getProductBudgetItem(UUID id) {
        Optional<ProductBudgetItem> productBudgetItemMaybe = productBudgetItemRepository.findById(id);

        if (productBudgetItemMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return ProductBudgetItemDTO.toDto(productBudgetItemMaybe.get());
    }

    public ProductBudgetItemDTO createProductBudgetItem(CreateProductBudgetItemDTO createProductBudgetItemDTO) {
        Optional<ProductCategory> productCategoryMaybe = productCategoryRepository.findById(createProductBudgetItemDTO.getProductCategoryId());

        if (productCategoryMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Optional<Event> eventMaybe = eventRepository.findById(createProductBudgetItemDTO.getEventId());

        if (eventMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Event event = eventMaybe.get();

        if (event.getProductBudgetItems()
                .stream()
                .anyMatch(e ->
                        e.getProductCategory().getId().equals(createProductBudgetItemDTO.getProductCategoryId()))) {
            throw new BuyProductException("Event already contains that category");
        }

        ProductBudgetItem newProductBudgetItem = new ProductBudgetItem();

        newProductBudgetItem.setProductCategory(productCategoryMaybe.get());
        newProductBudgetItem.setMaxPrice(createProductBudgetItemDTO.getMaxPrice());

        ProductBudgetItem createdProductBudgetItem = productBudgetItemRepository.save(newProductBudgetItem);

        event.getProductBudgetItems().add(createdProductBudgetItem);
        eventRepository.save(event);

        return ProductBudgetItemDTO.toDto(createdProductBudgetItem);
    }

    public ProductBudgetItemDTO buyProduct(BuyProductDTO buyProductDTO) {
        Optional<VersionedProduct> versionedProductMaybe = versionedProductRepository.getLatestByStaticProductIdAndLatestVersion(buyProductDTO.getProductId());

        if (versionedProductMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        VersionedProduct versionedProduct = versionedProductMaybe.get();

        if (!versionedProduct.getIsActive() || versionedProduct.getIsPrivate() || !versionedProduct.getIsAvailable()) {
            throw new BuyProductException("Can't buy product that is not visible to you");
        }

        Optional<Event> eventMaybe = eventRepository.findById(buyProductDTO.getEventId());

        if (eventMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Event event = eventMaybe.get();

        if (versionedProduct.getAvailableEventTypes()
                .stream()
                .map(EventType::getId)
                .noneMatch(id -> id.equals(event.getEventType().getId()))) {
            throw new BuyProductException("Event type not in event's available event types");
        }

        ProductBudgetItem productBudgetItem;

        if (buyProductDTO.getProductBudgetItemId() == null) {
            ProductBudgetItemDTO productBudgetItemDTO = createProductBudgetItem(new CreateProductBudgetItemDTO(
                    buyProductDTO.getEventId(),
                    versionedProduct.getStaticProduct().getProductCategory().getId(),
                    0.0
            ));

            productBudgetItem = productBudgetItemRepository.findById(productBudgetItemDTO.getId()).get();
        } else {
            Optional<ProductBudgetItem> productBudgetItemMaybe = productBudgetItemRepository.findById(buyProductDTO.getProductBudgetItemId());

            if (productBudgetItemMaybe.isEmpty()) {
                throw new EntityNotFoundException();
            } else {
                productBudgetItem = productBudgetItemMaybe.get();

                if (productBudgetItem.getMaxPrice() < versionedProduct.getPrice() * (1 - versionedProduct.getSalePercentage())) {
                    throw new BuyProductException("Product too expensive");
                }
            }
        }

        productBudgetItem.setProduct(versionedProduct);
        return ProductBudgetItemDTO.toDto(productBudgetItemRepository.save(productBudgetItem));
    }

    public void deleteEventEmptyProductCategoryFromBudget(UUID eventId, UUID productCategoryId) {
        if (!eventRepository.existsEventById(eventId)) {
            throw new EntityNotFoundException();
        }

        if (productBudgetItemRepository.removeEventEmptyProductCategory(eventId, productCategoryId) != 0) {
            throw new EntityCannotBeDeletedException();
        }
    }
}