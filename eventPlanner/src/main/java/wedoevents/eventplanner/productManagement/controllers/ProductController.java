package wedoevents.eventplanner.productManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import wedoevents.eventplanner.productManagement.dtos.CreateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.UpdateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductDTO;
import wedoevents.eventplanner.productManagement.services.ProductService;

import java.util.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

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
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping
    public ResponseEntity<?> searchProducts( @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "category", required = false) UUID categoryId,
                                             @RequestParam(value = "eventType", required = false) UUID eventTypeId,
                                             @RequestParam(value = "minPrice", required = false) Double minPrice,
                                             @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                             @RequestParam(value = "isAvailable", required = false) Boolean isAvailable,
                                             @RequestParam(value = "description", required = false) String description,
                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "10") int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            //call search latest product versions service
            List<VersionedProductDTO> products = buildMockProducts();
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{staticProductId}/latest-version")
    public ResponseEntity<?> getProductLatestVersionById(@PathVariable UUID staticProductId) {
        try{
            VersionedProductDTO product = productService.getVersionedProductById(staticProductId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
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

    public List<VersionedProductDTO> buildMockProducts() {
        List<VersionedProductDTO> products = new ArrayList<>();

        products.add(new VersionedProductDTO(
                UUID.randomUUID(),
                1,
                "Product A",
                29.99,
                Arrays.asList("image1.jpg", "image2.jpg"),
                10.0,
                true,
                true,
                false,
                UUID.randomUUID(),
                Arrays.asList(UUID.randomUUID(), UUID.randomUUID())
        ));

        products.add(new VersionedProductDTO(
                UUID.randomUUID(),
                1,
                "Product B",
                49.99,
                Arrays.asList("image3.jpg", "image4.jpg"),
                15.0,
                true,
                false,
                true,
                UUID.randomUUID(),
                Arrays.asList(UUID.randomUUID())
        ));

        products.add(new VersionedProductDTO(
                UUID.randomUUID(),
                1,
                "Product C",
                19.99,
                Arrays.asList("image5.jpg"),
                5.0,
                false,
                true,
                false,
                UUID.randomUUID(),
                Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
        ));

        return products;
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