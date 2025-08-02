package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StepperPOM {
    private WebDriver driver;
    private WebDriverWait wait;

    public StepperPOM(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    public boolean isOnThirdStep() {
        try {
            WebElement thirdStep = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("mat-step-header[ng-reflect-index='2'][ng-reflect-selected='true']")));
            return thirdStep != null;
        } catch (TimeoutException e) {
            return false;
        }
    }
}