package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EventBudgetPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public EventBudgetPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    public void clickAddCategoryButton() {
        WebElement addCategoryButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//div[contains(@class, 'add-category')])[1]//button")
                )
        );
        addCategoryButton.click();
    }

    public void submitForm() {
        By nextButtonLocator = By.id("button-next-budget");

        WebElement nextButton = wait.until(
                ExpectedConditions.elementToBeClickable(nextButtonLocator)
        );
        nextButton.click();
    }

    public boolean nextButtonEnabled() {
        By nextButtonLocator = By.id("button-next-budget");

        WebElement nextButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(nextButtonLocator)
        );
        return nextButton.isEnabled();
    }

    public boolean getTotalPrice(String totalPrice) {
        By totalAmountLocator = By.cssSelector("p.total-amount");

        WebElement totalAmountElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(totalAmountLocator)
        );

        try {
            wait.until(driver -> !totalAmountElement.getText().trim().equals(totalPrice));
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
}