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

public class MyEventsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".create-button button")
    private WebElement createNewEventButton;

    public MyEventsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickCreateNewEvent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.elementToBeClickable(createNewEventButton));
        createNewEventButton.click();
    }

    public void navigateToEvent(String eventName) {
        WebElement searchInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Search items...']"))
        );

        searchInput.sendKeys(eventName + Keys.ENTER);

        List<WebElement> listingCards = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("app-event-card"))
        );

        if (!listingCards.isEmpty()) {
            WebElement firstCard = listingCards.get(0);

            WebElement link = wait.until(
                    ExpectedConditions.elementToBeClickable(firstCard.findElement(By.tagName("a")))
            );

            link.click();
        }
    }
}
