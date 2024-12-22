package wedoevents.eventplanner.productManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToBeUpdatedProductsCatalogueDTO {
    private List<CatalogueProductDTO> toBeUpdated;
}
