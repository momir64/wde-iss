package wedoevents.eventplanner.serviceManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.serviceManagement.dtos.CreateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.UpdateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    
    @GetMapping(path = "allLatestVersions")
    public List<VersionedServiceDTO> getAllServicesWithLatestVersions() {
        return serviceService.getAllServicesWithLatestVersions();
    }

    @PostMapping
    public VersionedServiceDTO createService(@RequestBody CreateVersionedServiceDTO createVersionedServiceEntityDTO) {
        return serviceService.createService(createVersionedServiceEntityDTO);
    }

    @PutMapping
    public VersionedServiceDTO updateService(@RequestBody UpdateVersionedServiceDTO updateVersionedServiceEntityDTO) {
        return serviceService.updateVersionedService(updateVersionedServiceEntityDTO);
    }

    @GetMapping("/{staticServiceId}/latestVersion")
    public VersionedServiceDTO getServiceLatestVersionById(@PathVariable UUID staticServiceId) {
        return serviceService.getVersionedServiceById(staticServiceId);
    }

    @GetMapping("/{staticServiceId}/{version}")
    public VersionedServiceDTO getServiceLatestVersionById(@PathVariable Integer version,
                                                           @PathVariable UUID staticServiceId) {
        return serviceService.getVersionedServiceByStaticServiceIdAndVersion(version, staticServiceId);
    }

    @DeleteMapping("/{staticServiceId}")
    public void deleteService(@PathVariable UUID staticServiceId) {
        serviceService.deactivateService(staticServiceId);
    }
}