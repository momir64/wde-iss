package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.userManagement.dtos.ListingReviewDTO;
import wedoevents.eventplanner.userManagement.dtos.ListingReviewResponseDTO;
import wedoevents.eventplanner.userManagement.models.ListingReview;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.repositories.ListingReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public ListingReview createProductReview(ListingReviewDTO listingReviewDTO, EventOrganizer organizer, StaticProduct product){
        ListingReview review = new ListingReview();
        review.setProduct(product);
        review.setService(null);
        review.setComment(listingReviewDTO.getComment());
        review.setEventOrganizer(organizer);
        review.setPendingStatus(PendingStatus.PENDING);
        review.setGrade(listingReviewDTO.getGrade());
        return listingReviewRepository.save(review);
    }


    public ListingReview createServiceReview(ListingReviewDTO listingReviewDTO, EventOrganizer organizer, StaticService service){
        ListingReview review = new ListingReview();
        review.setProduct(null);
        review.setService(service);
        review.setComment(listingReviewDTO.getComment());
        review.setEventOrganizer(organizer);
        review.setPendingStatus(PendingStatus.PENDING);
        review.setGrade(listingReviewDTO.getGrade());
        return listingReviewRepository.save(review);
    }

    public void processReview(ListingReview review, boolean isAccepted) {
        review.setPendingStatus(isAccepted? PendingStatus.APPROVED : PendingStatus.DECLINED);
        listingReviewRepository.save(review);
    }


    public List<ListingReviewResponseDTO> getReviewsByListingIdAndStatus(UUID listingId, PendingStatus status, boolean isProduct){
        List<ListingReview> reviews;
        if(isProduct){
            reviews = listingReviewRepository.findByProductIdAndPendingStatus(listingId, status);
        }else{
            reviews = listingReviewRepository.findByServiceIdAndPendingStatus(listingId, status);
        }
        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ListingReviewResponseDTO> getAllPendingReviews(){
        List<ListingReview> reviews = listingReviewRepository.findByPendingStatus(PendingStatus.PENDING);
        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private ListingReviewResponseDTO mapToDTO(ListingReview review) {
        ListingReviewResponseDTO dto = new ListingReviewResponseDTO();
        dto.setId(review.getId());
        dto.setGrade(review.getGrade());
        dto.setComment(review.getComment());
        dto.setPendingStatus(review.getPendingStatus());
        dto.setListingId(review.getProduct() != null ? review.getProduct().getStaticProductId() : null);
        dto.setListingType((review.getProduct() != null ? ListingType.PRODUCT : ListingType.SERVICE));
        dto.setEventOrganizerId(review.getEventOrganizer().getId());
        dto.setGuestName(review.getEventOrganizer().getName());
        dto.setGuestSurname(review.getEventOrganizer().getSurname());
        return dto;
    }
}