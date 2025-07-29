package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MarketPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MarketPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToListing(String listingName) {
        WebElement searchInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='filter-bar']//input[@type='text']"))
        );

        searchInput.sendKeys(listingName);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        searchInput.sendKeys(Keys.ENTER);

        List<WebElement> listingCards = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("app-listing-card"))
        );

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        if (!listingCards.isEmpty()) {
            WebElement firstCard = listingCards.get(0);

            WebElement link = wait.until(
                    ExpectedConditions.elementToBeClickable(firstCard.findElement(By.tagName("a")))
            );

            link.click();
        }
    }
}
