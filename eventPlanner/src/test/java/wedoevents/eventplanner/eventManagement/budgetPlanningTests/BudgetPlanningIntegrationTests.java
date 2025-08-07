package wedoevents.eventplanner.eventManagement.budgetPlanningTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import wedoevents.eventplanner.eventManagement.dtos.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetPlanningIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public void setUpDatabase() {
        // referential integrity is turned off, because H2 sees inserting of
        // primary keys that are set to GENERATED_VALUE (the default) as
        // a violation of referential integrity, and in our file,
        // all primary keys are predefined, to be able to connect entities
        // referential integrity is never broken because this same script is
        // used in the PostgreSQL database where referential integrity is turned on
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
    }

    @AfterEach
    public void truncateAllTables() {
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'PUBLIC'";

        List<String> tableNames = jdbcTemplate.queryForList(query, String.class);

        for (String tableName : tableNames) {
            String truncateQuery = "TRUNCATE TABLE " + tableName;
            jdbcTemplate.execute(truncateQuery);
        }
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateProductBudgetItemForAnEventSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                productCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ProductBudgetItemDTO createdPBI = responseEntity.getBody();

        assertNotNull(createdPBI);
        assertNull(createdPBI.getProductId());
        assertNull(createdPBI.getProductVersion());
        assertEquals(500.0, createdPBI.getMaxPrice());
        assertEquals(productCategoryId, createdPBI.getProductCategoryId());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateProductBudgetItemForAnEventThatAlreadyHasThatProductCategory() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                productCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity2.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateProductBudgetItemForNotExistingProductCategory() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID nonExistingProductCategoryId = UUID.fromString("743a15be-352e-49e4-9b8c-4e95337c0dab");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                nonExistingProductCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateProductBudgetItemForNotExistingEvent() {
        UUID nonExistingEventId = UUID.fromString("743a15be-352e-49e4-9b8c-4e95337c0dab");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                nonExistingEventId,
                productCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity1.getStatusCode());
    }

    // tests that create a serviceBudgetItem without reserving it

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateServiceBudgetItemForAnEventSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                eventId,
                serviceCategoryId,
                500.0
        );

        ResponseEntity<ServiceBudgetItemDTO> responseEntity = restTemplate.postForEntity("/api/v1/service-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ServiceBudgetItemDTO createdPBI = responseEntity.getBody();

        assertNotNull(createdPBI);
        assertNull(createdPBI.getServiceId());
        assertNull(createdPBI.getServiceVersion());
        assertEquals(500.0, createdPBI.getMaxPrice());
        assertEquals(serviceCategoryId, createdPBI.getServiceCategoryId());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateServiceBudgetItemForAnEventThatAlreadyHasThatServiceCategory() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                eventId,
                serviceCategoryId,
                500.0
        );

        ResponseEntity<ServiceBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/service-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/service-budget-items",
                new HttpEntity<>(hasCategoryPath),
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity2.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateServiceBudgetItemForNotExistingServiceCategory() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID nonExistingServiceCategoryId = UUID.fromString("4d7fbed0-3f68-4e09-bd0c-db6727a000aa");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                eventId,
                nonExistingServiceCategoryId,
                500.0
        );

        ResponseEntity<ServiceBudgetItemDTO> responseEntity = restTemplate.postForEntity("/api/v1/service-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testCreateServiceBudgetItemForNotExistingEvent() {
        UUID nonExistingEventId = UUID.fromString("81bd4bcd-3144-44bc-bc5b-b6f942064911");
        UUID serviceCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                nonExistingEventId,
                serviceCategoryId,
                500.0
        );

        ResponseEntity<ServiceBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/service-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity1.getStatusCode());
    }

    // tests that test retrieving product budget items

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testRetrieveProductBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                productCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO createdPBI = responseEntity1.getBody();
        assertNotNull(createdPBI);

        ResponseEntity<ProductBudgetItemDTO> responseEntity2 = restTemplate.getForEntity(
                "/api/v1/product-budget-items/" + createdPBI.getId().toString(),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO retrievedPBI = responseEntity2.getBody();

        assertEquals(createdPBI, retrievedPBI);
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testRetrieveNonExistingProductBudgetItem() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/api/v1/product-budget-items/" + "7d7208f4-7e2c-409b-ad30-194ba1cd3a7c",
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // tests that test retrieving service budget items

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testRetrieveServiceBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                eventId,
                serviceCategoryId,
                500.0
        );

        ResponseEntity<ServiceBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/service-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ServiceBudgetItemDTO createdSBI = responseEntity1.getBody();
        assertNotNull(createdSBI);

        ResponseEntity<ServiceBudgetItemDTO> responseEntity2 = restTemplate.getForEntity(
                "/api/v1/service-budget-items/" + createdSBI.getId().toString(),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ServiceBudgetItemDTO retrievedSBI = responseEntity2.getBody();

        assertEquals(createdSBI, retrievedSBI);
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testRetrieveNonExistingServiceBudgetItem() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/api/v1/service-budget-items/" + "7d7208f4-7e2c-409b-ad30-194ba1cd3a7c",
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // tests that buy a product

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductWhileHavingProductBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");
        UUID versionedProductId = UUID.fromString("5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                productCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO createdPBI = responseEntity1.getBody();

        assertNotNull(createdPBI);

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductBudgetItemId(createdPBI.getId());
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<ProductBudgetItemDTO> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());

        ProductBudgetItemDTO pbiWithProduct = responseEntity2.getBody();

        assertNotNull(pbiWithProduct);

        assertEquals(versionedProductId, pbiWithProduct.getProductId());
        assertEquals(1, pbiWithProduct.getProductVersion());
        assertEquals(500.0, pbiWithProduct.getMaxPrice());
        assertEquals(productCategoryId, pbiWithProduct.getProductCategoryId());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductWithoutProductBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");
        UUID versionedProductId = UUID.fromString("5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26");

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO pbiWithProduct = responseEntity1.getBody();

        assertNotNull(pbiWithProduct);

        assertNotNull(pbiWithProduct.getId());
        assertEquals(versionedProductId, pbiWithProduct.getProductId());
        assertEquals(1, pbiWithProduct.getProductVersion());
        assertEquals(0.0, pbiWithProduct.getMaxPrice());
        assertEquals(productCategoryId, pbiWithProduct.getProductCategoryId());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductWhileHavingProductBudgetItemButNonExistingProductBudgetItem() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");
        UUID versionedProductId = UUID.fromString("5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                productCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO createdPBI = responseEntity1.getBody();

        assertNotNull(createdPBI);

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductBudgetItemId(UUID.fromString("b41fd083-4cd6-4340-a292-45be6d22e6ce"));
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<ProductBudgetItemDTO> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductWhileHavingProductBudgetItemButPriceTooMuch() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");
        UUID versionedProductId = UUID.fromString("5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                productCategoryId,
                10.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO createdPBI = responseEntity1.getBody();

        assertNotNull(createdPBI);

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductBudgetItemId(createdPBI.getId());
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity2.getStatusCode());
    }

    // the part that checks for the product is in the buyProduct method,
    // so there is no need to test for having productBudgetItem beforehand
    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductButNonExistingProduct() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedProductId = UUID.fromString("858b591b-2cfe-44a2-9909-ceef50980293");

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    // the part that checks for the product visibility is in the buyProduct method,
    // so there is no need to test for having productBudgetItem beforehand
    @ParameterizedTest
    @CsvSource(value = {
            "8e862226-257d-473c-94eb-aedff374dedf",
            "fdf4285e-1619-4f13-819a-0dc6843c4ce1",
            "11460238-d909-4b1e-ba1b-651904b36eb0"
    })
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductButNotVisibleProduct(String productId) {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");

        // these entities are set up so that one of the visibility booleans is always incorrect
        UUID versionedProductId = UUID.fromString(productId);

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity2.getStatusCode());
    }

    // the part that checks for the product available event types is in the buyProduct method,
    // so there is no need to test for having productBudgetItem beforehand
    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductButEventEventTypeIsNotInProductAvailableEventTypes() {
        // event that doesn't allow fireworks
        UUID eventId = UUID.fromString("379c96eb-7391-48b4-adc3-f07095576d3b");

        // firework product
        UUID versionedProductId = UUID.fromString("5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26");

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity2.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyProductButNonExistingEvent() {
        // non-existing event
        UUID eventId = UUID.fromString("2e878e9b-c458-482b-992c-caad56211c24");

        UUID versionedProductId = UUID.fromString("5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26");

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    // tests that test fail cases of reserving a service
    // can't test the successful paths because that requires
    // service reservation logic, which can't be mocked here
    // so those tests are done in the service booking integration tests file

    // the part that checks for the service is in the buyService method,
    // so there is no need to test for having serviceBudgetItem beforehand
    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyServiceButNonExistingService() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedServiceId = UUID.fromString("858b591b-2cfe-44a2-9909-ceef50980293");

        BuyServiceDTO buyServiceDTO = new BuyServiceDTO();
        buyServiceDTO.setEventId(eventId);
        buyServiceDTO.setServiceId(versionedServiceId);
        buyServiceDTO.setStartTime("00:00");
        buyServiceDTO.setEndTime("00:00");

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/service-budget-items/buy",
                new HttpEntity<>(buyServiceDTO),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    // the part that checks for the service visibility is in the buyService method,
    // so there is no need to test for having serviceBudgetItem beforehand
    @ParameterizedTest
    @CsvSource(value = {
            "db3fc51a-0775-44ba-b031-955503ed74d1",
            "f6bb3ca6-cd9c-42dc-9fe0-fd0b84dd79ca",
            "96997487-e316-46f0-8868-d795b80157ba",
    })
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyServiceButNotVisibleService(String serviceId) {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");

        // these entities are set up so that one of the visibility booleans is always incorrect
        UUID versionedServiceId = UUID.fromString(serviceId);

        BuyServiceDTO buyServiceDTO = new BuyServiceDTO();
        buyServiceDTO.setEventId(eventId);
        buyServiceDTO.setServiceId(versionedServiceId);
        buyServiceDTO.setStartTime("00:00");
        buyServiceDTO.setEndTime("00:00");

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/service-budget-items/buy",
                new HttpEntity<>(buyServiceDTO),
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity2.getStatusCode());
    }

    // the part that checks for the service available event types is in the buyService method,
    // so there is no need to test for having serviceBudgetItem beforehand
    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyServiceButEventEventTypeIsNotInServiceAvailableEventTypes() {
        // event that doesn't allow guest transportations
        UUID eventId = UUID.fromString("2c9f1c4d-1cb5-48f2-8618-78e3be06f27f");

        // guest transportation service
        UUID versionedServiceId = UUID.fromString("8d92004c-ce17-4248-ac60-e0a3750bf083");

        BuyServiceDTO buyServiceDTO = new BuyServiceDTO();
        buyServiceDTO.setEventId(eventId);
        buyServiceDTO.setServiceId(versionedServiceId);
        buyServiceDTO.setStartTime("00:00");
        buyServiceDTO.setEndTime("00:00");

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/service-budget-items/buy",
                new HttpEntity<>(buyServiceDTO),
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity2.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testBuyServiceButNonExistingEvent() {
        UUID nonExistingEventId = UUID.fromString("5c4eba0d-3f2c-4f3c-8bf2-a5fd725b85a3");
        UUID versionedServiceId = UUID.fromString("daa22294-5377-487a-aa3f-7cd5a42cc568");

        BuyServiceDTO buyServiceDTO = new BuyServiceDTO();
        buyServiceDTO.setEventId(nonExistingEventId);
        buyServiceDTO.setServiceId(versionedServiceId);
        buyServiceDTO.setStartTime("00:00");
        buyServiceDTO.setEndTime("00:00");

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/v1/service-budget-items/buy",
                new HttpEntity<>(buyServiceDTO),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    // tests that delete a planned productBudgetItem

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testDeletePlannedProductBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID productCategoryId = UUID.fromString("fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b");

        CreateProductBudgetItemDTO hasCategoryPath = new CreateProductBudgetItemDTO(
                eventId,
                productCategoryId,
                500.0
        );

        ResponseEntity<ProductBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/product-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO createdPBI = responseEntity1.getBody();
        assertNotNull(createdPBI);

        ResponseEntity<ProductBudgetItemDTO> responseEntity2 = restTemplate.getForEntity(
                "/api/v1/product-budget-items/" + createdPBI.getId().toString(),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ProductBudgetItemDTO retrievedPBI = responseEntity2.getBody();

        assertNotNull(retrievedPBI);
        assertEquals(createdPBI, retrievedPBI);

        ResponseEntity<Void> responseEntity3 = restTemplate.exchange(
                "/api/v1/product-budget-items/" + eventId + "/" + productCategoryId,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());

        ResponseEntity<String> responseEntity4 = restTemplate.getForEntity(
                "/api/v1/product-budget-items/" + createdPBI.getId().toString(),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity4.getStatusCode());
    }

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testDeletePlannedProductBudgetItemWithReservedProduct() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID versionedProductId = UUID.fromString("5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26");

        BuyProductDTO buyProductDTO = new BuyProductDTO();
        buyProductDTO.setEventId(eventId);
        buyProductDTO.setProductId(versionedProductId);

        ResponseEntity<ProductBudgetItemDTO> responseEntity2 = restTemplate.postForEntity("/api/v1/product-budget-items/buy",
                new HttpEntity<>(buyProductDTO),
                ProductBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());

        ProductBudgetItemDTO bpiWithProduct = responseEntity2.getBody();
        assertNotNull(bpiWithProduct);

        ResponseEntity<String> responseEntity3 = restTemplate.exchange(
                "/api/v1/product-budget-items/" + eventId + "/" + bpiWithProduct.getProductCategoryId().toString(),
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity3.getStatusCode());
    }

    // tests that delete a planned serviceBudgetItem

    @Test
    @Sql({"classpath:entity-insertion.sql"})
    void testDeletePlannedServiceBudgetItemSuccessful() {
        UUID eventId = UUID.fromString("86ad4f02-5c5c-42e6-b789-3181fa81e8f7");
        UUID serviceCategoryId = UUID.fromString("a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3");

        CreateServiceBudgetItemDTO hasCategoryPath = new CreateServiceBudgetItemDTO(
                eventId,
                serviceCategoryId,
                500.0
        );

        ResponseEntity<ServiceBudgetItemDTO> responseEntity1 = restTemplate.postForEntity("/api/v1/service-budget-items",
                new HttpEntity<>(hasCategoryPath),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ServiceBudgetItemDTO createdPBI = responseEntity1.getBody();
        assertNotNull(createdPBI);

        ResponseEntity<ServiceBudgetItemDTO> responseEntity2 = restTemplate.getForEntity(
                "/api/v1/service-budget-items/" + createdPBI.getId().toString(),
                ServiceBudgetItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());

        ServiceBudgetItemDTO retrievedPBI = responseEntity2.getBody();

        assertNotNull(retrievedPBI);
        assertEquals(createdPBI, retrievedPBI);

        ResponseEntity<Void> responseEntity3 = restTemplate.exchange(
                "/api/v1/service-budget-items/" + eventId + "/" + retrievedPBI.getServiceCategoryId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity3.getStatusCode());

        ResponseEntity<String> responseEntity4 = restTemplate.getForEntity(
                "/api/v1/service-budget-items/" + createdPBI.getId().toString(),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity4.getStatusCode());
    }

    // testing deletion of bought service requires service booking, which can't be mocked
    // in integration tests, so it's done in the service booking integration tests file
}
