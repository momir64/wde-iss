package wedoevents.eventplanner.eventManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ServiceBudgetItem> createOrUpdateServiceBudgetItem(@RequestBody ServiceBudgetItem serviceBudgetItem) {
        ServiceBudgetItem savedServiceBudgetItem = serviceBudgetItemService.saveServiceBudgetItem(serviceBudgetItem);
        return new ResponseEntity<>(savedServiceBudgetItem, HttpStatus.CREATED);
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