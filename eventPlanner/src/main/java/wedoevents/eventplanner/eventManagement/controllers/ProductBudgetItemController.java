package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> createProductBudgetItem(@RequestBody CreateProductBudgetItemDTO productBudgetItem) {
//  if (adding new budget item - event doesn't have item with that category) { --
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.CREATED);
//  } else if (error - some id isn't null but doesn't exist) { --
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.NOT_FOUND);
//  } else if (error - user doesn't have necessary permissions) { --
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.FORBIDDEN);
//  } else if (changing budget item max price, category or product - item id isn't null and exists) {
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.OK);
//  } else if (buying product - product id isn't null, event has item with that category and without product id) { --
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.OK);
//  } else if (buying product - product id isn't null, event doesn't have item with that category) { --
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.CREATED);
//  } else if (buying product error - product id isn't null, event has item with that category and with product id) { --
//      return new ResponseEntity<>(productBudgetItem, HttpStatus.BAD_REQUEST);
//  }
        try {
            return ResponseEntity.ok(productBudgetItemService.createProductBudgetItem(productBudgetItem));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BuyProductException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/buy")
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
}