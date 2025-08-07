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

    public void submitForm() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[text()='Finish']]")));
        nextButton.click();
    }
}
