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
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "name"), "Name should be invalid initially");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "description"), "Description should be invalid initially");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "location"), "Location should be invalid initially");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "startTime"), "Start time should be invalid initially");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "endTime"), "End time should be invalid initially");
        Assertions.assertFalse(table.isDeleteButtonEnabled(row), "Delete button should be disabled for the first row");
        Assertions.assertEquals("18:00", table.getStartTime(row), "Start time should be the same as the initial time");
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

        Assertions.assertFalse(table.hasInvalidTimeClass(row, "name"), "Name should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "description"), "Description should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "location"), "Location should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "startTime"), "Start time should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "endTime"), "End time should be valid after input");

        assertFalse(table.isNextButtonDisabled(), "Finish button should be enabled when all fields are valid");
    }

    @Test
    public void TestInvalidStartTimes() {
        goToAgenda();

        EventAgendaPage table = new EventAgendaPage(webDriver);
        int row = 1;


        table.setStartTime(row, "17:59");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "startTime"), "Start time should be invalid when it is before the event start time");

        table.setStartTime(row, "18:01");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "startTime"), "Start time should be invalid when it is before the event start time");

        table.setStartTime(row, "18:00");
        table.setEndTime(row, "19:00");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "startTime"), "Start time should be valid when it is equal to the event start time");

    }

    @Test
    public void TestInvalidEndTime() {
        goToAgenda();

        EventAgendaPage table = new EventAgendaPage(webDriver);
        int row = 1;

        table.setEndTime(row, "18:00");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "endTime"), "End time should be invalid when it is equal to the start time");

        table.setEndTime(row, "17:59");
        Assertions.assertTrue(table.hasInvalidTimeClass(row, "endTime"), "End time should be invalid when it is before the start time");

        table.setEndTime(row, "18:01");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "endTime"), "End time should be valid when it is after the start time");
    }

    @Test
    public void TestAgendaActivityOrder() {
        goToAgenda();

        EventAgendaPage table = new EventAgendaPage(webDriver);
        int row1 = 1;

        // Fill first row with valid data
        table.setName(row1, "Activity A");
        table.setDescription(row1, "First activity");
        table.setLocation(row1, "Hall A");
        table.setEndTime(row1, "18:30");

        // Add second row
        table.clickAddAgendaRowButton();
        int row2 = 2;

        table.setName(row2, "Activity B");
        table.setDescription(row2, "Second activity");
        table.setLocation(row2, "Hall B");
        table.setEndTime(row2, "19:00");

        // Check initial link
        Assertions.assertEquals(table.getEndTime(row1), table.getStartTime(row2), "Activities should be linked by time");

        // Change row2 start time
        table.setStartTime(row2, "18:31");
        Assertions.assertEquals("18:31", table.getEndTime(row1), "Row 1 end time should change when row 2 start time is changed");

        // Change row1 end time
        table.setEndTime(row1, "18:29");
        Assertions.assertEquals("18:29", table.getStartTime(row2), "Row 2 start time should change when row 1 end time is changed");
    }
    @Test
    public void TestAgendaRowDeletion() {
        goToAgenda();

        EventAgendaPage table = new EventAgendaPage(webDriver);
        int row1 = 1;
        table.setName(row1, "Activity A");
        table.setDescription(row1, "First activity");
        table.setLocation(row1, "Hall A");
        table.setEndTime(row1, "18:30");

        // Add and fill second row
        table.clickAddAgendaRowButton();
        int row2 = 2;

        table.setName(row2, "Activity B");
        table.setDescription(row2, "Second activity");
        table.setLocation(row2, "Hall B");
        table.setEndTime(row2, "19:00");

        Assertions.assertEquals(3, table.getRowCount(), "Should have two rows after adding");
        Assertions.assertEquals(table.getEndTime(row1), table.getStartTime(row2), "Activities should be linked by time");
        Assertions.assertEquals("18:30", table.getStartTime(2), "Row 2 start time should match Row 1 end time");

        // Ensure first row is deletable
        Assertions.assertTrue(table.isDeleteButtonEnabled(row1), "Delete button should be enabled for the first row");
        table.clickDeleteButton(row1);

        // Now check remaining
        Assertions.assertEquals(2, table.getRowCount(), "Should have one row after deletion");
        Assertions.assertFalse(table.isDeleteButtonEnabled(1), "Delete button should be disabled for the remaining row");
        Assertions.assertEquals("18:00", table.getStartTime(1), "Start time of the remaining row should be reset to the event start time");
    }
    @Test
    public void TestFinishCreatingEvent() throws InterruptedException {
        goToAgenda();

        EventAgendaPage table = new EventAgendaPage(webDriver);
        int row = 1;

        table.setName(row, "Team Meeting");
        table.setDescription(row, "Sprint review");
        table.setLocation(row, "Room 1");
        table.setStartTime(row, "18:00");
        table.setEndTime(row, "19:00");

        Assertions.assertFalse(table.hasInvalidTimeClass(row, "name"), "Name should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "description"), "Description should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "location"), "Location should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "startTime"), "Start time should be valid after input");
        Assertions.assertFalse(table.hasInvalidTimeClass(row, "endTime"), "End time should be valid after input");

        assertFalse(table.isNextButtonDisabled(), "Finish button should be enabled when all fields are valid");
        table.submitForm();

        EventPage eventPage = new EventPage(webDriver);


        String expectedTitle = "My Event";
        String expectedDescription = "A special event";
        String expectedTime = "18:00";
        String expectedDate = "25.12.2025.";
        String expectedLocation = "Novi Sad";

        assertEquals(expectedTitle, eventPage.getEventTitle(), "Title mismatch");
        assertEquals(expectedDescription, eventPage.getEventDescription(), "Description mismatch");
        assertEquals(expectedTime, eventPage.getEventTime(), "Time mismatch");
        assertEquals(expectedDate, eventPage.getEventDate(), "Date mismatch");
        assertEquals(expectedLocation, eventPage.getEventLocation(), "Location mismatch");

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


    @AfterEach
    public void tearDown() {
        //debugging
/*        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        webDriver.quit();
    }
}
