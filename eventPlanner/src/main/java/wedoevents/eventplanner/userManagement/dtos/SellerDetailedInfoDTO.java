package wedoevents.eventplanner.userManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.userManagement.models.userTypes.Seller;

@Getter
@Setter
@AllArgsConstructor
public class SellerDetailedInfoDTO {
    private String nameAndSurname;
    private String address;
    private String phone;
    private String description;
    private Double rating;
    private String email;
    private String image;

    public static SellerDetailedInfoDTO toDto(Seller seller) {
        return SellerDetailedInfoDTO.toDto(seller, 0.0);
    }

    public static SellerDetailedInfoDTO toDto(Seller seller, Double rating) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();

        return new SellerDetailedInfoDTO(
                seller.getName() + " " + seller.getSurname(),
                seller.getAddress(),
                seller.getTelephoneNumber(),
                seller.getDescription(),
                rating,
                seller.getProfile().getEmail(),
                String.format("%s/api/v1/profiles/images/%s/%s", baseUrl, seller.getProfile().getId(), seller.getProfile().getImageName())
        );
    }
}
