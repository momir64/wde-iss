package wedoevents.eventplanner.productManagement.controllers;

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
import wedoevents.eventplanner.productManagement.dtos.*;
import wedoevents.eventplanner.productManagement.services.ProductService;
import wedoevents.eventplanner.shared.config.auth.JwtUtil;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;
import wedoevents.eventplanner.shared.services.pdfService.PdfGeneratorService;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping(path = "latest-versions")
    public ResponseEntity<?> getAllProductsWithLatestVersions() {
        try{
            List<VersionedProductDTO> products = productService.getAllProductsWithLatestVersions();
            return ResponseEntity.ok(products);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> createProduct(@RequestParam(value = "images") MultipartFile[] images,
                                           @ModelAttribute CreateVersionedProductDTO createVersionedProductDTO,
                                           HttpServletRequest request) {
        try {
            createVersionedProductDTO.setSellerId(JwtUtil.extractUserId(request));
            VersionedProductDTO newProduct = productService.createProduct(createVersionedProductDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateProduct(@RequestParam(value = "images") MultipartFile[] images,
                                           @ModelAttribute UpdateVersionedProductDTO updateVersionedProductDTO) {
        try {
            VersionedProductDTO updatedProduct = productService.updateVersionedProduct(updateVersionedProductDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{staticProductId}/latest-version/edit")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductLatestVersionEditableById(@PathVariable UUID staticProductId) {
        try {
            VersionedProductForSellerDTO product = productService.getVersionedProductEditableById(staticProductId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping(value = "/{id}/{version}/images/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getProductImage(@PathVariable("id") UUID id, @PathVariable("version") Integer version, @PathVariable("image_name") String imageName) {
        try {
            ImageLocationConfiguration config = new ImageLocationConfiguration("product", id, version);
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

    @GetMapping("/{staticProductId}/latest-version")
    public ResponseEntity<?> getProductLatestVersionById(@PathVariable UUID staticProductId) {
        try {
            VersionedProductDTO product = productService.getVersionedProductById(staticProductId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{staticProductId}/{version}")
    public ResponseEntity<?> getProductByVersionById(@PathVariable Integer version,
                                                           @PathVariable UUID staticProductId) {
        try{
            VersionedProductDTO product = productService.getVersionedProductByStaticProductIdAndVersion(version, staticProductId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @DeleteMapping("/{staticProductId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID staticProductId)
    {
        try {
            productService.deactivateProduct(staticProductId);
            return ResponseEntity.ok("{}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (HttpClientErrorException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to archive product");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping(path = "/{sellerId}/my-products/catalogue")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSellersProductsForCatalogue(@PathVariable UUID sellerId) {
        try {
            List<CatalogueProductDTO> services = productService.getAllProductsLatestVersionsFromSellerForCatalogue(sellerId);
            return ResponseEntity.ok(services);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized services access");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PostMapping(path = "/{sellerId}/update-catalogue")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateSellerProductPrices(@PathVariable UUID sellerId, @RequestBody ToBeUpdatedProductsCatalogueDTO toBeUpdatedProductsCatalogueDTO) {

        try {
            productService.updateCataloguePrices(sellerId, toBeUpdatedProductsCatalogueDTO);
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
            byte[] pdfContent = pdfGeneratorService.generateProductsCatalogue(
                    productService.getAllProductsLatestVersionsFromSellerForCatalogue(sellerId)
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "products-catalogue.pdf");
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