package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EventBudgetRow {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement row;

    public EventBudgetRow(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.row = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(
            "(//mat-row[" +
                    ".//mat-select[@placeholder='Type']//span[contains(@class, 'mat-mdc-select-placeholder')] and " +
                    ".//mat-select[@placeholder='Category']//span[contains(@class, 'mat-mdc-select-placeholder')] and " +
                    ".//input[@placeholder='Amount...' and (not(@value) or @value='')]" +
                    "])[1]"
                )));
    }

    public void setBudget(String amount) {
        WebElement budgetInput = row.findElement(By.cssSelector("input[placeholder='Amount...']"));
        budgetInput.clear();
        budgetInput.sendKeys(amount);
    }

    public void selectProductTypeOption() {
        WebElement typeSelectTrigger = row.findElement(By.cssSelector("mat-select[placeholder='Type'] .mat-mdc-select-trigger"));
        typeSelectTrigger.click();

        try {
            Thread.sleep(500); // Adjust the time as necessary
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[.//span[normalize-space(text())='Product']]")));
        option.click();
    }

    public void selectServiceTypeOption() {
        WebElement typeSelectTrigger = row.findElement(By.cssSelector("mat-select[placeholder='Type'] .mat-mdc-select-trigger"));
        typeSelectTrigger.click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[.//span[normalize-space(text())='Service']]")));
        option.click();
    }

    public void selectCustomCategoryOption(String category) {
        WebElement categorySelect = row.findElement(By.cssSelector("mat-select[placeholder='Category']"));

        if (Boolean.parseBoolean(categorySelect.getAttribute("aria-disabled"))) {
            throw new IllegalStateException("Category select is disabled");
        }

        WebElement categoryTrigger = categorySelect.findElement(By.cssSelector(".mat-mdc-select-trigger"));
        categoryTrigger.click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[.//span[normalize-space(text())='" + category + "']]")));
        option.click();
    }

    public boolean isCustomCategoryEnabled(String category) {
        WebElement categorySelect = row.findElement(By.cssSelector("mat-select[placeholder='Category']"));

        if (Boolean.parseBoolean(categorySelect.getAttribute("aria-disabled"))) {
            throw new IllegalStateException("Category select is disabled");
        }

        WebElement categoryTrigger = categorySelect.findElement(By.cssSelector(".mat-mdc-select-trigger"));
        categoryTrigger.click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-option[.//span[normalize-space(text())='" + category + "']]")));

        return !Boolean.parseBoolean(option.getAttribute("aria-disabled"));
    }
}
