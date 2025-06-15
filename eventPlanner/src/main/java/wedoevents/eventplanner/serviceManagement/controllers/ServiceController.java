package wedoevents.eventplanner.serviceManagement.controllers;

import com.github.dockerjava.api.exception.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.serviceManagement.dtos.*;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;
import wedoevents.eventplanner.shared.config.auth.JwtUtil;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.shared.services.pdfService.PdfGeneratorService;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping(path = "/latest-versions")
    public ResponseEntity<?> getAllServicesWithLatestVersions() {
        try {
            List<VersionedServiceDTO> services = serviceService.getAllServicesWithLatestVersions();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> createService(@RequestParam(value = "images") MultipartFile[] images,
                                           @ModelAttribute CreateVersionedServiceDTO createVersionedServiceDTO,
                                           HttpServletRequest request) {
        try {
            createVersionedServiceDTO.setSellerId(JwtUtil.extractUserId(request));
            VersionedServiceDTO newService = serviceService.createService(createVersionedServiceDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(newService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateService(@RequestParam(value = "images") MultipartFile[] images,
                                           @ModelAttribute UpdateVersionedServiceDTO updateVersionedServiceEntityDTO) {
        try {
            VersionedServiceDTO updatedService = serviceService.updateVersionedService(updateVersionedServiceEntityDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{staticServiceId}/latest-version/edit")
    @PreAuthorize("hasRole('SELLER')")
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
    @PreAuthorize("hasRole('SELLER')")
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

    @GetMapping(path = "/{sellerId}/my-services/catalogue")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSellersServicesForCatalogue(@PathVariable UUID sellerId) {
        try {
            List<CatalogueServiceDTO> services = serviceService.getAllServicesLatestVersionsFromSellerForCatalogue(sellerId);
            return ResponseEntity.ok(services);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized services access");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping(path = "/{sellerId}/update-catalogue")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateSellerServicePrices(@PathVariable UUID sellerId, @RequestBody ToBeUpdatedServicesCatalogueDTO toBeUpdatedServicesCatalogueDTO) {
        try {
            serviceService.updateCataloguePrices(sellerId, toBeUpdatedServicesCatalogueDTO);
            return ResponseEntity.ok().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized services access");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping(path = "/{sellerId}/catalogue-pdf")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> get(@PathVariable UUID sellerId) {
        try {
            byte[] pdfContent = pdfGeneratorService.generateServicesCatalogue(
                    serviceService.getAllServicesLatestVersionsFromSellerForCatalogue(sellerId)
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "services-catalogue.pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfContent);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized services access");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }
}