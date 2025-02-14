package wedoevents.eventplanner.eventManagement.budgetPlanningTests;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.ProductBudgetItemRepository;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
@ActiveProfiles("test")
public class BudgetPlanningRepositoryTests {
    @Autowired
    private ServiceBudgetItemRepository serviceBudgetItemRepository;

    @Autowired
    private ProductBudgetItemRepository productBudgetItemRepository;

    // tests that delete a planned productBudgetItem

    @Test
    void testDeletePlannedProductBudgetItemSuccessful() {
        UUID productBudgetItemId = UUID.fromString("9f1fed25-b54b-4322-b206-341c9e2daa47");

        assertTrue(productBudgetItemRepository.existsById(productBudgetItemId));

        ProductBudgetItem matchingProductBudgetItem = productBudgetItemRepository.findById(productBudgetItemId).get();

        assertNull(matchingProductBudgetItem.getProduct());

        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
        UUID productCategoryId = matchingProductBudgetItem.getProductCategory().getId();

        boolean hasProduct = productBudgetItemRepository.hasBoughtProductByEventIdAndProductCategoryId(eventId, productCategoryId);
        assertFalse(hasProduct);

        productBudgetItemRepository.removeEventEmptyProductCategory(eventId, productCategoryId);

        assertFalse(productBudgetItemRepository.existsById(productBudgetItemId));
    }

    @Test
    void testDeletePlannedProductBudgetItemWithReservedProduct() {
        UUID productBudgetItemId = UUID.fromString("e3565a02-8603-4ed7-b207-467b6f4d2120");

        assertTrue(productBudgetItemRepository.existsById(productBudgetItemId));

        ProductBudgetItem matchingProductBudgetItem = productBudgetItemRepository.findById(productBudgetItemId).get();

        assertNotNull(matchingProductBudgetItem.getProduct());

        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
        UUID productCategoryId = matchingProductBudgetItem.getProductCategory().getId();

        boolean hasProduct = productBudgetItemRepository.hasBoughtProductByEventIdAndProductCategoryId(eventId, productCategoryId);
        assertTrue(hasProduct);
    }

    // tests that delete a planned serviceBudgetItem
    // note: only one function is tested, as the other two are for service reservation logic,
    // not for budget planning logic

    @Test
    void testDeletePlannedServiceBudgetItemSuccessful() {
        UUID serviceBudgetItemId = UUID.fromString("be10be60-9ab2-46a6-acbf-a310b018cdfa");

        assertTrue(serviceBudgetItemRepository.existsById(serviceBudgetItemId));

        ServiceBudgetItem matchingServiceBudgetItem = serviceBudgetItemRepository.findById(serviceBudgetItemId).get();

        assertNull(matchingServiceBudgetItem.getService());

        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
        UUID serviceCategoryId = matchingServiceBudgetItem.getServiceCategory().getId();

        boolean hasService = serviceBudgetItemRepository.hasBoughtServiceByEventIdAndServiceCategoryId(eventId, serviceCategoryId);
        assertFalse(hasService);

        serviceBudgetItemRepository.removeEventEmptyServiceCategory(eventId, serviceCategoryId);

        assertFalse(serviceBudgetItemRepository.existsById(serviceBudgetItemId));
    }

    @Test
    void testDeletePlannedServiceBudgetItemWithReservedService() {
        UUID serviceBudgetItemId = UUID.fromString("89d39b80-997d-4ffe-ba9d-74fc4e6c0e0b");

        assertTrue(serviceBudgetItemRepository.existsById(serviceBudgetItemId));

        ServiceBudgetItem matchingServiceBudgetItem = serviceBudgetItemRepository.findById(serviceBudgetItemId).get();

        assertNotNull(matchingServiceBudgetItem.getService());

        UUID eventId = UUID.fromString("ea0d1c1b-67fa-4f7e-b00d-78129d742d01");
        UUID serviceCategoryId = matchingServiceBudgetItem.getServiceCategory().getId();

        boolean hasService = serviceBudgetItemRepository.hasBoughtServiceByEventIdAndServiceCategoryId(eventId, serviceCategoryId);
        assertTrue(hasService);
    }
}
