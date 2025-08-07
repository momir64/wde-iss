package wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
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
        WebElement typeSelectTrigger = firstRow.findElement(
                By.cssSelector("mat-select[placeholder='Type'] .mat-mdc-select-trigger")
        );
        typeSelectTrigger.click();

        // Wait for dropdown options to be rendered
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mat-mdc-option")));

        // Wait for overlay backdrop to finish animating away
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".cdk-overlay-backdrop")));

        // Now wait until the first option is fully interactable and safe to click
        By optionSelector = By.cssSelector(".mat-mdc-option");

        wait.until(driver -> {
            WebElement option = driver.findElement(optionSelector);
            try {
                return option.isDisplayed() &&
                        option.isEnabled() &&
                        option.getLocation().getY() > 0 &&
                        option.getSize().getHeight() > 0;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        WebElement option = driver.findElement(optionSelector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
        option.click();
    }


    public void selectFirstCategoryOption() {
        WebElement categorySelect = firstRow.findElement(By.cssSelector("mat-select[placeholder='Category']"));

        if (Boolean.parseBoolean(categorySelect.getAttribute("aria-disabled"))) {
            throw new IllegalStateException("Category select is disabled");
        }

        WebElement categoryTrigger = categorySelect.findElement(By.cssSelector(".mat-mdc-select-trigger"));
        categoryTrigger.click();

        By optionSelector = By.cssSelector(".mat-mdc-option");

        // Wait for options to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(optionSelector));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".cdk-overlay-backdrop")));

        // Wait until one is interactable
        wait.until(driver -> {
            try {
                WebElement option = driver.findElement(optionSelector);
                return option.isDisplayed() &&
                        option.isEnabled() &&
                        option.getLocation().getY() > 0 &&
                        option.getSize().getHeight() > 0;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        // Click with retry: refetch element until successful
        int retries = 3;
        while (retries-- > 0) {
            try {
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionSelector));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
                option.click();
                return; // Success
            } catch (StaleElementReferenceException e) {
                if (retries == 0) throw e; // rethrow if out of retries
                System.out.println("Retrying after stale reference...");
            }
        }
    }



}
