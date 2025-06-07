package wedoevents.eventplanner.eventManagement.eventMangementTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class EventAgendaPage {

    private final WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "mat-table mat-row")
    private List<WebElement> tableRows;

    public EventAgendaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        PageFactory.initElements(driver, this);
    }

    private WebElement getRow(int index) {
        return tableRows.get(index);
    }

    private WebElement getCellInput(int rowIndex, String columnClass) {
        WebElement row = getRow(rowIndex);
        return row.findElement(By.cssSelector(".cdk-column-" + columnClass + " input, .cdk-column-" + columnClass + " textarea"));
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
            WebElement btn = row.findElement(By.cssSelector(".icon-disabled"));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public void clickDeleteButton(int rowIndex) {
        WebElement row = getRow(rowIndex);
        WebElement btn = row.findElement(By.cssSelector(".cdk-column-trash button"));
        btn.click();
    }
    public boolean isNextButtonDisabled() {
        WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@matStepperNext]")));
        return !nextButton.isEnabled();
    }
    public int getRowCount() {
        return tableRows.size();
    }
}
