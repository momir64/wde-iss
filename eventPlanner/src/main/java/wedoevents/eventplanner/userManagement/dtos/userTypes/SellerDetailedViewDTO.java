package wedoevents.eventplanner.userManagement.dtos.userTypes;

import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.userManagement.dtos.ListingReviewResponseDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SellerDetailedViewDTO {
    private UUID sellerId;
    private String name;
    private String surname;
    private String address;
    private String telephoneNumber;
    private String city;
    private String description;
    private String email;
    private List<ListingReviewResponseDTO> reviews;
    private Double rating;
}
