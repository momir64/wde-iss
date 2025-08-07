package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MarketPage {
    private final WebDriverWait wait;
    private final WebDriver webDriver;

    public MarketPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.webDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToListing(String listingName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='filter-bar']//input[@type='text']")));

        searchInput.clear();
        searchInput.sendKeys(listingName);

        clickNotStaleSearchElement();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'listing-title') and normalize-space(text())='" + listingName + "']")
        ));

        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("a")));
        card.click();
    }

    public void clickNotStaleSearchElement() {
        while (true) {
            try {
                WebElement searchButtonDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.mat-mdc-form-field-icon-suffix")));

                for (int i = 0; i < 3; i++) {
                    Actions actions = new Actions(webDriver);
                    actions.moveToElement(searchButtonDiv).click().perform();
                }

                return;
            } catch (StaleElementReferenceException ignored) { }
        }
    }
}