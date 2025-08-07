package wedoevents.eventplanner.eventManagement.eventFilterTests.seleniumPOMs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SidebarPage {
    private final WebDriver driver;

    @FindBy(xpath = "//p[contains(text(),'Events')]")
    private WebElement allEventsLink;

    public SidebarPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToAllEvents() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(allEventsLink));

        while (!driver.getCurrentUrl().equals("http://localhost:4200/events")) {
            wait.until(ExpectedConditions.elementToBeClickable(allEventsLink));
            allEventsLink.click();
        }
    }
}
