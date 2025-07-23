package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BoughtProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public BoughtProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By title = By.cssSelector("div.service-and-company-name > p.service-text");
    private final By price = By.cssSelector("div.current-price-and-rating > p.current-price");
    private final By category = By.xpath("(//div[contains(@class, 'description')])//p[4]");

    public String getProductTitle() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(title)).getText().trim();
    }

    public int getProductPrice() {
        String text = wait.until(ExpectedConditions.presenceOfElementLocated(price)).getText().trim();
        return (int) Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public String getProductCategory() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(category)).getText().trim();
    }
}
