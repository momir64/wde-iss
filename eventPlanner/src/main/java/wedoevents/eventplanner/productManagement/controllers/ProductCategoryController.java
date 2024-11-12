package wedoevents.eventplanner.productManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.services.ProductCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories();
    }

    @PostMapping
    public ProductCategory createProductCategory(@RequestBody ProductCategory category) {
        return productCategoryService.saveProductCategory(category);
    }

    @GetMapping("/{id}")
    public ProductCategory getProductCategoryById(@PathVariable UUID id) {
        return productCategoryService.getProductCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductCategory(@PathVariable UUID id) {
        productCategoryService.deleteProductCategory(id);
    }
}