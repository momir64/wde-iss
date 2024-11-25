package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.models.ListingReview;
import wedoevents.eventplanner.userManagement.services.ReviewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ListingReview> createReview(@RequestBody ListingReview review) {
        ListingReview savedAttempt = reviewService.saveReview(review);
        return ResponseEntity.ok(savedAttempt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingReview> getReviewById(@PathVariable UUID id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ListingReview>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}