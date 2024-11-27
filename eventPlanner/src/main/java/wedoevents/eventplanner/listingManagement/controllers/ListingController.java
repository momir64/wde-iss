package wedoevents.eventplanner.listingManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.listingManagement.dtos.ListingDTO;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.services.ProductService;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ListingController {
    private final ProductService productService;
    private final ServiceService serviceService;

    @Autowired
    public ListingController(ProductService productService, ServiceService serviceService) {
        this.productService = productService;
        this.serviceService = serviceService;
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopListings(@RequestParam(value = "city", required = false) String city) {
        try {
            List<ListingDTO> listings = buildMockListings(5);
            return ResponseEntity.ok(listings);
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
                                            @RequestParam(required = false) String order,
                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            List<ListingDTO> listings = buildMockListings(10);
            return ResponseEntity.ok(listings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    private List<ListingDTO> buildMockListings(int n) {
        List<ListingDTO> events = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            events.add(new ListingDTO(
                    ListingType.values()[i % 2],
                    UUID.randomUUID(),
                    i,
                    String.format("Listing %d", i),
                    String.format("This is the best listing ever %d!", i),
                    100 * i + 99.,
                    i * 0.9 % 5,
                    Arrays.asList("image url 1", "image url 2", "image url 3"),
                    0.1 * i,
                    true
            ));
        }

        return events;
    }
}