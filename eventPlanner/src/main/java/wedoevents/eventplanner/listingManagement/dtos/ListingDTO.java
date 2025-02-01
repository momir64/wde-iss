package wedoevents.eventplanner.listingManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.listingManagement.models.ListingType;
import wedoevents.eventplanner.productManagement.dtos.VersionedProductDTO;
import wedoevents.eventplanner.serviceManagement.dtos.VersionedServiceDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListingDTO {
    private ListingType type;
    private UUID id;
    private Integer version;
    private String name;
    private String description;
    private Double price;
    private Double oldPrice;
    private Double rating;
    private List<String> images;
    private Boolean isAvailable;

    public ListingDTO(Object[] data) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();
        this.type = ListingType.valueOf((String) data[0]);
        this.id = (UUID) data[1];
        this.version = (Integer) data[2];
        this.name = (String) data[3];
        this.description = (String) data[4];
        this.price = (Double) data[6];
        this.oldPrice = price.equals(data[5]) ? null : (Double) data[5];
        this.isAvailable = (Boolean) data[7];
        this.images = new ArrayList<>(List.of(String.format("%s/api/v1/%ss/%s/%d/images/%s", baseUrl, ((String) data[0]).toLowerCase(), id, version, data[8])));
        this.rating = 0.0; // todo
    }
    public ListingDTO(VersionedProductDTO product) {
        this.type = ListingType.PRODUCT;
        this.id = product.getStaticProductId();
        this.version = product.getVersion();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.oldPrice = product.getOldPrice();
        this.rating = product.getRating();
        this.images = product.getImages(); //TODO check and fix
        this.isAvailable = product.getIsAvailable();
    }
    public ListingDTO(VersionedServiceDTO service) {
        this.type = ListingType.SERVICE;
        this.id = service.getStaticServiceId();
        this.version = service.getVersion();
        this.name = service.getName();
        this.description = service.getDescription();
        this.price = service.getPrice();
        this.oldPrice = service.getOldPrice();
        this.rating = service.getRating();
        this.images = service.getImages(); //TODO check and fix
        this.isAvailable = service.getIsAvailable();
    }
    public static ListingDTO appendImages(ListingDTO from, ListingDTO to) {
        to.getImages().addAll(from.getImages());
        return to;
    }
}
