package wedoevents.eventplanner.serviceManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.serviceManagement.dtos.UpdateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.CreateVersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceForSellerDTO;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ImageService imageService;

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

    // todo when session tracking is enabled, add which seller created the service
    // todo for now the seller id is fixed to "2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5"
    @PostMapping
    public ResponseEntity<?> createService(@RequestParam(value = "images") MultipartFile[] images,
                                           @ModelAttribute CreateVersionedServiceDTO createVersionedServiceDTO) {
        try {
            createVersionedServiceDTO.setSellerId(UUID.fromString("2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5"));
            VersionedServiceDTO newService = serviceService.createService(createVersionedServiceDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(newService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateService(@RequestParam(value = "images") MultipartFile[] images,
                                           @ModelAttribute UpdateVersionedServiceDTO updateVersionedServiceEntityDTO) {
        try {
            VersionedServiceDTO updatedService = serviceService.updateVersionedService(updateVersionedServiceEntityDTO, images);
            return ResponseEntity.ok(updatedService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{staticServiceId}/latest-version/edit")
    public ResponseEntity<?> getServiceLatestVersionEditableById(@PathVariable UUID staticServiceId) {
        try {
            VersionedServiceForSellerDTO service = serviceService.getVersionedServiceEditableById(staticServiceId);
            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping(value = "/{id}/{version}/images/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getServiceImage(@PathVariable("id") UUID id, @PathVariable("version") Integer version, @PathVariable("image_name") String imageName) {
        try {
            ImageLocationConfiguration config = new ImageLocationConfiguration("service", id, version);
            Optional<byte[]> image = imageService.getImage(imageName, config);
            if (image.isEmpty())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
            return ResponseEntity.ok().body(image.get());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{staticServiceId}/latest-version")
    public ResponseEntity<?> getServiceLatestVersionById(@PathVariable UUID staticServiceId) {
        try {
            VersionedServiceDTO service = serviceService.getVersionedServiceById(staticServiceId);
            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{staticServiceId}/{version}")
    public ResponseEntity<?> getServiceByVersionById(@PathVariable Integer version,
                                                         @PathVariable UUID staticServiceId) {
        try {
            VersionedServiceDTO service = serviceService.getVersionedServiceByStaticServiceIdAndVersion(version, staticServiceId);
            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @DeleteMapping("/{staticServiceId}")
    public ResponseEntity<?> deleteService(@PathVariable UUID staticServiceId) {
        try {
            serviceService.deactivateService(staticServiceId);
            return ResponseEntity.ok("{}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to archive service");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
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
}