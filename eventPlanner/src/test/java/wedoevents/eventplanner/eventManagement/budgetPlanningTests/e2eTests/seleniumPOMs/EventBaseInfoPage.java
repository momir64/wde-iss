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

    public boolean isEventNameValid() {
        return isFieldValid(By.cssSelector("input[formControlName='name']"), "Must start with an uppercase letter");
    }

    public void enterGuestCount(int guestCount) {
        WebElement guestCountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='guestCount']")));
        guestCountInput.clear();
        guestCountInput.sendKeys(String.valueOf(guestCount));
    }

    public boolean isGuestCountValid() {
        return isFieldValid(By.cssSelector("input[formControlName='guestCount']"), "Must be at least 1");
    }
    public void eraseGuestCount() {
        WebElement guestCountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='guestCount']")));
        guestCountInput.clear(); // Clear the guest count input field
        guestCountInput.sendKeys(Keys.BACK_SPACE);
        guestCountInput.sendKeys(Keys.BACK_SPACE);
        guestCountInput.sendKeys(Keys.BACK_SPACE);
        guestCountInput.sendKeys(Keys.BACK_SPACE);
        guestCountInput.sendKeys(Keys.TAB);
    }
    public void selectCity(String city) {
        if(city.isEmpty()) return;
        // Wait for dropdown to be clickable
        WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("mat-select[formControlName='city']")));
        cityDropdown.click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Now select the city option
        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[contains(text(), '" + city + "')]")));
        cityOption.click();
    }

    public void enterAddress(String address) {
        WebElement addressInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='address']")));
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    public boolean isAddressValid() {
        return isFieldValid(By.cssSelector("input[formControlName='address']"), "Address is required");
    }

    public void enterDate(String date) {
        if(date.isEmpty()) return;
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='date']")));
        dateInput.sendKeys(Keys.CONTROL + "a"); // Clear existing value
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.RETURN);
    }

    public boolean isDateValid() {
        return isFieldValid(By.cssSelector("input[formControlName='date']"), "Date is required");
    }

    public void enterTime(String time) {
        if(time.isEmpty()) return;
        WebElement timeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formControlName='time']")));
        timeInput.clear();
        timeInput.sendKeys(time);
    }

    public boolean isTimeValid() {
        return isFieldValid(By.cssSelector("input[formControlName='time']"), "Time is required");
    }

    public void selectEventType(String eventType) {
        if(eventType.isEmpty()) return;
        WebElement eventTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("mat-select[formControlName='eventTypeId']")));
        eventTypeDropdown.click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
        String currentState = isPublicToggle.getAttribute("aria-checked");
        if (isPublic) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            isPublicToggle.click();
        }
    }

    public void submitForm() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@matStepperNext]")));
        nextButton.click();
    }

    public boolean isNextButtonDisabled() {
        WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@matStepperNext]")));
        return !nextButton.isEnabled();
    }

    private boolean isFieldValid(By inputLocator, String expectedErrorMessage) {
        try {
            WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocator));
            inputField.click(); // Ensure the field is active
            inputField.sendKeys(Keys.TAB); // Move focus away to trigger validation

            // Wait for error messages
            List<WebElement> errorMessages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("mat-error")));

            for (WebElement error : errorMessages) {
                if (error.getText().trim().equals(expectedErrorMessage)) {
                    return false; // Field is invalid if the expected error appears
                }
            }
            return true; // No error message means the field is valid
        } catch (TimeoutException e) {
            return true; // If no error appears after waiting, assume field is valid
        }
    }
}
