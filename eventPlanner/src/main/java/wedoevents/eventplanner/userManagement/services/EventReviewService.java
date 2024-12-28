package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.userManagement.dtos.EvenReviewResponseDTO;
import wedoevents.eventplanner.userManagement.dtos.EventReviewDTO;
import wedoevents.eventplanner.userManagement.models.EventReview;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;
import wedoevents.eventplanner.userManagement.repositories.EventReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public EventReview createReview(EventReviewDTO eventReviewDTO, Guest guest, Event event){
        EventReview eventReview = new EventReview();
        eventReview.setEvent(event);
        eventReview.setGuest(guest);
        eventReview.setComment(eventReviewDTO.getComment());
        eventReview.setGrade(eventReviewDTO.getGrade());
        eventReview.setPendingStatus(PendingStatus.PENDING);
        return evenetReviewRepository.save(eventReview);
    }
    public void processReview(EventReview eventReview, boolean isAccepted) {
        eventReview.setPendingStatus(isAccepted? PendingStatus.APPROVED : PendingStatus.DECLINED);
        evenetReviewRepository.save(eventReview);
    }

    public List<EvenReviewResponseDTO> getAcceptedReviewsByEventId(UUID eventId) {
        List<EventReview> eventReviews =  evenetReviewRepository.findByEventIdAndPendingStatus(eventId, PendingStatus.APPROVED);
        return eventReviews.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    public List<EvenReviewResponseDTO> getAllPendingReviews() {
        List<EventReview> reviews = evenetReviewRepository.findByPendingStatus(PendingStatus.PENDING);
        return reviews.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private EvenReviewResponseDTO mapToResponseDTO(EventReview review) {
        EvenReviewResponseDTO dto = new EvenReviewResponseDTO();
        dto.setId(review.getId());
        dto.setGrade(review.getGrade());
        dto.setComment(review.getComment());
        dto.setPendingStatus(review.getPendingStatus());
        dto.setEventId(review.getEvent().getId());
        dto.setGuestId(review.getGuest().getId());
        dto.setGuestName(review.getGuest().getName());
        dto.setGuestSurname(review.getGuest().getSurname());
        return dto;
    }

}