package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.BuyProductDTO;
import wedoevents.eventplanner.eventManagement.dtos.CreateProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.ProductBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.services.ProductBudgetItemService;
import wedoevents.eventplanner.shared.Exceptions.BuyProductException;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/product-budget-items")
public class ProductBudgetItemController {

    private final ProductBudgetItemService productBudgetItemService;

    @Autowired
    public ProductBudgetItemController(ProductBudgetItemService productBudgetItemService) {
        this.productBudgetItemService = productBudgetItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductBudgetItem(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(productBudgetItemService.getProductBudgetItem(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> createProductBudgetItem(@RequestBody CreateProductBudgetItemDTO productBudgetItem) {
        try {
            return ResponseEntity.ok(productBudgetItemService.createProductBudgetItem(productBudgetItem));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BuyProductException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/buy")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> buyProduct(@RequestBody BuyProductDTO buyProductDTO) {
        try {
            return ResponseEntity.ok(productBudgetItemService.buyProduct(buyProductDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BuyProductException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping ("/{eventId}/{productCategoryId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> deleteEventEmptyProductCategoryFromBudget(@PathVariable UUID eventId, @PathVariable UUID productCategoryId) {
        try {
            productBudgetItemService.deleteEventEmptyProductCategoryFromBudget(eventId, productCategoryId);
            return ResponseEntity.noContent().build();
        } catch (EntityCannotBeDeletedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Product category has purchased products");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{productBudgetItemId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> changeProductBudgetItemMaxPrice(@PathVariable UUID productBudgetItemId,
                                                             @RequestBody Double newPrice) {
        try {
            productBudgetItemService.changeProductBudgetItemMaxPrice(productBudgetItemId, newPrice);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}