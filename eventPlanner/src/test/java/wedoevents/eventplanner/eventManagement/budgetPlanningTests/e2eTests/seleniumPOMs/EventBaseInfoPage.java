package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.testData.EventTestData;

import java.time.Duration;
import java.util.List;

public class EventBaseInfoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public EventBaseInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    public void fillEventForm(EventTestData eventData) {
        enterEventName(eventData.name);
        enterGuestCount(eventData.guestCount);
        selectCity(eventData.city);
        enterAddress(eventData.address);
        enterDate(eventData.date);
        enterTime(eventData.time);
        selectEventType(eventData.eventType);
        enterDescription(eventData.description);
        toggleIsPublic(eventData.isPublic);
    }

    public void enterEventName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='name']")));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterGuestCount(int guestCount) {
        WebElement guestCountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='guestCount']")));
        guestCountInput.clear();
        guestCountInput.sendKeys(String.valueOf(guestCount));
    }

    public void selectCity(String city) {
        if(city.isEmpty()) return;
        WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("mat-select[formControlName='city']")));
        cityDropdown.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[contains(text(), '" + city + "')]")));
        cityOption.click();
    }

    public void enterAddress(String address) {
        WebElement addressInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='address']")));
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    public void enterDate(String date) {
        if(date.isEmpty()) return;
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='date']")));
        dateInput.sendKeys(Keys.CONTROL + "a"); // Clear existing value
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.RETURN);
    }

    public void enterTime(String time) {
        if(time.isEmpty()) return;
        WebElement timeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='time']")));
        timeInput.clear();
        timeInput.sendKeys(time);
    }

    public void selectEventType(String eventType) {
        if(eventType.isEmpty()) return;
        WebElement eventTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("mat-select[formControlName='eventTypeId']")));
        eventTypeDropdown.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement eventTypeOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + eventType + "')]")));
        eventTypeOption.click();
    }

    public void enterDescription(String description) {
        WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea[formControlName='description']")));
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    public void toggleIsPublic(boolean isPublic) {
        WebElement isPublicToggle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-slide-toggle[formControlName='isPublic']")));
        if (isPublic) {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            isPublicToggle.click();
        }
    }

    public void submitForm() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@matStepperNext]")));
        nextButton.click();
    }
}
