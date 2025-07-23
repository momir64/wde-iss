package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wedoevents.eventplanner.listingManagement.models.ListingType;

import java.time.Duration;
import java.util.List;

public class EventEditPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public EventEditPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
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

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(editButtonLocator));
        editButton.click();
    }

    public int numberOfBudgetItems() {
        By rowsLocator = By.xpath("//mat-table//mat-row");
        List<WebElement> budgetItems;

        try {
            budgetItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
        } catch (TimeoutException e) {
            return 0;
        }

        return budgetItems.size();
    }

    public boolean containsProductBudgetItem(ListingType listingType, String category, int maxBudget) {
        By rowsLocator = By.xpath("//mat-table//mat-row");
        List<WebElement> budgetItems;

        try {
            budgetItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
        } catch (TimeoutException e) {
            return false;
        }

        for (WebElement bi : budgetItems) {
            String type = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-type')]")).getText().trim();
            String cat = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-category')]")).getText().trim();
            String budgetStr = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-budget')]//input")).getAttribute("value").trim();

            int budget = Integer.parseInt(budgetStr);

            if (type.equalsIgnoreCase(listingType.toString())
                    && cat.equalsIgnoreCase(category)
                    && budget == maxBudget) {
                return true;
            }
        }

        return false;
    }

    public boolean deleteBudgetItemIfDeletable(ListingType listingType, String category, int maxBudget) {
        By rowsLocator = By.xpath("//mat-table//mat-row");
        List<WebElement> budgetItems;

        try {
            budgetItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
        } catch (TimeoutException e) {
            return false;
        }

        for (WebElement bi : budgetItems) {
            String type = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-type')]")).getText().trim();
            String cat = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-category')]")).getText().trim();
            String budgetStr = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-budget')]//input")).getAttribute("value").trim();
            WebElement deleteButton = bi.findElement(By.xpath(".//button[.//mat-icon[normalize-space(text())='delete']]"));

            int budget = Integer.parseInt(budgetStr);

            if (type.equalsIgnoreCase(listingType.toString())
                    && cat.equalsIgnoreCase(category)
                    && budget == maxBudget) {
                if (deleteButton.isEnabled()) {
                    deleteButton.click();
                    return true;
                }
            }
        }

        return false;
    }

    public boolean changeBudgetItemPriceIfChangeable(ListingType listingType, String category, int oldMaxBudget, int newMaxBudget) {
        By rowsLocator = By.xpath("//mat-table//mat-row");
        List<WebElement> budgetItems;

        try {
            budgetItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
        } catch (TimeoutException e) {
            return false;
        }

        for (WebElement bi : budgetItems) {
            String type = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-type')]")).getText().trim();
            String cat = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-category')]")).getText().trim();
            String budgetStr = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-budget')]//input")).getAttribute("value").trim();
            WebElement budgetInput = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-budget')]//input"));

            int budget = Integer.parseInt(budgetStr);

            if (type.equalsIgnoreCase(listingType.toString())
                    && cat.equalsIgnoreCase(category)
                    && budget == oldMaxBudget) {
                budgetInput.clear();
                budgetInput.sendKeys(String.valueOf(newMaxBudget));
                return true;
            }
        }

        return false;
    }

    public boolean navigateToBoughtListingPageIfBought(ListingType listingType, String category, int maxBudget) {
        By rowsLocator = By.xpath("//mat-table//mat-row");
        List<WebElement> budgetItems;

        try {
            budgetItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
        } catch (TimeoutException e) {
            return false;
        }

        for (WebElement bi : budgetItems) {
            String type = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-type')]")).getText().trim();
            String cat = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-category')]")).getText().trim();
            String budgetStr = bi.findElement(By.xpath(".//mat-cell[contains(@class, 'cdk-column-budget')]//input")).getAttribute("value").trim();
            WebElement navigateButton = bi.findElement(By.xpath(".//button[.//mat-icon[normalize-space(text())='frame_inspect']]"));

            int budget = Integer.parseInt(budgetStr);

            if (type.equalsIgnoreCase(listingType.toString())
                    && cat.equalsIgnoreCase(category)
                    && budget == maxBudget) {
                if (navigateButton.isEnabled()) {
                    navigateButton.click();
                    return true;
                }
            }
        }

        return false;
    }

    public void clickSave() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By saveButtonLocator = By.xpath("//button[normalize-space(.)='Save']");

        wait.until(ExpectedConditions.elementToBeClickable(saveButtonLocator)).click();

        try {
            Thread.sleep(2700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
