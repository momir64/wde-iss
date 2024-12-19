package wedoevents.eventplanner.eventManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.BuyProductDTO;
import wedoevents.eventplanner.eventManagement.dtos.BuyServiceDTO;
import wedoevents.eventplanner.eventManagement.dtos.CreateServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.dtos.ServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.services.ServiceBudgetItemService;
import wedoevents.eventplanner.shared.Exceptions.BuyProductException;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service-budget-items")
public class ServiceBudgetItemController {

    private final ServiceBudgetItemService serviceBudgetItemService;

    @Autowired
    public ServiceBudgetItemController(ServiceBudgetItemService serviceBudgetItemService) {
        this.serviceBudgetItemService = serviceBudgetItemService;
    }

    @PostMapping
    public ResponseEntity<?> createServiceBudgetItem(@RequestBody CreateServiceBudgetItemDTO serviceBudgetItem) {
        // todo clean up this comment after checking
//  if (adding new budget item - event doesn't have item with that category) {
//        return new ResponseEntity<>(serviceBudgetItem, HttpStatus.CREATED);
//  } else if (error - some id isn't null but doesn't exist) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.NOT_FOUND);
//  } else if (error - user doesn't have necessary permissions) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.FORBIDDEN);
//  } else if (changing budget item max price, category or service - item id isn't null and exists) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.OK);
//  } else if (buying service - service id isn't null, event has item with that category and without service id) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.OK);
//  } else if (buying service - service id isn't null, event doesn't have item with that category) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.CREATED);
//  } else if (buying service error - service id isn't null, event has item with that category and with service id) {
//      return new ResponseEntity<>(serviceBudgetItem, HttpStatus.BAD_REQUEST);
//  }
        try {
            return ResponseEntity.ok(serviceBudgetItemService.createServiceBudgetItem(serviceBudgetItem));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BuyProductException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyService(@RequestBody BuyServiceDTO buyServiceDTO) {
        try {
            return ResponseEntity.ok(serviceBudgetItemService.buyService(buyServiceDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BuyProductException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping ("/{eventId}/{serviceCategoryId}")
    public ResponseEntity<?> deleteEventEmptyServiceCategoryFromBudget(@PathVariable UUID eventId, @PathVariable UUID serviceCategoryId) {
        try {
            serviceBudgetItemService.deleteEventEmptyServiceCategoryFromBudget(eventId, serviceCategoryId);
            return ResponseEntity.noContent().build();
        } catch (EntityCannotBeDeletedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Service category has reserved services");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}