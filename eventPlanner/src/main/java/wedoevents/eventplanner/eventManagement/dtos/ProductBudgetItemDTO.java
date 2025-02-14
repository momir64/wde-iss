package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ProductBudgetItemDTO {
    private UUID id;
    private UUID productCategoryId;
    private Double maxPrice;
    private UUID productId;
    private Integer productVersion;

    public static ProductBudgetItemDTO toDto(ProductBudgetItem productBudgetItem) {
        return new ProductBudgetItemDTO(
                productBudgetItem.getId(),
                productBudgetItem.getProductCategory().getId(),
                productBudgetItem.getMaxPrice(),
                productBudgetItem.getProduct() == null ? null : productBudgetItem.getProduct().getStaticProductId(),
                productBudgetItem.getProduct() == null ? null : productBudgetItem.getProduct().getVersion()
        );
    }
}
