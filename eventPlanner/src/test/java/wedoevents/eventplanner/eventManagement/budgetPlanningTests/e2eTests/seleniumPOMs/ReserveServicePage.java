package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class ReserveServicePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ReserveServicePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void reserveForEvent(String eventName, String startTime, String endTime) {
        clickReserveButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[text()='Select event and time']")));

        selectDropdownOption("//mat-select[@placeholder='Event']", eventName);

        selectDropdownOption("//mat-select[@placeholder='Start time' and not(@aria-disabled='true')]", startTime);
        selectDropdownOption("//mat-select[@placeholder='End time' and not(@aria-disabled='true')]", endTime);

        WebElement finalReserveButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.purple-button.finish:not([disabled])"))
        );
        finalReserveButton.click();
    }

    public boolean reservableForEvent(String eventName) {
        try {
            clickReserveButton();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[text()='Select event and time']")));

            return selectDropdownOption("//mat-select[@placeholder='Event']", eventName);
        } catch (TimeoutException | NoSuchElementException ignored) {
            return false;
        }
    }

    private void clickReserveButton() {
        WebElement reserveButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Reserve']"))
        );
        reserveButton.click();
    }

    private boolean selectDropdownOption(String dropdownXPath, String optionText) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXPath)));
        dropdown.click();

        By optionLocator = By.xpath("//mat-option//span[normalize-space(text())='" + optionText.trim() + "']");
        try {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            Actions actions = new Actions(driver);
            actions.moveToElement(option).click().perform();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}