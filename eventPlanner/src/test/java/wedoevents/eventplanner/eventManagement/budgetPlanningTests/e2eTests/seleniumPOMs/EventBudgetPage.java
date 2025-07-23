package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventBudgetPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public EventBudgetPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    public void clickAddCategoryButton() {
        WebElement addCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'add-category')])[1]//button")
        ));
        addCategoryButton.click();
    }

    public void submitForm() {
        By nextButtonLocator = By.id("button-next-budget");

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator));
        nextButton.click();
    }

    public boolean nextButtonEnabled() {
        By nextButtonLocator = By.id("button-next-budget");

        WebElement nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(nextButtonLocator));
        return nextButton.isEnabled();
    }

    public int getTotalPrice() {
        WebElement totalAmountElement = driver.findElement(By.cssSelector("p.total-amount"));
        String totalText = totalAmountElement.getText();

        return Integer.parseInt(totalText.replaceAll("[^0-9]", ""));
    }
}
