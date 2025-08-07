package wedoevents.eventplanner.listingManagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.listingManagement.dtos.CreateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.ListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.ReplacingListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.dtos.UpdateListingCategoryDTO;
import wedoevents.eventplanner.listingManagement.services.ListingCategoryService;
import wedoevents.eventplanner.shared.Exceptions.EntityCannotBeDeletedException;

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
    public ResponseEntity<List<ListingCategoryDTO>> getAllActiveListingCategories() {
        List<ListingCategoryDTO> listingCategories = listingCategoryService.getAllActiveListingCategories();
        return ResponseEntity.ok(listingCategories);
    }

    // only used by admin
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListingCategoryDTO> createListingCategory(@RequestBody CreateListingCategoryDTO createListingCategoryDTO) {
        return ResponseEntity.ok(listingCategoryService.createListingCategory(createListingCategoryDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListingCategoryDTO> updateListingCategory(@PathVariable UUID id, @RequestBody UpdateListingCategoryDTO updateListingCategoryDTO) {
        try {
            ListingCategoryDTO updatedListingDTO = listingCategoryService.updateListingCategory(id, updateListingCategoryDTO);
            return ResponseEntity.ok(updatedListingDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteListingCategory(@PathVariable UUID id) {
        try {
            listingCategoryService.deleteListingCategory(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityCannotBeDeletedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Listing category is used by listings");
        }
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ListingCategoryDTO>> getAllPendingListingCategories() {
        return ResponseEntity.ok(listingCategoryService.getAllPendingListingCategories());
    }

    @PutMapping("/pending/replace")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> replaceListingCategory(@RequestBody ReplacingListingCategoryDTO replacingListingCategoryDTO) {
        try {
            listingCategoryService.replaceListingCategory(replacingListingCategoryDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/pending/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListingCategoryDTO> approveListingCategory(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(listingCategoryService.approveListingCategory(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // used by the seller
    @PostMapping("/pending")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ListingCategoryDTO> createPendingListingCategory(@RequestBody CreateListingCategoryDTO createListingCategoryDTO) {
        // no matter what the seller sends, the pending flag must be true
        createListingCategoryDTO.setIsPending(true);
        return ResponseEntity.ok(listingCategoryService.createListingCategory(createListingCategoryDTO));
    }
}
