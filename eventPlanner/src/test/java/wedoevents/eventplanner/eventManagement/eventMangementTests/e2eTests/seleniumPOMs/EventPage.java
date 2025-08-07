package wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EventPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public EventPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By title = By.cssSelector(".title-section .title");
    private final By description = By.cssSelector(".description .description-content");
    private final By time = By.xpath("//mat-icon[text()='access_time_filled']/following-sibling::div");
    private final By date = By.xpath("//mat-icon[text()='calendar_month']/following-sibling::div");
    private final By location = By.xpath("//mat-icon[text()='location_pin']/preceding-sibling::div");

    public String getEventTitle() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(title)).getText().trim();
    }

    public String getEventDescription() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(description)).getText().trim();
    }

    public String getEventTime() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(time)).getText().trim();
    }

    public String getEventDate() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(date)).getText().trim();
    }

    public String getEventLocation() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(location)).getText().trim();
    }
}
