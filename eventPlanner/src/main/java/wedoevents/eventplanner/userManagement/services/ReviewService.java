package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.ListingReview;
import wedoevents.eventplanner.userManagement.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ListingReview saveReview(ListingReview review) {
        return reviewRepository.save(review);
    }

    public Optional<ListingReview> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    public List<ListingReview> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }
}