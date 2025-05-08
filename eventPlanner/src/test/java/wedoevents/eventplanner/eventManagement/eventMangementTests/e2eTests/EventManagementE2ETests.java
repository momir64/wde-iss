package wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests;


import org.junit.jupiter.api.AfterEach;
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


        // Optionally verify that the next step was navigated correctly
        // (this can be checked via a URL change, page title, etc. depending on your app)
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


    @AfterEach
    public void tearDown() {
        //debugging
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //webDriver.quit();
    }
}
