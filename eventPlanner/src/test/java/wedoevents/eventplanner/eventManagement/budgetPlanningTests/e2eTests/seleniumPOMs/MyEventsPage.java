package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='filter-bar']//input[@type='text']")));

        searchInput.clear();
        searchInput.sendKeys(eventName);

        WebElement searchButtonDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.mat-mdc-form-field-icon-suffix")));

        Actions actions = new Actions(driver);
        actions.moveToElement(searchButtonDiv).click().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'listing-title') and normalize-space(text())='" + eventName + "']")
        ));

        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("a")));
        card.click();
    }
}
