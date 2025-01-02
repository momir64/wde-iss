package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.notificationManagement.models.NotificationType;
import wedoevents.eventplanner.notificationManagement.services.NotificationService;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.productManagement.services.ProductService;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;
import wedoevents.eventplanner.userManagement.dtos.ListingReviewDTO;
import wedoevents.eventplanner.userManagement.dtos.ReviewHandlingDTO;
import wedoevents.eventplanner.userManagement.models.ListingReview;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;
import wedoevents.eventplanner.userManagement.services.ListingReviewService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.SellerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listing-reviews")
public class ListingReviewController {

    private final ListingReviewService listingReviewService;
    private final EventOrganizerService eventOrganizerService;
    private final NotificationService notificationService;
    private final ProductService productService;
    private final ServiceService serviceService;
    private final SellerService sellerService;

    @Autowired
    public ListingReviewController(ListingReviewService listingReviewService, EventOrganizerService eventOrganizerService, ProductService productService, ServiceService serviceService, SellerService sellerService, NotificationService notificationService) {
        this.listingReviewService = listingReviewService;
        this.eventOrganizerService = eventOrganizerService;
        this.notificationService = notificationService;
        this.productService = productService;
        this.serviceService = serviceService;
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ListingReviewDTO listingReviewDTO) {
        Optional<EventOrganizer> organizer = eventOrganizerService.getEventOrganizerById(listingReviewDTO.getEventOrganizerId());
        if (organizer.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ListingType listingType = listingReviewDTO.getListingType();
        if (listingType == ListingType.PRODUCT) {
            Optional<StaticProduct> product = productService.getStaticProductById(listingReviewDTO.getListingId());
            if (product.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            if (!listingReviewService.IsReviewAllowed(organizer.get(), ListingType.PRODUCT, listingReviewDTO.getListingId()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            listingReviewService.createProductReview(listingReviewDTO, organizer.get(), product.get());
        } else {
            Optional<StaticService> service = serviceService.getStaticServiceById(listingReviewDTO.getListingId());
            if (service.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            if (!listingReviewService.IsReviewAllowed(organizer.get(), ListingType.SERVICE, listingReviewDTO.getListingId()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            listingReviewService.createServiceReview(listingReviewDTO, organizer.get(), service.get());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/check/{organizerId}/{isProduct}/{listingId}")
    public ResponseEntity<?> checkIfReviewIsAllowed(@PathVariable UUID organizerId, @PathVariable boolean isProduct, @PathVariable UUID listingId) {
        Optional<EventOrganizer> organizer = eventOrganizerService.getEventOrganizerById(organizerId);
        if (organizer.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        ListingType listingType = isProduct ? ListingType.PRODUCT : ListingType.SERVICE;
        return listingReviewService.IsReviewAllowed(organizer.get(), listingType, listingId) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<?> processReview(@RequestBody ReviewHandlingDTO reviewHandlingDTO) {
        UUID listingReviewId = reviewHandlingDTO.getId();
        boolean isAccepted = reviewHandlingDTO.isDecision();
        Optional<ListingReview> reviewOptional = listingReviewService.getReviewById(listingReviewId);
        if (reviewOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ListingReview review = reviewOptional.get();
        listingReviewService.processReview(review, isAccepted);

        if (isAccepted) {
            boolean isProduct = review.getProduct() != null;
            UUID listingId = isProduct ? review.getProduct().getStaticProductId() : review.getService().getStaticServiceId();
            Optional<Seller> sellerOptional = isProduct ? sellerService.getSellerByProductId(listingId) : sellerService.getSellerByServiceId(listingId);
            if (sellerOptional.isPresent()) {
                NotificationType type = isProduct ? NotificationType.PRODUCT : NotificationType.SERVICE;
                String listingName = isProduct ? productService.getVersionedProductById(review.getProduct().getStaticProductId()).getName() :
                                     serviceService.getVersionedServiceById(review.getService().getStaticServiceId()).getName();
                String title = "New review for " + listingName;
                String message = "Your " + type.toString().toLowerCase() + " " + listingName + " has received a new review from " +
                                 review.getEventOrganizer().getName() + " " + review.getEventOrganizer().getSurname() + "!";
                notificationService.sendNotification(sellerOptional.get().getProfile(), title, message, type, listingId);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listing/{listingId}/{isProduct}")
    public ResponseEntity<?> getAcceptedListingReviewsByListingId(@PathVariable UUID listingId, @PathVariable boolean isProduct) {
        return new ResponseEntity<>(listingReviewService.getReviewsByListingIdAndStatus(listingId, PendingStatus.APPROVED, isProduct), HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingReviews() {
        return new ResponseEntity<>(listingReviewService.getAllPendingReviews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingReview> getReviewById(@PathVariable UUID id) {
        return listingReviewService.getReviewById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listing")
    public ResponseEntity<?> getReviewsByListing(@RequestParam("listingType") String listingType, @RequestParam("listingId") UUID listingId) {
        try {
            // call reviews by listings service
            List<ListingReviewDTO> reviews = mockListingReviewDTOs();
            return ResponseEntity.ok(reviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @GetMapping
    public ResponseEntity<List<ListingReview>> getAllReviews() {
        return ResponseEntity.ok(listingReviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        listingReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    private List<ListingReviewDTO> mockListingReviewDTOs() {
        ListingReviewDTO review1 = new ListingReviewDTO();
        review1.setId(UUID.randomUUID());
        review1.setGrade(5);
        review1.setComment("Excellent product, very satisfied!");
        review1.setPendingStatus(PendingStatus.PENDING);
        review1.setListingType(ListingType.PRODUCT);
        review1.setListingId(UUID.randomUUID());
        review1.setEventOrganizerId(UUID.randomUUID());

        ListingReviewDTO review2 = new ListingReviewDTO();
        review2.setId(UUID.randomUUID());
        review2.setGrade(3);
        review2.setComment("Product is decent but could use some improvements.");
        review2.setPendingStatus(PendingStatus.APPROVED);
        review2.setListingType(ListingType.PRODUCT);
        review2.setListingId(UUID.randomUUID());
        review2.setEventOrganizerId(UUID.randomUUID());

        ListingReviewDTO review3 = new ListingReviewDTO();
        review3.setId(UUID.randomUUID());
        review3.setGrade(1);
        review3.setComment("Not what I expected, very disappointed.");
        review3.setPendingStatus(PendingStatus.DECLINED);
        review3.setListingType(ListingType.SERVICE);
        review3.setListingId(UUID.randomUUID());
        review3.setEventOrganizerId(UUID.randomUUID());

        return Arrays.asList(review1, review2, review3);
    }
}