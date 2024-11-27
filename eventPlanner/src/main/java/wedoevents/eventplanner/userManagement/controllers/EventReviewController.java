package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.EventReviewDTO;
import wedoevents.eventplanner.userManagement.models.EventReview;
import wedoevents.eventplanner.userManagement.services.EventReviewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-reviews")
public class EventReviewController {

    private final EventReviewService eventReviewService;

    @Autowired
    public EventReviewController(EventReviewService eventReviewService) {
        this.eventReviewService = eventReviewService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody EventReviewDTO eventReviewDTO) {
        try {
            // call process review service
            return ResponseEntity.ok("Review processed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review data");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to process this review");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    public ResponseEntity<?> processReview(@RequestBody EventReviewDTO listingReviewDTO) {
        try {
            // call process review service
            return ResponseEntity.ok("Review processed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review data");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to process this review");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventReview> getReviewById(@PathVariable UUID id) {
        return eventReviewService.getReviewById(id)
                                 .map(ResponseEntity::ok)
                                 .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventReview>> getAllReviews() {
        return ResponseEntity.ok(eventReviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        eventReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}