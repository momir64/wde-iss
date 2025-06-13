package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs.LoginPage;
import wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs.MyEventsPage;
import wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs.SidebarPage;

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
}
