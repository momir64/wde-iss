package wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EventBudgetRow {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement firstRow;

    public EventBudgetRow(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.firstRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("mat-row.mat-mdc-row")));
    }
    public void setBudget(String amount) {
        WebElement budgetInput = firstRow.findElement(By.cssSelector("input[placeholder='Amount...']"));
        budgetInput.clear();
        budgetInput.sendKeys(amount);
    }
    public void submitForm() {
        WebElement nextButton = driver.findElement(By.cssSelector("div.btn-group button.mat-stepper-next"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", nextButton);
    }
    public void selectFirstTypeOption() {
        WebElement typeSelectTrigger = firstRow.findElement(By.cssSelector("mat-select[placeholder='Type'] .mat-mdc-select-trigger"));
        typeSelectTrigger.click();

        try {
            Thread.sleep(500); // Adjust the time as necessary
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(" .mat-mdc-option")));
        option.click();
    }

    public void selectFirstCategoryOption() {
        WebElement categorySelect = firstRow.findElement(By.cssSelector("mat-select[placeholder='Category']"));

        if (Boolean.parseBoolean(categorySelect.getAttribute("aria-disabled"))) {
            throw new IllegalStateException("Category select is disabled");
        }

        WebElement categoryTrigger = categorySelect.findElement(By.cssSelector(".mat-mdc-select-trigger"));
        categoryTrigger.click();

        try {
            Thread.sleep(500); // Adjust the time as necessary
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(" .mat-mdc-option")));
        option.click();
    }
}
