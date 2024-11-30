package wedoevents.eventplanner.listingManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.services.ListingCategoryService;
import wedoevents.eventplanner.productManagement.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listing-categories")
public class ListingCategoryController {

    private final ListingCategoryService listingCategoryService;

    @Autowired
    public ListingCategoryController(ListingCategoryService listingCategoryService) {
        this.listingCategoryService = listingCategoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllListingCategories() {
        List<ListingCategoryDTO> listingCategories = listingCategoryService.getAllListingCategories();
        return ResponseEntity.ok(listingCategories);
    }

}
