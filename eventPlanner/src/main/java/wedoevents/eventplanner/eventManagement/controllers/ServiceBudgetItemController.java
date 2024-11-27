package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.eventManagement.dtos.ServiceBudgetItemDTO;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.services.ServiceBudgetItemService;

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
    public ResponseEntity<ServiceBudgetItemDTO> createOrUpdateServiceBudgetItem(@RequestBody ServiceBudgetItemDTO serviceBudgetItem) {
//  if (adding new budget item - event doesn't have item with that category) {
        return new ResponseEntity<>(serviceBudgetItem, HttpStatus.CREATED);
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
    }

    @GetMapping
    public ResponseEntity<List<ServiceBudgetItem>> getAllServiceBudgetItems() {
        List<ServiceBudgetItem> serviceBudgetItems = serviceBudgetItemService.getAllServiceBudgetItems();
        return new ResponseEntity<>(serviceBudgetItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceBudgetItem> getServiceBudgetItemById(@PathVariable UUID id) {
        Optional<ServiceBudgetItem> serviceBudgetItem = serviceBudgetItemService.getServiceBudgetItemById(id);
        return serviceBudgetItem.map(ResponseEntity::ok)
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceBudgetItem(@PathVariable UUID id) {
        if (serviceBudgetItemService.getServiceBudgetItemById(id).isPresent()) {
            serviceBudgetItemService.deleteServiceBudgetItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}