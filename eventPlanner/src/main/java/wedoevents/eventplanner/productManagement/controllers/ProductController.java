package wedoevents.eventplanner.productManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.productManagement.dtos.CreateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.UpdateVersionedProductDTO;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductDTO;
import wedoevents.eventplanner.productManagement.services.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "allLatestVersions")
    public List<VersionedProductDTO> getAllProductsWithLatestVersions() {
        return productService.getAllProductsWithLatestVersions();
    }

    @PostMapping
    public VersionedProductDTO createProduct(@RequestBody CreateVersionedProductDTO createVersionedProductDTO) {
        return productService.createProduct(createVersionedProductDTO);
    }

    @PutMapping
    public VersionedProductDTO updateProduct(@RequestBody UpdateVersionedProductDTO updateVersionedProductDTO) {
        return productService.updateVersionedProduct(updateVersionedProductDTO);
    }

    @GetMapping("/{staticProductId}/latestVersion")
    public VersionedProductDTO getProductLatestVersionById(@PathVariable UUID staticProductId) {
        return productService.getVersionedProductById(staticProductId);
    }

    @GetMapping("/{staticProductId}/{version}")
    public VersionedProductDTO getProductLatestVersionById(@PathVariable Integer version,
                                                           @PathVariable UUID staticProductId) {
        return productService.getVersionedProductByStaticProductIdAndVersion(version, staticProductId);
    }

    @DeleteMapping("/{staticProductId}")
    public void deleteProduct(@PathVariable UUID staticProductId) {
        productService.deactivateProduct(staticProductId);
    }
}