package wedoevents.eventplanner.listingManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.listingManagement.dtos.CreateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.UpdateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.services.ListingCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listing-categories")
public class ListingCategoryController {

    private final ListingCategoryService listingCategoryService;

    @Autowired
    public ListingCategoryController(ListingCategoryService listingCategoryService) {
        this.listingCategoryService = listingCategoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllActiveListingCategories() {
        List<ListingCategoryDTO> listingCategories = listingCategoryService.getAllActiveListingCategories();
        return ResponseEntity.ok(listingCategories);
    }

    @PostMapping
    public ResponseEntity<?> createListingCategory(@RequestBody CreateListingCategoryDTO createListingCategoryDTO) {
        return ResponseEntity.ok(listingCategoryService.createListingCategory(createListingCategoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateListingCategory(@PathVariable UUID id, @RequestBody UpdateListingCategoryDTO updateListingCategoryDTO) {
        try {
            ListingCategoryDTO updatedListingDTO = listingCategoryService.updateListingCategory(id, updateListingCategoryDTO);
            return ResponseEntity.ok(updatedListingDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListingCategory(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingListingCategories() {
        return ResponseEntity.ok(listingCategoryService.getAllPendingListingCategories());
    }

    @PutMapping("/pending/replace/{toBeReplacedId}/{replacingId}")
    public ResponseEntity<?> replaceListingCategory(@PathVariable UUID toBeReplacedId, @PathVariable UUID replacingId) {
        return ResponseEntity.ok("temp");
    }

    @PutMapping("/pending/{id}")
    public ResponseEntity<?> approveListingCategory(@PathVariable UUID id) {
        return ResponseEntity.ok("temp");
    }
}
