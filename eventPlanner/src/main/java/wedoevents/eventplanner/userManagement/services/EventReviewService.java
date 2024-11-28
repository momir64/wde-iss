package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.EventReview;
import wedoevents.eventplanner.userManagement.repositories.EventReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventReviewService {

    private final EventReviewRepository evenetReviewRepository;

    @Autowired
    public EventReviewService(EventReviewRepository evenetReviewRepository) {
        this.evenetReviewRepository = evenetReviewRepository;
    }

    public EventReview saveReview(EventReview review) {
        return evenetReviewRepository.save(review);
    }

    public Optional<EventReview> getReviewById(UUID id) {
        return evenetReviewRepository.findById(id);
    }

    public List<EventReview> getAllReviews() {
        return evenetReviewRepository.findAll();
    }

    public void deleteReview(UUID id) {
        evenetReviewRepository.deleteById(id);
    }
}