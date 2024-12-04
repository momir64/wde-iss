package wedoevents.eventplanner.serviceManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import wedoevents.eventplanner.serviceManagement.dtos.CreateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.TemporaryMockServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.UpdateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping(path = "/latest-versions")
    public ResponseEntity<?> getAllServicesWithLatestVersions() {
        try {
            List<VersionedServiceDTO> services = serviceService.getAllServicesWithLatestVersions();
            return ResponseEntity.ok(services);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized service access");
//        }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping
    public ResponseEntity<?> createService(@RequestBody CreateVersionedServiceDTO createVersionedServiceEntityDTO) {
        try {
            VersionedServiceDTO newService = serviceService.createService(createVersionedServiceEntityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateService(@RequestBody UpdateVersionedServiceDTO updateVersionedServiceEntityDTO) {
        try {
            VersionedServiceDTO updatedService = serviceService.updateVersionedService(updateVersionedServiceEntityDTO);
            return ResponseEntity.ok(updatedService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }


    @GetMapping
    public ResponseEntity<?> searchServices(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) UUID serviceCategoryId,
            @RequestParam(value = "eventType", required = false) UUID eventTypeId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "isAvailable", required = false) Boolean isAvailable,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            // call search latest service versions service
            List<VersionedServiceDTO> services = buildMockServices();
            return ResponseEntity.ok(services);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }


    @GetMapping("/{staticServiceId}/latest-version")
    public ResponseEntity<?> getServiceLatestVersionById(@PathVariable UUID staticServiceId) {
        return ResponseEntity.ok(new TemporaryMockServiceDTO("Best service sever", "Best company ever", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", 199.99, 4.2, Arrays.asList("https://picsum.photos/300/200", "https://picsum.photos/300/201"), 0.2));
//        try {
//            VersionedServiceDTO service = serviceService.getVersionedServiceById(staticServiceId);
//            return ResponseEntity.ok(service);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
////        } catch (ProductNotFoundException e) {
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
//        }
    }

    @GetMapping("/{staticServiceId}/{version}")
    public ResponseEntity<?> getServiceLatestVersionById(@PathVariable Integer version,
                                                         @PathVariable UUID staticServiceId) {
        try {
            VersionedServiceDTO service = serviceService.getVersionedServiceByStaticServiceIdAndVersion(version, staticServiceId);
            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @DeleteMapping("/{staticServiceId}")
    public ResponseEntity<?> deleteService(@PathVariable UUID staticServiceId) {
        try {
            serviceService.deactivateService(staticServiceId);
            return ResponseEntity.ok("Service archived successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to archive service");
//        } catch (ProductNotFoundException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    public List<VersionedServiceDTO> buildMockServices() {
        List<VersionedServiceDTO> services = new ArrayList<>();

        services.add(new VersionedServiceDTO(
                UUID.randomUUID(),
                1,
                UUID.randomUUID(),
                "Service A",
                10.0,
                Arrays.asList("image1.jpg", "image2.jpg"),
                "Description for Service A",
                false,
                true,
                60,
                24,
                48,
                true,
                false,
                99.99,
                Arrays.asList(UUID.randomUUID(), UUID.randomUUID())
        ));

        services.add(new VersionedServiceDTO(
                UUID.randomUUID(),
                1,
                UUID.randomUUID(),
                "Service B",
                15.0,
                List.of("image3.jpg"),
                "Description for Service B",
                true,
                false,
                120,
                48,
                72,
                true,
                true,
                199.99,
                List.of(UUID.randomUUID())
        ));

        services.add(new VersionedServiceDTO(
                UUID.randomUUID(),
                1,
                UUID.randomUUID(),
                "Service C",
                5.0,
                Arrays.asList("image4.jpg", "image5.jpg"),
                "Description for Service C",
                false,
                true,
                30,
                12,
                24,
                false,
                false,
                49.99,
                Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
        ));

        return services;
    }

    @GetMapping(path = "/catalogue/{id}")
    public ResponseEntity<?> getSellersServices(@PathVariable UUID id) {
        try {
            // will later change to only return services of a given seller
            List<VersionedServiceDTO> services = serviceService.getAllServicesWithLatestVersions();
            return ResponseEntity.ok(services);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized services access");
//        }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping(path = "/catalogue")
    public ResponseEntity<?> updateServices(@RequestBody List<UpdateVersionedServiceDTO> updateVersionedServiceDTOs) {
        try {
            List<VersionedServiceDTO> updatedServices = serviceService.updateVersionedServices(updateVersionedServiceDTOs);
            return ResponseEntity.ok(updatedServices);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized services access");
//        }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("An unexpected error occurred");
        }
    }
}