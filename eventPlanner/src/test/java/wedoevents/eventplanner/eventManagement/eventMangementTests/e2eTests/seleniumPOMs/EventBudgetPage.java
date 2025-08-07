package wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EventBudgetPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public EventBudgetPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }
    public void clickRecommendedCategoriesButton() {
        WebElement recommendedCategoriesButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'add-category')])[2]//button")
        ));
        recommendedCategoriesButton.click();
    }
    public void clickAddCategoryButton() {
        WebElement recommendedCategoriesButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'add-category')])[1]//button")
        ));
        recommendedCategoriesButton.click();
    }
}
