package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.services.ProductBudgetItemService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-budget-items")
public class ProductBudgetItemController {

    private final ProductBudgetItemService productBudgetItemService;

    @Autowired
    public ProductBudgetItemController(ProductBudgetItemService productBudgetItemService) {
        this.productBudgetItemService = productBudgetItemService;
    }

    @PostMapping
    public ResponseEntity<ProductBudgetItem> createOrUpdateProductBudgetItem(@RequestBody ProductBudgetItem productBudgetItem) {
        ProductBudgetItem savedProductBudgetItem = productBudgetItemService.saveProductBudgetItem(productBudgetItem);
        return new ResponseEntity<>(savedProductBudgetItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductBudgetItem>> getAllProductBudgetItems() {
        List<ProductBudgetItem> productBudgetItems = productBudgetItemService.getAllProductBudgetItems();
        return new ResponseEntity<>(productBudgetItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBudgetItem> getProductBudgetItemById(@PathVariable UUID id) {
        Optional<ProductBudgetItem> productBudgetItem = productBudgetItemService.getProductBudgetItemById(id);
        return productBudgetItem.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductBudgetItem(@PathVariable UUID id) {
        if (productBudgetItemService.getProductBudgetItemById(id).isPresent()) {
            productBudgetItemService.deleteProductBudgetItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}