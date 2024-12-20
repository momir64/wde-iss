package wedoevents.eventplanner.productManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import wedoevents.eventplanner.productManagement.dtos.CreateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.UpdateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductForSellerDTO;
import wedoevents.eventplanner.productManagement.services.ProductService;
import wedoevents.eventplanner.shared.services.imageService.ImageLocationConfiguration;
import wedoevents.eventplanner.shared.services.imageService.ImageService;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @GetMapping(path = "latest-versions")
    public ResponseEntity<?> getAllProductsWithLatestVersions() {
        try{
            List<VersionedProductDTO> products = productService.getAllProductsWithLatestVersions();
            return ResponseEntity.ok(products);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized product access");
//        }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    // todo when session tracking is enabled, add which seller created the product
    // todo for now the seller id is fixed to "2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5"
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestParam(value = "images") MultipartFile[] images,
                                           @ModelAttribute CreateVersionedProductDTO createVersionedProductDTO) {
        try {
            createVersionedProductDTO.setSellerId(UUID.fromString("2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5"));
            VersionedProductDTO newProduct = productService.createProduct(createVersionedProductDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
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

    @GetMapping(path = "/catalogue/{id}")
    public ResponseEntity<?> getSellersProducts(@PathVariable UUID id) {
        try {
            // will later change to only return products of a given seller
            List<VersionedProductDTO> products = productService.getAllProductsWithLatestVersions();
            return ResponseEntity.ok(products);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized products access");
//        }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }
}