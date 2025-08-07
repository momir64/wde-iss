package wedoevents.eventplanner.eventManagement.eventFilterTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AllEventsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public AllEventsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        PageFactory.initElements(driver, this);
    }

    public void openFilterDialog() {
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'filter') and contains(text(), 'Filter')]")));
        filterButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[normalize-space()='Filters']")));
    }

    public void selectType(String typeName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='Type']")));
        dropdown.click();

        do {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[normalize-space()='" + typeName + "']")));
            Actions actions = new Actions(driver);
            actions.moveToElement(option).click().perform();

            dropdown = driver.findElement(By.xpath("//mat-select[@placeholder='Type']"));
        } while (!dropdown.getText().trim().equals(typeName));
    }

    public void setDateRange(String afterDate, String beforeDate) {
        WebElement afterInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='After']")));
        afterInput.clear();
        afterInput.sendKeys(afterDate);
        afterInput.sendKeys(Keys.ENTER);

        WebElement beforeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Before']")));
        beforeInput.clear();
        beforeInput.sendKeys(beforeDate);
        beforeInput.sendKeys(Keys.ENTER);
    }

    public void setRatingRange(double minRating, double maxRating) {
        WebElement minRatingInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Min. rating']")));
        minRatingInput.clear();
        minRatingInput.sendKeys(String.valueOf(minRating));

        WebElement maxRatingInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Max. rating']")));
        maxRatingInput.clear();
        maxRatingInput.sendKeys(String.valueOf(maxRating));
    }

    public void selectCity(String city) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='City']")));

        do {
            dropdown.click();

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[contains(text(),'" + city + "')]")));
            Actions actions = new Actions(driver);
            actions.moveToElement(option).click().perform();

            dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@placeholder='City']")));
        } while (!dropdown.getText().trim().equals(city));
    }

    public void clickFilterButton() {
        WebElement filterBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'purple-button') and contains(text(),'Filter')]")));
        filterBtn.click();
    }

    public List<String> getAllEventNames() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading mat-spinner")));

        List<WebElement> eventTitleElements;
        try {
            eventTitleElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("p.listing-title")));
        } catch (TimeoutException e) {
            return new ArrayList<>();
        }

        List<String> eventNames = new ArrayList<>();

        for (WebElement element : eventTitleElements)
            eventNames.add(element.getText().trim());

        return eventNames;
    }

    public List<String> getAllEventsCities() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading mat-spinner")));

        List<WebElement> cityElements;
        try {
            cityElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@class, 'item')]/p[contains(@class, 'item-text')][following-sibling::mat-icon[@fonticon='location_on']]")));
        } catch (TimeoutException e) {
            return new ArrayList<>();
        }

        List<String> eventCities = new ArrayList<>();

        for (WebElement element : cityElements)
            eventCities.add(element.getText().trim());

        return eventCities;
    }

    public List<String> getAllEventsDates() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading mat-spinner")));

        List<WebElement> dateElements;
        try {
            dateElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@class, 'item')]/p[contains(@class, 'item-text')][following-sibling::mat-icon[@fonticon='date_range']]")));
        } catch (TimeoutException e) {
            return new ArrayList<>();
        }

        List<String> eventDates = new ArrayList<>();

        for (WebElement element : dateElements)
            eventDates.add(element.getText().trim());

        return eventDates;
    }

    public List<String> getAllEventsRatings() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading mat-spinner")));

        List<WebElement> ratingElements;
        try {
            ratingElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@class, 'item')]/p[contains(@class, 'item-text')][following-sibling::mat-icon[@fonticon='star']]")));
        } catch (TimeoutException e) {
            return new ArrayList<>();
        }

        List<String> ratings = new ArrayList<>();

        for (WebElement el : ratingElements)
            ratings.add(el.getText().trim());

        return ratings;
    }

    public void searchEvents(String searchQuery) {
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Search items...']")));

        searchInput.clear();
        searchInput.sendKeys(searchQuery);

        for (int i = 0; i < 5; i++)
            searchInput.sendKeys(Keys.ENTER);
    }
}
