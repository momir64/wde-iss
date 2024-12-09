package wedoevents.eventplanner.eventManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.dtos.CreateProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.ProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.ProductBudgetItemRepository;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductBudgetItemService {

    private final ProductBudgetItemRepository productBudgetItemRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductBudgetItemService(ProductBudgetItemRepository productBudgetItemRepository, ProductCategoryRepository productCategoryRepository) {
        this.productBudgetItemRepository = productBudgetItemRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductBudgetItemDTO createProductBudgetItem(CreateProductBudgetItemDTO createProductBudgetItemDTO) {
        ProductBudgetItem newProductBudgetItem = new ProductBudgetItem();

        Optional<ProductCategory> productCategoryMaybe = productCategoryRepository.findById(createProductBudgetItemDTO.getProductCategoryId());

        if (productCategoryMaybe.isEmpty()) {
            throw new EntityNotFoundException();
        }

        newProductBudgetItem.setProductCategory(productCategoryMaybe.get());
        newProductBudgetItem.setMaxPrice(createProductBudgetItemDTO.getMaxPrice());

        return ProductBudgetItemDTO.toDto(productBudgetItemRepository.save(newProductBudgetItem));
    }

    public List<ProductBudgetItem> getAllProductBudgetItems() {
        return productBudgetItemRepository.findAll();
    }

    public Optional<ProductBudgetItem> getProductBudgetItemById(UUID id) {
        return productBudgetItemRepository.findById(id);
    }

    public void deleteProductBudgetItem(UUID id) {
        productBudgetItemRepository.deleteById(id);
    }
}