package wedoevents.eventplanner.listingManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wedoevents.eventplanner.listingManagement.models.ListingType;

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

    public static ListingDTO appendImages(ListingDTO from, ListingDTO to) {
        to.getImages().addAll(from.getImages());
        return to;
    }
}
