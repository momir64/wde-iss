package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class BuyProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public BuyProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void buyForEvent(String eventName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement buyButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Buy']"))
        );
        buyButton.click();

        WebElement eventDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='Event']")));
        eventDropdown.click();

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

         WebElement finalBuyButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.purple-button.finish:not([disabled])")));
         finalBuyButton.click();
    }

    public boolean buyableForEvent(String eventName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement buyButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Buy']"))
        );
        buyButton.click();

        WebElement eventDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='Event']")));
        eventDropdown.click();

        List<WebElement> eventOptions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//mat-option/span"))
        );

        for (WebElement option : eventOptions) {
            if (option.getText().trim().equalsIgnoreCase(eventName.trim())) {
                option.click();
                return true;
            }
        }

        return false;
    }
}
