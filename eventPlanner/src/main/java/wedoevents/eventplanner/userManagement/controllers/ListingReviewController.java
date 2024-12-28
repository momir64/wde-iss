package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.productManagement.services.ProductService;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.services.ServiceService;
import wedoevents.eventplanner.userManagement.dtos.ListingReviewDTO;
import wedoevents.eventplanner.userManagement.models.ListingReview;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.models.userTypes.EventOrganizer;
import wedoevents.eventplanner.userManagement.services.ListingReviewService;
import wedoevents.eventplanner.userManagement.services.userTypes.EventOrganizerService;
import wedoevents.eventplanner.userManagement.services.userTypes.GuestService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listing-reviews")
public class ListingReviewController {

    private final ListingReviewService listingReviewService;
    private final EventOrganizerService eventOrganizerService;
    private final ProductService productService;
    private final ServiceService serviceService;

    @Autowired
    public ListingReviewController(ListingReviewService listingReviewService, EventOrganizerService eventOrganizerService, ProductService productService, ServiceService serviceService) {
        this.listingReviewService = listingReviewService;
        this.eventOrganizerService = eventOrganizerService;
        this.productService = productService;
        this.serviceService = serviceService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ListingReviewDTO listingReviewDTO) {
        Optional<EventOrganizer> organizer = eventOrganizerService.getEventOrganizerById(listingReviewDTO.getEventOrganizerId());
        if(organizer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ListingType listingType = listingReviewDTO.getListingType();
        if(listingType == ListingType.PRODUCT){
            Optional<StaticProduct> product = productService.getStaticProductById(listingReviewDTO.getListingId());
            if(product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            listingReviewService.createProductReview(listingReviewDTO, organizer.get(), product.get());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            Optional<StaticService> service = serviceService.getStaticServiceById(listingReviewDTO.getListingId());
            if(service.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            listingReviewService.createServiceReview(listingReviewDTO, organizer.get(), service.get());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @PutMapping("process")
    public ResponseEntity<?> processReview(@RequestBody UUID listingReviewId,@RequestBody boolean isAccepted){
        Optional<ListingReview> review = listingReviewService.getReviewById(listingReviewId);
        if(review.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        listingReviewService.processReview(review.get(), isAccepted);
        if(isAccepted) {
            //TODO send review notification (get seller from listing)
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/listing/{listingId}/{isProduct}")
    public ResponseEntity<?> getAcceptedListingReviewsByListingId(@PathVariable UUID listingId, @PathVariable boolean isProduct){
        return new ResponseEntity<>(listingReviewService.getReviewsByListingIdAndStatus(listingId,PendingStatus.APPROVED,isProduct), HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingReviews(){
        return new ResponseEntity<>(listingReviewService.getAllPendingReviews(), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<?> processReview(@RequestBody ListingReviewDTO listingReviewDTO) {
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
    public ResponseEntity<ListingReview> getReviewById(@PathVariable UUID id) {
        return listingReviewService.getReviewById(id)
                                   .map(ResponseEntity::ok)
                                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listing")
    public ResponseEntity<?> getReviewsByListing(
            @RequestParam("listingType") String listingType,
            @RequestParam("listingId") UUID listingId) {
        try {
            // call reviews by listings service
            List<ListingReviewDTO> reviews = mockListingReviewDTOs();
            return ResponseEntity.ok(reviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review data");
        } catch (Exception e){
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