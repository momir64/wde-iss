package wedoevents.eventplanner.productManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory saveProductCategory(ProductCategory category) {
        return productCategoryRepository.save(category);
    }

    public ProductCategory getProductCategoryById(UUID id) {
        return productCategoryRepository.findById(id).orElse(null);
    }

    public void deleteProductCategory(UUID id) {
        productCategoryRepository.deleteById(id);
    }
}