package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class ReserveServicePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ReserveServicePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void reserveForEvent(String eventName) {
        WebElement reserveButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Reserve']"))
        );
        reserveButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Select event and time']")));

        WebElement eventDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='Event']")));
        eventDropdown.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        List<WebElement> eventOptions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//mat-option/span"))
        );

        boolean found = false;
        for (WebElement option : eventOptions) {
            if (option.getText().trim().equalsIgnoreCase(eventName.trim())) {
                option.click();
                found = true;
                break;
            }
        }

        if (!found) {
            throw new NoSuchElementException("Event with name '" + eventName + "' not found in dropdown.");
        }

        WebElement startTimeDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='Start time' and not(@aria-disabled='true')]"))
        );

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        startTimeDropdown.click();

        List<WebElement> startTimeOptions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//mat-option/span"))
        );

        if (!startTimeOptions.isEmpty()) {
            startTimeOptions.get(0).click();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement endTimeDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='End time' and not(@aria-disabled='true')]"))
        );
        endTimeDropdown.click();

        List<WebElement> endTimeOptions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//mat-option/span"))
        );

        if (!endTimeOptions.isEmpty()) {
            endTimeOptions.get(0).click();
        }

         WebElement finalReserveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.purple-button.finish:not([disabled])")));
         finalReserveButton.click();
    }

    public boolean reservableForEvent(String eventName) {
        WebElement reserveButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Reserve']"))
        );
        reserveButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Select event and time']")));

        WebElement eventDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='Event']")));
        eventDropdown.click();

        List<WebElement> eventOptions;

        try {
            eventOptions = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//mat-option/span"))
            );
        } catch (TimeoutException e) {
            return false;
        }

        for (WebElement option : eventOptions) {
            if (option.getText().trim().equalsIgnoreCase(eventName.trim())) {
                option.click();
                return true;
            }
        }

        return false;
    }
}
