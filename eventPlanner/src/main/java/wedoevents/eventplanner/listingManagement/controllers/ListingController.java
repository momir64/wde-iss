package wedoevents.eventplanner.listingManagement.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.listingManagement.services.ListingService;
import wedoevents.eventplanner.shared.config.auth.JwtUtil;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {
    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopListings(@RequestParam(value = "city", required = false) String city, HttpServletRequest request) {
        try {
            try {
                UUID profileId = JwtUtil.extractProfileId(request);
                return ResponseEntity.ok(listingService.getTopListings(city, profileId));
            } catch (Exception e) {
                return ResponseEntity.ok(listingService.getTopListings(city, null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping
    public ResponseEntity<?> searchListings(@RequestParam(value = "searchTerms", required = false) String searchTerms,
                                            @RequestParam(value = "type", required = false) ListingType type,
                                            @RequestParam(value = "category", required = false) UUID category,
                                            @RequestParam(value = "minPrice", required = false) Double minPrice,
                                            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                            @RequestParam(value = "minRating", required = false) Double minRating,
                                            @RequestParam(value = "maxRating", required = false) Double maxRating,
                                            @RequestParam(required = false) String sortBy,
                                            @RequestParam(defaultValue = "asc", required = false) String order,
                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                            HttpServletRequest request) {
        try {
            try {
                UUID profileId = JwtUtil.extractProfileId(request);
                return ResponseEntity.ok(listingService.getListings(null, profileId, searchTerms, type, category, minPrice, maxPrice, minRating, maxRating, sortBy, order, page, size));
            } catch (Exception e) {
                return ResponseEntity.ok(listingService.getListings(null, null, searchTerms, type, category, minPrice, maxPrice, minRating, maxRating, sortBy, order, page, size));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<?> searchListingsForSeller(@PathVariable UUID sellerId,
                                                     @RequestParam(value = "searchTerms", required = false) String searchTerms,
                                                     @RequestParam(value = "type", required = false) ListingType type,
                                                     @RequestParam(value = "category", required = false) UUID category,
                                                     @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                     @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                                     @RequestParam(value = "minRating", required = false) Double minRating,
                                                     @RequestParam(value = "maxRating", required = false) Double maxRating,
                                                     @RequestParam(required = false) String sortBy,
                                                     @RequestParam(defaultValue = "asc", required = false) String order,
                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(listingService.getListings(sellerId, null, searchTerms, type, category, minPrice, maxPrice, minRating, maxRating, sortBy, order, page, size));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }
}