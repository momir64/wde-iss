package wedoevents.eventplanner.serviceManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.services.ServiceCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service-categories")
public class ServiceCategoryController {


    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @GetMapping
    public List<ServiceCategory> getAllServiceCategories() {
        return serviceCategoryService.getAllServiceCategories();
    }

    @PostMapping
    public ServiceCategory createServiceCategory(@RequestBody ServiceCategory category) {
        return serviceCategoryService.saveServiceCategory(category);
    }

    @GetMapping("/{id}")
    public ServiceCategory getServiceCategoryById(@PathVariable UUID id) {
        return serviceCategoryService.getServiceCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteServiceCategory(@PathVariable UUID id) {
        serviceCategoryService.deleteServiceCategory(id);
    }
}
