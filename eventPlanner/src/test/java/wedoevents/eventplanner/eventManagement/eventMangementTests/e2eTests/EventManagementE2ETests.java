package wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs.*;
import wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.testData.EventTestData;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventManagementE2ETests {
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
    public void testHomePage() {
        String pageTitle = webDriver.getTitle();
        System.out.println("Current Page Title: " + pageTitle); // Print check
        assertEquals("WeDoEvents", pageTitle);
    }
    @Test
    public void testEventNameInput() {
        EventBaseInfoPage eventCreationPage = new EventBaseInfoPage(webDriver);

        eventCreationPage.enterEventName("My event");

        assertTrue(eventCreationPage.isEventNameValid(), "Event name should be valid but is not");
    }

    @Test
    public void testValidFormSubmission() {

        EventTestData validEventData = new EventTestData(
                "My Event", 50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(validEventData);


        assertFalse(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be enabled");

        eventBaseInfoPage.submitForm();


        StepperPOM stepperPOM = new StepperPOM(webDriver);

        assertTrue(stepperPOM.isOnSecondStep(), "The second step should be active.");
    }
    @Test
    public void testEmptyNameField() {
        EventTestData invalidEventData = new EventTestData(
                "", // Empty name
                50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when name is empty");

        assertFalse(eventBaseInfoPage.isEventNameValid(), "Name field should show an error when it is empty");

    }
    @Test
    public void testNameStartsWithLowercaseLetter() {
        EventTestData invalidEventData = new EventTestData(
                "my Event", // Name starts with lowercase letter
                50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when name starts with lowercase letter");

        assertFalse(eventBaseInfoPage.isEventNameValid(), "Name field should show an error when it starts with a lowercase letter");
    }
    @Test
    public void testEmptyAddressField() {
        EventTestData invalidEventData = new EventTestData(
                "My Event", 50, "Novi Sad", "",
                "2025-12-25", "18:00", "Corporate Event", "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when address is empty");

        assertFalse(eventBaseInfoPage.isAddressValid(), "Address field should show an error when it is empty");
    }
    @Test
    public void testUnselectedCityField() {
        EventTestData invalidEventData = new EventTestData(
                "My Event", 50, "", "Ntp FTN", // City is empty
                "2025-12-25", "18:00", "Corporate Event", "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when city is not selected");
    }
    @Test
    public void testUnselectedEventType() {

        EventTestData invalidEventData = new EventTestData(
                "My Event", 50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "", // Event type is empty
                "A special event", true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when event type is not selected");
    }
    @Test
    public void testUnsetTime() {
        EventTestData invalidEventData = new EventTestData(
                "My Event", 50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "", // Time is left empty
                "Corporate Event", "A special event", true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when time is not set");

    }
    @Test
    public void testGuestCountZero() {
        EventTestData invalidEventData = new EventTestData(
                "My Event", 0, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event", true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when guest count is 0");

        assertFalse(eventBaseInfoPage.isGuestCountValid(), "Guest count field should show an error when it is 0");
    }
    @Test
    public void testGuestCountNegative() {
        EventTestData invalidEventData = new EventTestData(
                "My Event", -1, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event", true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(invalidEventData);

        assertTrue(eventBaseInfoPage.isNextButtonDisabled(), "Next button should be disabled when guest count is negative");

        assertFalse(eventBaseInfoPage.isGuestCountValid(), "Guest count field should show an error when it is negative");
    }
    @Test
    public void testGuestsStepVisibility() {
        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);
        StepperPOM stepperPOM = new StepperPOM(webDriver);



        assertTrue(stepperPOM.isGuestsStepVisible(), "Guests step should be visible when isPublic is true");

        eventBaseInfoPage.toggleIsPublic(true);
        assertFalse(stepperPOM.isGuestsStepVisible(), "Guests step should not be visible when isPublic is false");

        eventBaseInfoPage.toggleIsPublic(true);
        assertTrue(stepperPOM.isGuestsStepVisible(), "Guests step should be visible again when isPublic is true");
    }
    @Test
    public void TestValidCategoriesSuggestion() {

        EventTestData validEventData = new EventTestData(
                "My Event", 50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(validEventData);

        eventBaseInfoPage.submitForm();

        StepperPOM stepperPOM = new StepperPOM(webDriver);

        assertTrue(stepperPOM.isOnSecondStep(), "The second step should be active.");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickRecommendedCategoriesButton();

        EventRecommendedCategoriesModal recommendedCategoriesModal = new EventRecommendedCategoriesModal(webDriver);
        assertTrue(recommendedCategoriesModal.areProductCategoriesPresent(
                recommendedCategoriesModal.productCategories
        ), "Product categories should be present in the modal");

        assertTrue(recommendedCategoriesModal.areServiceCategoriesPresent(recommendedCategoriesModal.serviceCategories), "Service categories should be present in the modal");
    }

    @Test
    public void TestInvalidAgendaRow() {
        goToAgenda();

        EventAgendaPage table = new EventAgendaPage(webDriver);
        Assertions.assertEquals(2, table.getRowCount(), "Should start with one row");

        int row = 1;
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "name"));
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "description"));
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "location"));
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "startTime"));
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "endTime"));
        Assertions.assertFalse(table.isDeleteButtonEnabled(row));
        Assertions.assertEquals("18:00", table.getStartTime(row));
    }

    @Test
    public void TestAllValidAgendaRow() {
        goToAgenda();

        EventAgendaPage table = new EventAgendaPage(webDriver);
        int row = 1;

        table.setName(row, "Team Meeting");
        table.setDescription(row, "Sprint review");
        table.setLocation(row, "Room 1");
        table.setStartTime(row, "18:00");
        table.setEndTime(row, "19:00");

        Assertions.assertFalse(table.hasInvalidTimeClass(row, "name"));
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "description"));
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "location"));
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "startTime"));
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "endTime"));

        assertFalse(table.isNextButtonDisabled(), "Finish button should be enabled when all fields are valid");
    }


    private void goToAgenda(){
        EventTestData validEventData = new EventTestData(
                "My Event", 50, "Novi Sad", "Ntp FTN",
                "2025-12-25", "18:00", "Corporate Event", "A special event",
                true
        );

        EventBaseInfoPage eventBaseInfoPage = new EventBaseInfoPage(webDriver);

        eventBaseInfoPage.fillEventForm(validEventData);

        eventBaseInfoPage.submitForm();

        StepperPOM stepperPOM = new StepperPOM(webDriver);

        assertTrue(stepperPOM.isOnSecondStep(), "The second step should be active.");

        EventBudgetPage eventBudgetPage = new EventBudgetPage(webDriver);
        eventBudgetPage.clickAddCategoryButton();

        EventBudgetRow eventBudgetRow = new EventBudgetRow(webDriver);
        eventBudgetRow.setBudget("1000");
        eventBudgetRow.selectFirstTypeOption();
        eventBudgetRow.selectFirstCategoryOption();

        eventBudgetRow.submitForm();
    }
    // before each: add category, choose type, category, insert budget, click next

    // 1 make sure you cant finish and cant delete first row, then make sure everything is red and start time is the same
    // 2 insert valid and check if you can finish
    // 3 insert valid and change start time, check it is red and cant finish
    // 4 check if empty row is red for each input and insert to change to okay
    // 5 add new row and check new start time is like previous end, change new row end time and check for the first row that end time changed
    // 6 check that you can delete the first row, delete it, check you cant delete the fierst row anymore, check its start time
    // 7 finish and go to my events, search it and its over

    @AfterEach
    public void tearDown() {
        //debugging
//        try {
//            Thread.sleep(100000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //webDriver.quit();
    }
}
