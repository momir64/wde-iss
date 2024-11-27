package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.ListingReview;
import wedoevents.eventplanner.userManagement.repositories.ListingReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListingReviewService {

    private final ListingReviewRepository listingReviewRepository;

    @Autowired
    public ListingReviewService(ListingReviewRepository listingReviewRepository) {
        this.listingReviewRepository = listingReviewRepository;
    }

    public ListingReview saveReview(ListingReview review) {
        return listingReviewRepository.save(review);
    }

    public Optional<ListingReview> getReviewById(UUID id) {
        return listingReviewRepository.findById(id);
    }

    public List<ListingReview> getAllReviews() {
        return listingReviewRepository.findAll();
    }

    public void deleteReview(UUID id) {
        listingReviewRepository.deleteById(id);
    }
}