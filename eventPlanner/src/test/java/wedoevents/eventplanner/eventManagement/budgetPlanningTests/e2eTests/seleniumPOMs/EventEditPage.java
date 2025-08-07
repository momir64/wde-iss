package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.time.Duration;
import java.util.List;

public class EventEditPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public EventEditPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateToBudget() {
        By budgetTabLocator = By.xpath("//div[@role='tab' and contains(., 'Budget')]");
        WebElement budgetTab = wait.until(ExpectedConditions.elementToBeClickable(budgetTabLocator));
        budgetTab.click();
    }

    public void navigateToActualEditPage() {
        By editButtonLocator = By.xpath("//button[normalize-space(.)='Edit']");
        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonLocator));

        Actions actions = new Actions(driver);
        actions.moveToElement(editButton).click().perform();
    }

    public int numberOfBudgetItems() {
        By rowsLocator = By.xpath("//mat-table//mat-row");
        try {
            List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
            return rows.size();
        } catch (TimeoutException e) {
            return 0;
        }
    }

    public boolean containsListingBudgetItem(ListingType listingType, String category, int maxBudget) {
        List<WebElement> rows = getVisibleBudgetRows();
        for (WebElement row : rows) {
            if (matchesBudgetItem(row, listingType, category, maxBudget)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteBudgetItemIfDeletable(ListingType listingType, String category, int maxBudget) {
        List<WebElement> rows = getVisibleBudgetRows();
        for (WebElement row : rows) {
            if (matchesBudgetItem(row, listingType, category, maxBudget)) {
                WebElement deleteButton = wait.until(driver -> row.findElement(By.xpath(".//button[.//mat-icon[normalize-space(text())='delete']]")));
                if (deleteButton.isEnabled()) {
                    wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean changeBudgetItemPriceIfChangeable(ListingType listingType, String category, int oldMaxBudget, int newMaxBudget) {
        List<WebElement> rows = getVisibleBudgetRows();
        for (WebElement row : rows) {
            if (matchesBudgetItem(row, listingType, category, oldMaxBudget)) {
                WebElement budgetInput = wait.until(driver -> row.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-budget')]//input")));
                wait.until(ExpectedConditions.elementToBeClickable(budgetInput)).clear();
                budgetInput.sendKeys(String.valueOf(newMaxBudget));
                return true;
            }
        }
        return false;
    }

    public boolean navigateToBoughtListingPageIfBought(ListingType listingType, String category, int maxBudget) {
        List<WebElement> rows = getVisibleBudgetRows();
        for (WebElement row : rows) {
            if (matchesBudgetItem(row, listingType, category, maxBudget)) {
                WebElement navigateButton = wait.until(driver -> row.findElement(By.xpath(".//button[.//mat-icon[normalize-space(text())='frame_inspect']]")));
                if (navigateButton.isEnabled()) {
                    wait.until(ExpectedConditions.elementToBeClickable(navigateButton)).click();
                    return true;
                }
            }
        }
        return false;
    }

    public void clickSave() {
        By saveButtonLocator = By.xpath("//button[@type='submit' and .//span[contains(text(), 'Save')]]");
        By loadingSpinnerLocator = By.cssSelector("div.loading-budget");

        WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(saveButtonLocator));
        Actions actions = new Actions(driver);
        actions.moveToElement(saveButton).click().perform();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinnerLocator));
    }

    private List<WebElement> getVisibleBudgetRows() {
        By rowsLocator = By.xpath("//mat-table//mat-row");
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
        } catch (TimeoutException e) {
            return List.of();
        }
    }

    private boolean matchesBudgetItem(WebElement row, ListingType listingType, String category, int budgetToMatch) {
        String type = row.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-type')]")).getText().trim();
        String cat = row.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-category')]")).getText().trim();
        String budgetStr = row.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-budget')]//input")).getAttribute("value").trim();
        int budget;
        try {
            budget = Integer.parseInt(budgetStr);
        } catch (NumberFormatException e) {
            return false;
        }

        return type.equalsIgnoreCase(listingType.toString())
                && cat.equalsIgnoreCase(category)
                && budget == budgetToMatch;
    }
}