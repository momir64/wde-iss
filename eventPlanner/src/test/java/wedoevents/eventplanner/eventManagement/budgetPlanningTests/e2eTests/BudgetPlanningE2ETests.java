package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs.*;
import wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.testData.EventTestData;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetPlanningE2ETests {
    @LocalServerPort
    private int port;

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        webDriver.manage().window().maximize();

        String url = "http://localhost:4200/login";
        webDriver.get(url);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login("jane.smith@example.com", "123");

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);

        myEventsPage.clickCreateNewEvent();
    }

    @Test
    public void testNoTwoSameCategoriesAllowed() {
        goToBudgetStep("My event", "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow1 = new EventBudgetRow(webDriver);
        productBudgetRow1.selectProductTypeOption();
        productBudgetRow1.selectCustomCategoryOption("Fireworks");

        EventBudgetRow productBudgetRow2 = new EventBudgetRow(webDriver);
        productBudgetRow2.selectProductTypeOption();
        assertFalse(productBudgetRow2.isCustomCategoryEnabled("Fireworks"), "The selected category is already chosen");
    }

    @Test
    public void testNextButtonDisabledIfBudgetValueMissing() {
        goToBudgetStep("My event", "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Fireworks");
        productBudgetRow.setBudget("1000");

        EventBudgetRow serviceBudgetRow = new EventBudgetRow(webDriver);
        serviceBudgetRow.selectServiceTypeOption();
        serviceBudgetRow.selectCustomCategoryOption("Music");

        assertFalse(eventBudgetPage.nextButtonEnabled(), "The next button must be disabled");
    }

    @Test
    public void testCreateEventWithProductAndServiceBudgetItems() {
        goToBudgetStep("My event", "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Fireworks");
        productBudgetRow.setBudget("1000");

        EventBudgetRow serviceBudgetRow = new EventBudgetRow(webDriver);
        serviceBudgetRow.selectServiceTypeOption();
        serviceBudgetRow.selectCustomCategoryOption("Music");
        serviceBudgetRow.setBudget("1500");

        assertTrue(eventBudgetPage.nextButtonEnabled(), "The next button must be enabled");

        eventBudgetPage.submitForm();

        StepperPOM stepperPOM = new StepperPOM(webDriver);
        assertTrue(stepperPOM.isOnThirdStep(), "The third step should be active.");

        fillAgendaAndSubmit();

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();

        assertEquals(2, eventEditPage.numberOfBudgetItems());
        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.PRODUCT, "Fireworks", 1000));
        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.SERVICE, "Music", 1500));
    }

    @Test
    public void testCalculateTotalPriceOfBudgetItems() {
        goToBudgetStep("My event", "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Fireworks");
        productBudgetRow.setBudget("1000");

        EventBudgetRow serviceBudgetRow = new EventBudgetRow(webDriver);
        serviceBudgetRow.selectServiceTypeOption();
        serviceBudgetRow.selectCustomCategoryOption("Music");
        serviceBudgetRow.setBudget("1500");

        assertTrue(eventBudgetPage.getTotalPrice("2500"));
    }

    @Test
    public void testDeleteBudgetItemWithNoAssociatedListing() {
        goToBudgetStep("My event", "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Fireworks");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();
        assertTrue(eventEditPage.deleteBudgetItemIfDeletable(ListingType.PRODUCT, "Fireworks", 1000));
        eventEditPage.clickSave();

        eventEditPage.refreshPage();
        eventEditPage.navigateToBudget();

        assertFalse(eventEditPage.containsListingBudgetItem(ListingType.PRODUCT, "Fireworks", 1000));
    }

    @Test
    public void testChangeBudgetItemPriceWithNoAssociatedListing() {
        goToBudgetStep("My event", "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Fireworks");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();
        assertTrue(eventEditPage.changeBudgetItemPriceIfChangeable(ListingType.PRODUCT, "Fireworks", 1000, 2000));
        eventEditPage.clickSave();

        eventEditPage.refreshPage();
        eventEditPage.navigateToBudget();

        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.PRODUCT, "Fireworks", 2000));
    }

    @Test
    public void testCreateEventWithServiceBudgetItemsAndBuyService() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectServiceTypeOption();
        productBudgetRow.selectCustomCategoryOption("Music");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Classic Jazz Band");

        ReserveServicePage reserveServicePage = new ReserveServicePage(webDriver);
        reserveServicePage.reserveForEvent(eventName, "00:00", "01:15");

        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);
        myEventsPage.navigateToEvent(eventName);

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();

        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.SERVICE, "Music", 1000));
        assertTrue(eventEditPage.navigateToBoughtListingPageIfBought(ListingType.SERVICE, "Music", 1000));

        BoughtServicePage boughtServicePage = new BoughtServicePage(webDriver);

        assertEquals(890, boughtServicePage.getServicePrice());
        assertEquals("Classic Jazz Band", boughtServicePage.getServiceTitle());
        assertEquals("Music", boughtServicePage.getServiceCategory());
    }

    @Test
    public void testCreateEventWithProductBudgetItemsAndBuyProduct() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Drinks");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Champagne");

        BuyProductPage buyProductPage = new BuyProductPage(webDriver);
        buyProductPage.buyForEvent(eventName);

        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);
        myEventsPage.navigateToEvent(eventName);

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();

        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.PRODUCT, "Drinks", 1000));
        assertTrue(eventEditPage.navigateToBoughtListingPageIfBought(ListingType.PRODUCT, "Drinks", 1000));

        BoughtProductPage boughtProductPage = new BoughtProductPage(webDriver);

        assertEquals(930, boughtProductPage.getProductPrice());
        assertEquals("Champagne", boughtProductPage.getProductTitle());
        assertEquals("Drinks", boughtProductPage.getProductCategory());
    }

    @Test
    public void testCreateEventWithoutServiceBudgetItemsAndBuyService() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow serviceBudgetRow = new EventBudgetRow(webDriver);
        serviceBudgetRow.selectServiceTypeOption();
        serviceBudgetRow.selectCustomCategoryOption("Catering");
        serviceBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("House DJ");

        ReserveServicePage reserveServicePage = new ReserveServicePage(webDriver);
        reserveServicePage.reserveForEvent(eventName, "00:00", "01:15");

        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);
        myEventsPage.navigateToEvent(eventName);

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();

        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.SERVICE, "Music", 0));
        assertTrue(eventEditPage.navigateToBoughtListingPageIfBought(ListingType.SERVICE, "Music", 0));

        BoughtServicePage boughtServicePage = new BoughtServicePage(webDriver);

        assertEquals(169, boughtServicePage.getServicePrice());
        assertEquals("House DJ", boughtServicePage.getServiceTitle());
        assertEquals("Music", boughtServicePage.getServiceCategory());
    }

    @Test
    public void testCreateEventWithoutProductBudgetItemsAndBuyProduct() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Food");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Champagne");

        BuyProductPage buyProductPage = new BuyProductPage(webDriver);
        buyProductPage.buyForEvent(eventName);

        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);
        myEventsPage.navigateToEvent(eventName);

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();

        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.PRODUCT, "Drinks", 0));
        assertTrue(eventEditPage.navigateToBoughtListingPageIfBought(ListingType.PRODUCT, "Drinks", 0));

        BoughtProductPage boughtProductPage = new BoughtProductPage(webDriver);

        assertEquals(930, boughtProductPage.getProductPrice());
        assertEquals("Champagne", boughtProductPage.getProductTitle());
        assertEquals("Drinks", boughtProductPage.getProductCategory());
    }

    @Test
    public void testCantBuyProductForEventThatAlreadyHasBoughtProductWithSameCategory() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Drinks");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Champagne");

        BuyProductPage buyProductPage = new BuyProductPage(webDriver);
        buyProductPage.buyForEvent(eventName);

        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);
        myEventsPage.navigateToEvent(eventName);

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();

        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.PRODUCT, "Drinks", 1000));
        assertTrue(eventEditPage.navigateToBoughtListingPageIfBought(ListingType.PRODUCT, "Drinks", 1000));

        BoughtProductPage boughtProductPage = new BoughtProductPage(webDriver);

        assertEquals(930, boughtProductPage.getProductPrice());
        assertEquals("Champagne", boughtProductPage.getProductTitle());
        assertEquals("Drinks", boughtProductPage.getProductCategory());

        sidebarPage.navigateToMarket();
        marketPage.navigateToListing("Champagne");

        assertFalse(buyProductPage.buyableForEvent(eventName));
    }

    @Test
    public void testCantBuyServiceForEventThatAlreadyHasBoughtServiceWithSameCategory() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectServiceTypeOption();
        productBudgetRow.selectCustomCategoryOption("Catering");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Cover Band");

        ReserveServicePage reserveServicePage = new ReserveServicePage(webDriver);
        reserveServicePage.reserveForEvent(eventName, "00:00", "01:15");

        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);
        myEventsPage.navigateToEvent(eventName);

        EventEditPage eventEditPage = new EventEditPage(webDriver);
        eventEditPage.navigateToActualEditPage();
        eventEditPage.navigateToBudget();

        assertTrue(eventEditPage.containsListingBudgetItem(ListingType.SERVICE, "Music", 0));
        assertTrue(eventEditPage.navigateToBoughtListingPageIfBought(ListingType.SERVICE, "Music", 0));

        BoughtServicePage boughtServicePage = new BoughtServicePage(webDriver);

        assertEquals(124, boughtServicePage.getServicePrice());
        assertEquals("Cover Band", boughtServicePage.getServiceTitle());
        assertEquals("Music", boughtServicePage.getServiceCategory());

        sidebarPage.navigateToMarket();
        marketPage.navigateToListing("Cover Band");

        assertFalse(reserveServicePage.reservableForEvent(eventName));
    }

    @Test
    public void testCantBuyProductForEventThatHasNoTypeSupported() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Outdoor Festival");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Food");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Chocolate Fountain");

        BuyProductPage buyProductPage = new BuyProductPage(webDriver);
        assertFalse(buyProductPage.buyableForEvent(eventName));
    }

    @Test
    public void testCantBuyServiceForEventThatHasNoTypeSupported() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Outdoor Festival");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectServiceTypeOption();
        productBudgetRow.selectCustomCategoryOption("Music");
        productBudgetRow.setBudget("1000");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Solo Violinist");

        ReserveServicePage reserveServicePage = new ReserveServicePage(webDriver);
        assertFalse(reserveServicePage.reservableForEvent(eventName));
    }

    @Test
    public void testCantBuyProductIfThePriceIsTooHigh() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectProductTypeOption();
        productBudgetRow.selectCustomCategoryOption("Drinks");
        productBudgetRow.setBudget("1");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Champagne");

        BuyProductPage buyProductPage = new BuyProductPage(webDriver);
        assertFalse(buyProductPage.buyableForEvent(eventName));
    }

    @Test
    public void testCantReserveServiceIfThePriceIsTooHigh() {
        String eventName = generateRandomString();

        goToBudgetStep(eventName, "Corporate Event");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow productBudgetRow = new EventBudgetRow(webDriver);
        productBudgetRow.selectServiceTypeOption();
        productBudgetRow.selectCustomCategoryOption("Music");
        productBudgetRow.setBudget("1");

        eventBudgetPage.submitForm();

        fillAgendaAndSubmit();

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMarket();

        MarketPage marketPage = new MarketPage(webDriver);
        marketPage.navigateToListing("Classic Jazz Band");

        ReserveServicePage reserveServicePage = new ReserveServicePage(webDriver);
        assertFalse(reserveServicePage.reservableForEvent(eventName));
    }

    private void goToBudgetStep(String eventName, String eventType) {
        EventTestData validEventData = new EventTestData(
                eventName, 50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", eventType, "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(validEventData);

        eventBaseInfoPage.submitForm();
    }

    private void fillAgendaAndSubmit() {
        EventAgendaPage table = new EventAgendaPage(webDriver);
        int row = 1;

        table.setName(row, "Team Meeting");
        table.setDescription(row, "Sprint review");
        table.setLocation(row, "Room 1");
        table.setStartTime(row, "18:00");
        table.setEndTime(row, "19:00");

        table.submitForm();
    }

    private String generateRandomString() {
        SecureRandom random = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(25);

        for (int i = 0; i < 25; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    @AfterEach
    public void tearDown() {
        //debugging

//        try {
//            Thread.sleep(100000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        webDriver.quit();
    }

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void runSqlScriptAfterAllTests() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            List<String> tables = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT tablename FROM pg_tables WHERE tableowner = 'admin' AND schemaname = 'public'")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        tables.add(rs.getString("tablename"));
                    }
                }
            }

            if (tables.isEmpty()) {
                return;
            }

            StringBuilder sql = new StringBuilder("TRUNCATE TABLE ");
            for (int i = 0; i < tables.size(); i++) {
                sql.append("\"").append(tables.get(i)).append("\"");
                if (i < tables.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(" CASCADE;");

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql.toString());
            }

            ScriptUtils.executeSqlScript(conn, new ClassPathResource("entity-insertion.sql"));
        }
    }
}
