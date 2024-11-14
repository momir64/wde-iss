package wedoevents.eventplanner.eventManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.ProductBudgetItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductBudgetItemService {

    private final ProductBudgetItemRepository productBudgetItemRepository;

    @Autowired
    public ProductBudgetItemService(ProductBudgetItemRepository productBudgetItemRepository) {
        this.productBudgetItemRepository = productBudgetItemRepository;
    }

    public ProductBudgetItem saveProductBudgetItem(ProductBudgetItem productBudgetItem) {
        return productBudgetItemRepository.save(productBudgetItem);
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