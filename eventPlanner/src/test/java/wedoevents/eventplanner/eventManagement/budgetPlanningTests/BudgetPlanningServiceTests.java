package wedoevents.eventplanner.eventManagement.budgetPlanningTests;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import wedoevents.eventplanner.eventManagement.dtos.*;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventType;
import wedoevents.eventplanner.eventManagement.models.ProductBudgetItem;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.EventRepository;
import wedoevents.eventplanner.eventManagement.repositories.ProductBudgetItemRepository;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.eventManagement.services.ProductBudgetItemService;
import wedoevents.eventplanner.eventManagement.services.ServiceBudgetItemService;
import wedoevents.eventplanner.productManagement.models.ProductCategory;
import wedoevents.eventplanner.productManagement.models.StaticProduct;
import wedoevents.eventplanner.productManagement.models.VersionedProduct;
import wedoevents.eventplanner.productManagement.repositories.ProductCategoryRepository;
import wedoevents.eventplanner.productManagement.repositories.VersionedProductRepository;
import wedoevents.eventplanner.serviceManagement.models.ServiceCategory;
import wedoevents.eventplanner.serviceManagement.models.StaticService;
import wedoevents.eventplanner.serviceManagement.models.VersionedService;
import wedoevents.eventplanner.serviceManagement.repositories.ServiceCategoryRepository;
import wedoevents.eventplanner.serviceManagement.repositories.VersionedServiceRepository;
import wedoevents.eventplanner.shared.Exceptions.BuyProductException;
import wedoevents.eventplanner.shared.Exceptions.BuyServiceException;
import wedoevents.eventplanner.userManagement.repositories.userTypes.EventOrganizerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BudgetPlanningServiceTests {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private ProductBudgetItemRepository productBudgetItemRepository;

    @Mock
    private ServiceBudgetItemRepository serviceBudgetItemRepository;

    @Mock
    private VersionedProductRepository versionedProductRepository;

    @Mock
    private VersionedServiceRepository versionedServiceRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ServiceCategoryRepository serviceCategoryRepository;

    @Mock
    private EventOrganizerRepository eventOrganizerRepository;

    private ProductBudgetItemService productBudgetItemService;

    private ServiceBudgetItemService serviceBudgetItemService;

    private Event mockEvent;
    private ProductCategory mockProductCategory;
    private ServiceCategory mockServiceCategory;
    private EventType mockEventType;
    private StaticProduct mockStaticProduct;
    private StaticService mockStaticService;
    private VersionedProduct mockVersionedProduct;
    private VersionedService mockVersionedService;
    private ArrayList<ProductBudgetItem> mockedProductBudgetItemStorage;
    private ArrayList<ServiceBudgetItem> mockedServiceBudgetItemStorage;

    // setup of entities that will not be changed in any test
    @BeforeAll
    public void setUpClass() {
        MockitoAnnotations.openMocks(this);

        mockEventType = new EventType();
        mockEventType.setId(UUID.fromString("ba2b04ef-6a54-4408-9fb1-fb6098da8bab"));
        mockEventType.setName("event type 1");

        mockServiceCategory = new ServiceCategory();
        mockServiceCategory.setName("service category 1");
        mockServiceCategory.setId(UUID.fromString("570ca14a-eb56-4b4e-9ba2-ff94242cc6a0"));

        mockProductCategory = new ProductCategory();
        mockProductCategory.setName("product category 1");
        mockProductCategory.setId(UUID.fromString("50eb63f0-cba4-4e81-988c-7b8a13462a9c"));

        mockStaticProduct = new StaticProduct();
        mockStaticProduct.setStaticProductId(UUID.fromString("414ea2fe-5d03-400d-97c4-3ca113ca99b5"));
        mockStaticProduct.setProductCategory(mockProductCategory);

        mockStaticService = new StaticService();
        mockStaticService.setStaticServiceId(UUID.fromString("e8b870dc-d195-48c8-a4a8-cf06dd321077"));
        mockStaticService.setServiceCategory(mockServiceCategory);

        mockEvent = new Event();
        mockEvent.setId(UUID.fromString("95a2026d-db35-446f-99f9-75a7434abd08"));
        mockEvent.setEventType(mockEventType);
        mockEvent.setDate(LocalDate.now());

        mockVersionedProduct = new VersionedProduct();
        mockVersionedProduct.setStaticProductId(mockStaticProduct.getStaticProductId());
        mockVersionedProduct.setStaticProduct(mockStaticProduct);
        mockVersionedProduct.setVersion(1);
        mockVersionedProduct.setIsLastVersion(true);
        mockVersionedProduct.setPrice(150.0);
        mockVersionedProduct.setSalePercentage(0.04);
        mockVersionedProduct.setName("product 1");

        mockVersionedService = new VersionedService();
        mockVersionedService.setStaticServiceId(mockStaticService.getStaticServiceId());
        mockVersionedService.setStaticService(mockStaticService);
        mockVersionedService.setVersion(1);
        mockVersionedService.setIsLastVersion(true);
        mockVersionedService.setPrice(150.0);
        mockVersionedService.setSalePercentage(0.04);
        mockVersionedService.setName("service 1");

        when(eventRepository.findById(mockEvent.getId())).thenReturn(Optional.of(mockEvent));
        when(eventRepository.existsEventById(mockEvent.getId())).thenReturn(true);
        when(productCategoryRepository.findById(mockProductCategory.getId())).thenReturn(Optional.of(mockProductCategory));
        when(serviceCategoryRepository.findById(mockServiceCategory.getId())).thenReturn(Optional.of(mockServiceCategory));

        // mocking service booking to always book
        when(serviceBudgetItemRepository.doesOverlap(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);

        when(productBudgetItemRepository.save(any(ProductBudgetItem.class)))
                .thenAnswer((Answer<ProductBudgetItem>) invocation -> {
                    ProductBudgetItem itemToSave = invocation.getArgument(0);
                    itemToSave.setId(UUID.randomUUID());
                    mockedProductBudgetItemStorage.add(itemToSave);
                    return itemToSave;
                });

        when(serviceBudgetItemRepository.save(any(ServiceBudgetItem.class)))
                .thenAnswer((Answer<ServiceBudgetItem>) invocation -> {
                    ServiceBudgetItem itemToSave = invocation.getArgument(0);
                    itemToSave.setId(UUID.randomUUID());
                    mockedServiceBudgetItemStorage.add(itemToSave);
                    return itemToSave;
                });

        when(productBudgetItemRepository.findById(any(UUID.class)))
                .thenAnswer((Answer<Optional<ProductBudgetItem>>) invocation -> {
                    UUID idToFind = invocation.getArgument(0);
                    return mockedProductBudgetItemStorage
                            .stream()
                            .filter(pbi -> pbi.getId().equals(idToFind))
                            .findFirst();
                });

        when(serviceBudgetItemRepository.findById(any(UUID.class)))
                .thenAnswer((Answer<Optional<ServiceBudgetItem>>) invocation -> {
                    UUID idToFind = invocation.getArgument(0);
                    return mockedServiceBudgetItemStorage
                            .stream()
                            .filter(pbi -> pbi.getId().equals(idToFind))
                            .findFirst();
                });

        when(versionedProductRepository.getLatestByStaticProductIdAndLatestVersion(
                mockVersionedProduct.getStaticProductId())).thenReturn(Optional.of(mockVersionedProduct));

        when(versionedServiceRepository.getLatestByStaticServiceIdAndLatestVersion(
                mockVersionedService.getStaticServiceId())).thenReturn(Optional.of(mockVersionedService));

        productBudgetItemService = new ProductBudgetItemService(
                productBudgetItemRepository, productCategoryRepository, eventRepository, versionedProductRepository
        );

        serviceBudgetItemService = new ServiceBudgetItemService(
                serviceBudgetItemRepository, versionedServiceRepository, serviceCategoryRepository, eventOrganizerRepository, eventRepository
        );
    }

    // setup of entity attributes that can change per test
    @BeforeEach
    public void setUp() {
        mockEvent.setProductBudgetItems(new ArrayList<>());
        mockEvent.setServiceBudgetItems(new ArrayList<>());

        mockVersionedProduct.setIsPrivate(false);
        mockVersionedProduct.setIsActive(true);
        mockVersionedProduct.setIsAvailable(true);
        mockVersionedProduct.setAvailableEventTypes(Collections.singletonList(mockEventType));

        mockVersionedService.setIsPrivate(false);
        mockVersionedService.setIsActive(true);
        mockVersionedService.setIsAvailable(true);
        mockVersionedService.setAvailableEventTypes(Collections.singletonList(mockEventType));

        mockedProductBudgetItemStorage = new ArrayList<>();
        mockedServiceBudgetItemStorage = new ArrayList<>();
    }

    // tests that create a productBudgetItem without buying it

    @Test
    void testCreateProductBudgetItemForAnEventSuccessful() {
        CreateProductBudgetItemDTO successfulPath = new CreateProductBudgetItemDTO(
                mockEvent.getId(),
                mockProductCategory.getId(),
                500.0
        );

        ProductBudgetItemDTO successfulResponse = productBudgetItemService.createProductBudgetItem(successfulPath);

        assertNull(successfulResponse.getProductId());
        assertNull(successfulResponse.getProductVersion());
        assertEquals(500.0, successfulResponse.getMaxPrice());
        assertEquals(mockProductCategory.getId(), successfulResponse.getProductCategoryId());

        assertEquals(mockEvent.getProductBudgetItems().get(0).getId(), successfulResponse.getId());
    }

    @Test
    void testCreateProductBudgetItemForAnEventThatAlreadyHasThatProductCategory() {
        // inserting a product budget item with predefined category
        ProductBudgetItem existing = new ProductBudgetItem();
        existing.setProductCategory(mockProductCategory);
        mockEvent.getProductBudgetItems().add(existing);

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                mockEvent.getId(),
                mockProductCategory.getId(),
                500.0
        );

        BuyProductException thrown = assertThrows(
                BuyProductException.class,
                () -> productBudgetItemService.createProductBudgetItem(hasCategoryPath)
        );

        assertEquals("Event already contains that category", thrown.getMessage());
    }

    @Test
    void testCreateProductBudgetItemForNotExistingProductCategory() {
        CreateProductBudgetItemDTO nonExistingProductCategory = new CreateProductBudgetItemDTO(
                mockEvent.getId(),
                UUID.fromString("17dcd740-1abc-489f-bee1-ab6800debbc7"),
                500.0
        );

        assertThrows(
                EntityNotFoundException.class,
                () -> productBudgetItemService.createProductBudgetItem(nonExistingProductCategory)
        );
    }

    @Test
    void testCreateProductBudgetItemForNotExistingEvent() {
        CreateProductBudgetItemDTO nonExistingProductCategory = new CreateProductBudgetItemDTO(
                UUID.fromString("17dcd740-1abc-489f-bee1-ab6800debbc7"),
                mockProductCategory.getId(),
                500.0
        );

        assertThrows(
                EntityNotFoundException.class,
                () -> productBudgetItemService.createProductBudgetItem(nonExistingProductCategory)
        );
    }

    // tests that create a serviceBudgetItem without reserving it

    @Test
    void testCreateServiceBudgetItemForAnEventSuccessful() {
        CreateServiceBudgetItemDTO successfulPath = new CreateServiceBudgetItemDTO(
                mockEvent.getId(),
                mockServiceCategory.getId(),
                500.0
        );

        ServiceBudgetItemDTO successfulResponse = serviceBudgetItemService.createServiceBudgetItem(successfulPath);

        assertNull(successfulResponse.getServiceId());
        assertNull(successfulResponse.getServiceVersion());
        assertEquals(500.0, successfulResponse.getMaxPrice());
        assertEquals(mockServiceCategory.getId(), successfulResponse.getServiceCategoryId());

        assertEquals(mockEvent.getServiceBudgetItems().get(0).getId(), successfulResponse.getId());
    }

    @Test
    void testCreateServiceBudgetItemForAnEventThatAlreadyHasThatServiceCategory() {
        // inserting a service budget item with predefined category
        ServiceBudgetItem existing = new ServiceBudgetItem();
        existing.setServiceCategory(mockServiceCategory);
        mockEvent.getServiceBudgetItems().add(existing);

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                mockEvent.getId(),
                mockServiceCategory.getId(),
                500.0
        );

        BuyServiceException thrown = assertThrows(
                BuyServiceException.class,
                () -> serviceBudgetItemService.createServiceBudgetItem(hasCategoryPath)
        );

        assertEquals("Event already contains that category", thrown.getMessage());
    }

    @Test
    void testCreateServiceBudgetItemForNotExistingServiceCategory() {
        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                mockEvent.getId(),
                UUID.fromString("17dcd740-1abc-489f-bee1-ab6800debbc7"),
                500.0
        );

        assertThrows(
                EntityNotFoundException.class,
                () -> serviceBudgetItemService.createServiceBudgetItem(hasCategoryPath)
        );
    }

    @Test
    void testCreateServiceBudgetItemForNotExistingEvent() {
        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                UUID.fromString("17dcd740-1abc-489f-bee1-ab6800debbc7"),
                mockServiceCategory.getId(),
                500.0
        );

        assertThrows(
                EntityNotFoundException.class,
                () -> serviceBudgetItemService.createServiceBudgetItem(hasCategoryPath)
        );
    }

    // it doesn't make sense to test budget item retrieval because
    // that is just testing the .save() method from the repository

    // tests that buy a product

    @Test
    void testBuyProductWhileHavingProductBudgetItemSuccessful() {
        CreateProductBudgetItemDTO successfulCreatePath = new CreateProductBudgetItemDTO(
                mockEvent.getId(),
                mockProductCategory.getId(),
                500.0
        );

        ProductBudgetItemDTO successfulCreateResponse = productBudgetItemService.createProductBudgetItem(successfulCreatePath);

        BuyProductDTO successfulBuyPath = new BuyProductDTO(
                mockEvent.getId(),
                successfulCreateResponse.getId(),
                mockVersionedProduct.getStaticProductId()
        );

        ProductBudgetItemDTO successfulBuyResponse = productBudgetItemService.buyProduct(successfulBuyPath);

        assertEquals(mockVersionedProduct.getStaticProductId(), successfulBuyResponse.getProductId());
        assertEquals(mockVersionedProduct.getVersion(), successfulBuyResponse.getProductVersion());
        assertEquals(500.0, successfulBuyResponse.getMaxPrice());
        assertEquals(mockProductCategory.getId(), successfulBuyResponse.getProductCategoryId());

        assertEquals(mockEvent.getProductBudgetItems().get(0).getId(), successfulBuyResponse.getId());
    }

    @Test
    void testBuyProductWithoutProductBudgetItemSuccessful() {
        BuyProductDTO successfulBuyPath = new BuyProductDTO();
        successfulBuyPath.setProductId(mockVersionedProduct.getStaticProductId());
        successfulBuyPath.setEventId(mockEvent.getId());

        ProductBudgetItemDTO successfulBuyResponse = productBudgetItemService.buyProduct(successfulBuyPath);

        assertEquals(mockVersionedProduct.getStaticProductId(), successfulBuyResponse.getProductId());
        assertEquals(mockVersionedProduct.getVersion(), successfulBuyResponse.getProductVersion());
        assertEquals(0.0, successfulBuyResponse.getMaxPrice());
        assertEquals(mockProductCategory.getId(), successfulBuyResponse.getProductCategoryId());

        assertEquals(mockEvent.getProductBudgetItems().get(0).getId(), successfulBuyResponse.getId());
    }

    @Test
    void testBuyProductWhileHavingProductBudgetItemButNonExistingProductBudgetItem() {
        BuyProductDTO buyPath = new BuyProductDTO(
                mockEvent.getId(),
                UUID.fromString("d12be171-f83e-4130-9cac-c94a3a444a3b"),
                mockVersionedProduct.getStaticProductId()
        );

        assertThrows(EntityNotFoundException.class,
                () -> productBudgetItemService.buyProduct(buyPath));
    }

    @Test
    void testBuyProductWhileHavingProductBudgetItemButPriceTooMuch() {
        CreateProductBudgetItemDTO createPBI = new CreateProductBudgetItemDTO(
                mockEvent.getId(),
                mockProductCategory.getId(),
                100.0
        );

        ProductBudgetItemDTO successfulCreateResponse = productBudgetItemService.createProductBudgetItem(createPBI);

        BuyProductDTO priceTooMuch = new BuyProductDTO(
                mockEvent.getId(),
                successfulCreateResponse.getId(),
                mockVersionedProduct.getStaticProductId()
        );

        BuyProductException exception = assertThrows(BuyProductException.class,
                () -> productBudgetItemService.buyProduct(priceTooMuch));

        assertEquals("Product too expensive", exception.getMessage());
    }

    // the part that checks for the product is in the buyProduct method,
    // so there is no need to test for having productBudgetItem beforehand
    @Test
    void testBuyProductButNonExistingProduct() {
        BuyProductDTO buyPath = new BuyProductDTO();
        buyPath.setProductId(UUID.fromString("6e01db61-a686-4d3b-abbc-87e313f01dd6"));
        buyPath.setEventId(mockEvent.getId());

        assertThrows(EntityNotFoundException.class,
                () -> productBudgetItemService.buyProduct(buyPath));
    }

    // the part that checks for the product visibility is in the buyProduct method,
    // so there is no need to test for having productBudgetItem beforehand
    @ParameterizedTest
    @CsvSource(value = {
            "false, false, true",
            "true, true, true",
            "true, false, false",
    })
    void testBuyProductButNotVisibleProduct(String isActiveString, String isPrivateString, String isAvailableString) {
        boolean isPrivate = Boolean.parseBoolean(isPrivateString);
        boolean isAvailable = Boolean.parseBoolean(isAvailableString);
        boolean isActive = Boolean.parseBoolean(isActiveString);

        mockVersionedProduct.setIsPrivate(isPrivate);
        mockVersionedProduct.setIsAvailable(isAvailable);
        mockVersionedProduct.setIsActive(isActive);

        BuyProductDTO buyPath = new BuyProductDTO();
        buyPath.setProductId(mockVersionedProduct.getStaticProductId());
        buyPath.setEventId(mockEvent.getId());

        BuyProductException exception = assertThrows(BuyProductException.class,
                () -> productBudgetItemService.buyProduct(buyPath));

        assertEquals("Can't buy product that is not visible to you", exception.getMessage());
    }

    // the part that checks for the product available event types is in the buyProduct method,
    // so there is no need to test for having productBudgetItem beforehand
    @Test
    void testBuyProductButEventEventTypeIsNotInProductAvailableEventTypes() {
        mockVersionedProduct.setAvailableEventTypes(new ArrayList<>());

        BuyProductDTO buyPath = new BuyProductDTO();
        buyPath.setProductId(mockVersionedProduct.getStaticProductId());
        buyPath.setEventId(mockEvent.getId());

        BuyProductException exception = assertThrows(BuyProductException.class,
                () -> productBudgetItemService.buyProduct(buyPath));

        assertEquals("Event type not in event's available event types", exception.getMessage());
    }

    @Test
    void testBuyProductButNonExistingEvent() {
        BuyProductDTO buyPath = new BuyProductDTO();
        buyPath.setProductId(mockVersionedProduct.getStaticProductId());
        buyPath.setEventId(UUID.fromString("bdde8be7-fd3e-4a30-a16d-5fb7cd2bb835"));

        assertThrows(EntityNotFoundException.class,
                () -> productBudgetItemService.buyProduct(buyPath));
    }

    // tests that reserve (reserving is mocked) a service

    @Test
    void testBuyServiceWhileHavingServiceBudgetItemSuccessful() {
        CreateServiceBudgetItemDTO successfulCreatePath = new CreateServiceBudgetItemDTO(
                mockEvent.getId(),
                mockServiceCategory.getId(),
                500.0
        );

        serviceBudgetItemService.createServiceBudgetItem(successfulCreatePath);

        BuyServiceDTO successfulBuyPath = new BuyServiceDTO();
        successfulBuyPath.setEventId(mockEvent.getId());
        successfulBuyPath.setServiceId(mockVersionedService.getStaticServiceId());
        successfulBuyPath.setEndTime("00:00");
        successfulBuyPath.setStartTime("00:00");

        ServiceBudgetItemDTO successfulBuyResponse = serviceBudgetItemService.buyService(successfulBuyPath);

        assertEquals(mockVersionedService.getStaticServiceId(), successfulBuyResponse.getServiceId());
        assertEquals(mockVersionedService.getVersion(), successfulBuyResponse.getServiceVersion());
        assertEquals(500.0, successfulBuyResponse.getMaxPrice());
        assertEquals(mockServiceCategory.getId(), successfulBuyResponse.getServiceCategoryId());

        assertEquals(mockEvent.getServiceBudgetItems().get(0).getId(), successfulBuyResponse.getId());
    }

    @Test
    void testBuyServiceWithoutServiceBudgetItemSuccessful() {
        BuyServiceDTO successfulBuyPath = new BuyServiceDTO();
        successfulBuyPath.setServiceId(mockVersionedService.getStaticServiceId());
        successfulBuyPath.setEventId(mockEvent.getId());
        successfulBuyPath.setEndTime("00:00");
        successfulBuyPath.setStartTime("00:00");

        ServiceBudgetItemDTO successfulBuyResponse = serviceBudgetItemService.buyService(successfulBuyPath);

        assertEquals(mockVersionedService.getStaticServiceId(), successfulBuyResponse.getServiceId());
        assertEquals(mockVersionedService.getVersion(), successfulBuyResponse.getServiceVersion());
        assertEquals(0.0, successfulBuyResponse.getMaxPrice());
        assertEquals(mockServiceCategory.getId(), successfulBuyResponse.getServiceCategoryId());

        assertEquals(mockEvent.getServiceBudgetItems().get(0).getId(), successfulBuyResponse.getId());
    }

    @Test
    void testBuyServiceWhileHavingServiceBudgetItemButPriceTooMuch() {
        CreateServiceBudgetItemDTO createPBI = new CreateServiceBudgetItemDTO(
                mockEvent.getId(),
                mockServiceCategory.getId(),
                100.0
        );

        serviceBudgetItemService.createServiceBudgetItem(createPBI);

        BuyServiceDTO priceTooMuch = new BuyServiceDTO();
        priceTooMuch.setServiceId(mockVersionedService.getStaticServiceId());
        priceTooMuch.setEventId(mockEvent.getId());
        priceTooMuch.setEndTime("00:00");
        priceTooMuch.setStartTime("00:00");

        BuyServiceException exception = assertThrows(BuyServiceException.class,
                () -> serviceBudgetItemService.buyService(priceTooMuch));

        assertEquals("Service too expensive", exception.getMessage());
    }

    // the part that checks for the service is in the buyService method,
    // so there is no need to test for having serviceBudgetItem beforehand
    @Test
    void testBuyServiceButNonExistingService() {
        BuyServiceDTO buyPath = new BuyServiceDTO();
        buyPath.setServiceId(UUID.fromString("6e01db61-a686-4d3b-abbc-87e313f01dd6"));
        buyPath.setEventId(mockEvent.getId());
        buyPath.setEndTime("00:00");
        buyPath.setStartTime("00:00");

        assertThrows(EntityNotFoundException.class,
                () -> serviceBudgetItemService.buyService(buyPath));
    }

    // the part that checks for the service visibility is in the buyService method,
    // so there is no need to test for having serviceBudgetItem beforehand
    @ParameterizedTest
    @CsvSource(value = {
            "false, false, true",
            "true, true, true",
            "true, false, false",
    })
    void testBuyServiceButNotVisibleService(String isActiveString, String isPrivateString, String isAvailableString) {
        boolean isPrivate = Boolean.parseBoolean(isPrivateString);
        boolean isAvailable = Boolean.parseBoolean(isAvailableString);
        boolean isActive = Boolean.parseBoolean(isActiveString);

        mockVersionedService.setIsPrivate(isPrivate);
        mockVersionedService.setIsAvailable(isAvailable);
        mockVersionedService.setIsActive(isActive);

        BuyServiceDTO buyPath = new BuyServiceDTO();
        buyPath.setServiceId(mockVersionedService.getStaticServiceId());
        buyPath.setEventId(mockEvent.getId());
        buyPath.setEndTime("00:00");
        buyPath.setStartTime("00:00");

        BuyServiceException exception = assertThrows(BuyServiceException.class,
                () -> serviceBudgetItemService.buyService(buyPath));

        assertEquals("Can't buy service that is not visible to you", exception.getMessage());
    }

    // the part that checks for the service available event types is in the buyService method,
    // so there is no need to test for having serviceBudgetItem beforehand
    @Test
    void testBuyServiceButEventEventTypeIsNotInServiceAvailableEventTypes() {
        mockVersionedService.setAvailableEventTypes(new ArrayList<>());

        BuyServiceDTO buyPath = new BuyServiceDTO();
        buyPath.setServiceId(mockVersionedService.getStaticServiceId());
        buyPath.setEventId(mockEvent.getId());
        buyPath.setEndTime("00:00");
        buyPath.setStartTime("00:00");

        BuyServiceException exception = assertThrows(BuyServiceException.class,
                () -> serviceBudgetItemService.buyService(buyPath));

        assertEquals("Event type not in event's available event types", exception.getMessage());
    }

    @Test
    void testBuyServiceButNonExistingEvent() {
        BuyServiceDTO buyPath = new BuyServiceDTO();
        buyPath.setServiceId(mockVersionedService.getStaticServiceId());
        buyPath.setEventId(UUID.fromString("bdde8be7-fd3e-4a30-a16d-5fb7cd2bb835"));
        buyPath.setEndTime("00:00");
        buyPath.setStartTime("00:00");

        assertThrows(EntityNotFoundException.class,
                () -> serviceBudgetItemService.buyService(buyPath));
    }

    // it doesn't make sense to test service's deletion of PBI / PBS items
    // because all the logic is contained in the respective repositories
    // who are mocked for these tests
}
