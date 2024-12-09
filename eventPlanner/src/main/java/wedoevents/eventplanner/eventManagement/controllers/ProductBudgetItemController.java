package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.CreateProductBudgetItemDTO;
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
    public ResponseEntity<CreateProductBudgetItemDTO> createOrUpdateProductBudgetItem(@RequestBody CreateProductBudgetItemDTO productBudgetItem) {
//  if (adding new budget item - event doesn't have item with that category) {
        return new ResponseEntity<>(productBudgetItem, HttpStatus.CREATED);
//  } else if (error - some id isn't null but doesn't exist) {
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.NOT_FOUND);
//  } else if (error - user doesn't have necessary permissions) {
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.FORBIDDEN);
//  } else if (changing budget item max price, category or product - item id isn't null and exists) {
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.OK);
//  } else if (buying product - product id isn't null, event has item with that category and without product id) {
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.OK);
//  } else if (buying product - product id isn't null, event doesn't have item with that category) {
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.CREATED);
//  } else if (buying product error - product id isn't null, event has item with that category and with product id) {
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.BAD_REQUEST);
//  }
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

// todo za kupovinu proizvoda: dodaj mogucnost da se kupi proizvod koji nema kategoriju (automatski se dodaje kategorija sa budzetom 0 din)