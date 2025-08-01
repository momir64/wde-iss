package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.testData.EventTestData;

import java.time.Duration;

public class EventBaseInfoPage {
    private final WebDriverWait wait;
    private final WebDriver webDriver;

    public EventBaseInfoPage(WebDriver driver) {
        this.webDriver = driver;
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
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='name']")));
        input.clear();
        input.sendKeys(name);
    }

    public void enterGuestCount(int guestCount) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='guestCount']")));
        input.clear();
        input.sendKeys(String.valueOf(guestCount));
    }

    public void selectCity(String city) {
        if (city.isEmpty()) return;
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("mat-select[formControlName='city']")));
        dropdown.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".cdk-overlay-backdrop")));
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[contains(text(),'" + city + "')]")));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(option).click().perform();
    }

    public void enterAddress(String address) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='address']")));
        input.clear();
        input.sendKeys(address);
    }

    public void enterDate(String date) {
        if (date.isEmpty()) return;
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='date']")));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(date);
        input.sendKeys(Keys.RETURN);
    }

    public void enterTime(String time) {
        if (time.isEmpty()) return;
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='time']")));
        input.clear();
        input.sendKeys(time);
    }

    public void selectEventType(String eventType) {
        if (eventType.isEmpty()) return;
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("mat-select[formControlName='eventTypeId']")));
        dropdown.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(),'" + eventType + "')]")));
        option.click();
    }

    public void enterDescription(String description) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea[formControlName='description']")));
        input.clear();
        input.sendKeys(description);
    }

    public void toggleIsPublic(boolean isPublic) {
        WebElement toggle = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("mat-slide-toggle[formControlName='isPublic']")));
        if (isPublic && !toggle.getAttribute("class").contains("mat-mdc-slide-toggle-checked")) {
            toggle.click();
        }
    }

    public void submitForm() {
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@matStepperNext]")));
        next.click();
    }
}