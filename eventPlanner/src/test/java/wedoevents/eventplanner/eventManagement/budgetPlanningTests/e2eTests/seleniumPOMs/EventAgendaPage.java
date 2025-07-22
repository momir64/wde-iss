package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EventAgendaPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public EventAgendaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        PageFactory.initElements(driver, this);
    }

    private WebElement getRow(int index) {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("mat-table mat-row")));
        return rows.get(index);
    }

    private WebElement getCellInput(int rowIndex, String columnClass) {
        WebElement row = getRow(rowIndex);
        By inputLocator = By.cssSelector(".cdk-column-" + columnClass + " input, .cdk-column-" + columnClass + " textarea");
        By inputLocatorXpath = By.xpath(
                "//mat-cell[" +
                        "contains(@class, 'cdk-column-" + columnClass + "') and " +
                        "contains(@class, 'mat-column-" + columnClass + "')]//input" +
                        " | " +
                        "//mat-cell[" +
                        "contains(@class, 'cdk-column-" + columnClass + "') and " +
                        "contains(@class, 'mat-column-" + columnClass + "')]//textarea"
        );
        return wait.until(ExpectedConditions.visibilityOf(row.findElement(inputLocatorXpath)));
    }

    public void setName(int rowIndex, String value) {
        WebElement input = getCellInput(rowIndex, "name");
        input.clear();
        input.sendKeys(value);
    }

    public void setDescription(int rowIndex, String value) {
        WebElement input = getCellInput(rowIndex, "description");
        input.clear();
        input.sendKeys(value);
    }

    public void setLocation(int rowIndex, String value) {
        WebElement input = getCellInput(rowIndex, "location");
        input.clear();
        input.sendKeys(value);
    }

    public void setStartTime(int rowIndex, String value) {
        WebElement input = getCellInput(rowIndex, "startTime");
        input.clear();
        input.sendKeys(value);
    }

    public void setEndTime(int rowIndex, String value) {
        WebElement input = getCellInput(rowIndex, "endTime");
        input.clear();
        input.sendKeys(value);
    }

    public String getName(int rowIndex) {
        return getCellInput(rowIndex, "name").getAttribute("value");
    }

    public String getDescription(int rowIndex) {
        return getCellInput(rowIndex, "description").getAttribute("value");
    }

    public String getLocation(int rowIndex) {
        return getCellInput(rowIndex, "location").getAttribute("value");
    }

    public String getStartTime(int rowIndex) {
        return getCellInput(rowIndex, "startTime").getAttribute("value");
    }

    public String getEndTime(int rowIndex) {
        return getCellInput(rowIndex, "endTime").getAttribute("value");
    }

    public boolean hasInvalidTimeClass(int rowIndex, String column) {
        WebElement input = getCellInput(rowIndex, column);
        return input.getAttribute("class").contains("invalid-time");
    }

    public boolean isDeleteButtonEnabled(int rowIndex) {
        WebElement row = getRow(rowIndex);
        try {
            // Wait up to 1s for presence of icon-disabled. If not found, it's enabled.
            wait.withTimeout(Duration.ofSeconds(1))
                    .until(ExpectedConditions.presenceOfNestedElementLocatedBy(row, By.cssSelector(".icon-disabled")));
            return false;
        } catch (Exception e) {
            return true;
        } finally {
            wait.withTimeout(Duration.ofSeconds(4)); // Reset default timeout
        }
    }

    public void clickDeleteButton(int rowIndex) {
        WebElement row = getRow(rowIndex);
        By btnLocator = By.cssSelector(".cdk-column-trash button");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(row.findElement(btnLocator)));
        btn.click();
    }

    public boolean isNextButtonDisabled() {
        WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@matStepperNext]")));
        return !nextButton.isEnabled();
    }

    public int getRowCount() {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("mat-table mat-row")));
        return rows.size();
    }
    public void clickAddAgendaRowButton() {
        By addButtonLocator = By.cssSelector(".row.action-buttons button");

        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator));
        addButton.click();
    }
    public void submitForm() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[text()='Finish']]")));
        nextButton.click();
    }
}
