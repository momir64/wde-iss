package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs.*;
import wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.testData.EventTestData;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetPlanningE2ETests {
    @LocalServerPort
    private int port;

    @Autowired
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver.manage().window().maximize();

        String url = "http://localhost:4200/login";
        webDriver.get(url);

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login("jane.smith@example.com", "123");

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToMyEvents();

        MyEventsPage myEventsPage = new MyEventsPage(webDriver);

        myEventsPage.clickCreateNewEvent();
    }

    @Test
    public void testNoTwoSameCategoriesAllowed() throws InterruptedException {
        goToBudgetStep();

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
    public void testNextButtonDisabledIfBudgetValueMissing() throws InterruptedException {
        goToBudgetStep();

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
    public void testCreateEventWithProductAndServiceBudgetItems() throws InterruptedException {
        goToBudgetStep();

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
        assertTrue(eventEditPage.containsProductBudgetItem(ListingType.PRODUCT, "Fireworks", 1000));
        assertTrue(eventEditPage.containsProductBudgetItem(ListingType.SERVICE, "Music", 1500));
    }

    @Test
    public void testDeleteBudgetItemWithNoAssociatedListing() throws InterruptedException {
        goToBudgetStep();

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

        assertFalse(eventEditPage.containsProductBudgetItem(ListingType.PRODUCT, "Fireworks", 1000));
    }

    @Test
    public void testChangeBudgetItemPriceWithNoAssociatedListing() throws InterruptedException {
        goToBudgetStep();

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

        assertTrue(eventEditPage.containsProductBudgetItem(ListingType.PRODUCT, "Fireworks", 2000));
    }

    private void goToBudgetStep() {
        EventTestData validEventData = new EventTestData(
                "My Event", 50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event",
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
}
