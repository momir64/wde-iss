package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class EventBudgetRow {
    private final WebDriverWait wait;
    private final WebElement row;

    public EventBudgetRow(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.row = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//mat-row[" +
                        ".//mat-select[@placeholder='Type']//span[contains(@class, 'mat-mdc-select-placeholder')] and " +
                        ".//mat-select[@placeholder='Category']//span[contains(@class, 'mat-mdc-select-placeholder')] and " +
                        ".//input[@placeholder='Amount...' and (not(@value) or @value='')]" +
                        "])[1]")));
    }

    private WebElement waitInRow(By by) {
        return wait.until(driver -> row.findElement(by));
    }

    public void setBudget(String amount) {
        WebElement budgetInput = waitInRow(By.cssSelector("input[placeholder='Amount...']"));
        budgetInput.clear();
        budgetInput.sendKeys(amount);
    }

    public void selectProductTypeOption() {
        selectTypeOption("Product");
    }

    public void selectServiceTypeOption() {
        selectTypeOption("Service");
    }

    private void selectTypeOption(String optionText) {
        WebElement typeSelectTrigger = waitInRow(By.cssSelector("mat-select[placeholder='Type'] .mat-mdc-select-trigger"));
        typeSelectTrigger.click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[.//span[normalize-space(text())='" + optionText + "']]")));
        option.click();
    }

    public void selectCustomCategoryOption(String category) {
        WebElement categorySelect = waitInRow(By.cssSelector("mat-select[placeholder='Category']"));

        // Wait until the select is enabled (aria-disabled = false)
        wait.until(driver -> {
            String disabled = categorySelect.getAttribute("aria-disabled");
            return disabled == null || disabled.equals("false");
        });

        WebElement categoryTrigger = categorySelect.findElement(By.cssSelector(".mat-mdc-select-trigger"));
        wait.until(ExpectedConditions.elementToBeClickable(categoryTrigger)).click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[.//span[normalize-space(text())='" + category + "']]")));
        option.click();
    }

    public boolean isCustomCategoryEnabled(String category) {
        WebElement categorySelect = waitInRow(By.cssSelector("mat-select[placeholder='Category']"));

        wait.until(driver -> {
            String disabled = categorySelect.getAttribute("aria-disabled");
            return disabled == null || disabled.equals("false");
        });

        WebElement categoryTrigger = categorySelect.findElement(By.cssSelector(".mat-mdc-select-trigger"));
        wait.until(ExpectedConditions.elementToBeClickable(categoryTrigger)).click();

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-option[.//span[normalize-space(text())='" + category + "']]")));

        return !Boolean.parseBoolean(option.getAttribute("aria-disabled"));
    }
}