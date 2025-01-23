package wedoevents.eventplanner.userManagement.services.userTypes;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.eventManagement.dtos.CalendarEventDTO;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.services.EventService;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.shared.services.mappers.CalendarEventMapper;
import wedoevents.eventplanner.userManagement.dtos.ListingReviewResponseDTO;
import wedoevents.eventplanner.userManagement.dtos.userTypes.SellerDetailedViewDTO;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.repositories.userTypes.SellerRepository;
import wedoevents.eventplanner.userManagement.services.ListingReviewService;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ListingReviewService listingReviewService;
    private final EventService eventService;

    @Autowired
    public SellerService(SellerRepository sellerRepository, ListingReviewService listingReviewService, EventService eventService) {
        this.sellerRepository = sellerRepository;
        this.listingReviewService = listingReviewService;
        this.eventService = eventService;
    }

    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Optional<Seller> getSellerById(UUID id) {
        return sellerRepository.findById(id);
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public void deleteSeller(UUID id) {
        sellerRepository.deleteById(id);
    }

    public Seller createOrUpdateSeller(Seller seller) {
        if (seller.getId() != null && sellerRepository.existsById(seller.getId())) {
            Seller existingSeller = sellerRepository.findById(seller.getId()).orElse(null);
            if (existingSeller != null) {
                BeanUtils.copyProperties(seller, existingSeller, "id");
                return sellerRepository.save(existingSeller);
            }
        }
        // Create new
        return sellerRepository.save(seller);
    }

    public void deleteByProfile(Profile profile){
        sellerRepository.deleteByProfile(profile);
    }

    public Optional<Seller> getSellerByServiceId(UUID serviceId) {
        return sellerRepository.findByServiceId(serviceId);
    }

    public Optional<Seller> getSellerByProductId(UUID productId) {
        return sellerRepository.findByProductId(productId);
    }

    public SellerDetailedViewDTO getSellerDetailedView(UUID id) {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if(sellerOptional.isEmpty()){
            return null;
        }

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();

        Seller seller = sellerOptional.get();
        SellerDetailedViewDTO response = new SellerDetailedViewDTO();
        response.setSellerId(seller.getId());
        response.setName(seller.getName());
        response.setSurname(seller.getSurname());
        response.setAddress(seller.getAddress());
        response.setTelephoneNumber(seller.getTelephoneNumber());
        response.setCity(seller.getCity().getName());
        response.setDescription(seller.getDescription());
        response.setEmail(seller.getProfile().getEmail());
        response.setImage(
                String.format("%s/api/v1/profiles/images/%s/%s", baseUrl, seller.getProfile().getId(), seller.getProfile().getImageName())
        );
        List<ListingReviewResponseDTO> reviews = new ArrayList<>();
        for (StaticProduct product: seller.getMyProducts()){
            List<ListingReviewResponseDTO> productReviews = listingReviewService.getReviewsByListingIdAndStatus(product.getStaticProductId(), PendingStatus.APPROVED,true);
            reviews.addAll(productReviews);
        }
        for(StaticService service: seller.getMyServices()){
            List<ListingReviewResponseDTO> serviceReviews = listingReviewService.getReviewsByListingIdAndStatus(service.getStaticServiceId(), PendingStatus.APPROVED,false);
            reviews.addAll(serviceReviews);
        }
        response.setReviews(reviews);
        double averageGrade = reviews.stream()
                .mapToInt(ListingReviewResponseDTO::getGrade) // Extract grades
                .average() // Calculate average
                .orElse(0);
        response.setRating(averageGrade);
        return response;
    }

    public List<CalendarEventDTO> getCalendarEvents(UUID id) {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if(sellerOptional.isEmpty()){
            return null;
        }
        Seller seller = sellerOptional.get();
        List<Event> allEvents = eventService.getAllEvents();
        List<Event> calendarEvents = new ArrayList<>();
        Set<UUID> sellerServiceIds = seller.getMyServices().stream()
                .map(StaticService::getStaticServiceId)
                .collect(Collectors.toSet());
        Set<UUID> sellerProductIds = seller.getMyProducts().stream()
                .map(StaticProduct::getStaticProductId)
                .collect(Collectors.toSet());
        for (Event event : allEvents) {
            boolean containsSellerService = event.getServiceBudgetItems().stream()
                    .anyMatch(item -> sellerServiceIds.contains(item.getService().getStaticServiceId()));

            boolean containsSellerProduct = event.getProductBudgetItems().stream()
                    .anyMatch(item -> sellerProductIds.contains(item.getProduct().getStaticProductId()));

            if (containsSellerService || containsSellerProduct) {
                calendarEvents.add(event);
            }
        }
        return calendarEvents.stream()
                .map(CalendarEventMapper::toCalendarEventDTO)
                .collect(Collectors.toList());
    }
}