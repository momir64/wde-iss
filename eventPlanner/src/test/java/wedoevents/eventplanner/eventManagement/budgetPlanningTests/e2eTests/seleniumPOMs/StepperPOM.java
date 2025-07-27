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

    public void clickNext() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@matStepperNext]")));
        nextButton.click();
    }

    public boolean isNextButtonEnabled() {
        WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@matStepperNext]")));
        return nextButton.isEnabled();
    }
    public boolean isOnSecondStep() {
        try {
            // Wait for the second step header to be selected and active
            WebElement secondStep = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("mat-step-header[ng-reflect-index='1'][ng-reflect-selected='true']")));
            return secondStep != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isOnThirdStep() {
        try {
            // Wait for the second step header to be selected and active
            WebElement thirdStep = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("mat-step-header[ng-reflect-index='2'][ng-reflect-selected='true']")));
            return thirdStep != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isGuestsStepVisible() {
        try {
            WebElement guestsStep = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-step-header//div[contains(text(), 'Guests')]")));
            return guestsStep.isDisplayed();
        } catch (TimeoutException e) {
            return false; // If the step is not found, it's not visible
        }
    }
}