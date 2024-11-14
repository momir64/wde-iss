package wedoevents.eventplanner.serviceManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.serviceManagement.models.ServiceEntity;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public List<ServiceEntity> getAllServices() {
        return serviceService.getAllServices();
    }

    @PostMapping
    public ServiceEntity createService(@RequestBody ServiceEntity service) {
        return serviceService.saveService(service);
    }

    @GetMapping("/{id}")
    public ServiceEntity getServiceById(@PathVariable UUID id) {
        return serviceService.getServiceById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable UUID id) {
        serviceService.deleteService(id);
    }
}