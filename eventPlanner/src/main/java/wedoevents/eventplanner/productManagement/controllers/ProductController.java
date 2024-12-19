package wedoevents.eventplanner.productManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import wedoevents.eventplanner.productManagement.dtos.CreateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.UpdateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductDTO;
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

    @PostMapping
    public ResponseEntity<?>  createProduct(@RequestBody CreateVersionedProductDTO createVersionedProductDTO) {
        try{
            VersionedProductDTO newProduct = productService.createProduct(createVersionedProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    public ResponseEntity<?>  updateProduct(@RequestBody UpdateVersionedProductDTO updateVersionedProductDTO) {
        try{
            VersionedProductDTO updatedProduct = productService.updateVersionedProduct(updateVersionedProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping(value = "/{id}/{version}/images/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getProfileImage(@PathVariable("id") UUID id, @PathVariable("version") Integer version, @PathVariable("image_name") String imageName) {
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
        }
    }

    @GetMapping("/{staticProductId}/{version}")
    public ResponseEntity<?> getProductLatestVersionById(@PathVariable Integer version,
                                                           @PathVariable UUID staticProductId) {
        try{
            VersionedProductDTO product = productService.getVersionedProductByStaticProductIdAndVersion(version, staticProductId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @DeleteMapping("/{staticProductId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID staticProductId)
    {
        try{
            productService.deactivateProduct(staticProductId);
            return ResponseEntity.ok("Product archived successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (HttpClientErrorException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to archive product");
//        } catch (ProductNotFoundException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
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

    @PutMapping(path = "/catalogue")
    public ResponseEntity<?> updateProducts(@RequestBody List<UpdateVersionedProductDTO> updateVersionedProductDTOs) {
        try {
            List<VersionedProductDTO> updatedProducts = productService.updateVersionedProducts(updateVersionedProductDTOs);
            return ResponseEntity.ok(updatedProducts);
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized products access");
//        }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("An unexpected error occurred");
        }
    }
}