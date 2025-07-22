package wedoevents.eventplanner.eventManagement.budgetPlanningTests.e2eTests.seleniumPOMs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class EventRecommendedCategoriesModal {
    private WebDriver driver;
    private WebDriverWait wait;
    public final List<String> productCategories = List.of(
            "Drinks",
            "Food",
            "Lighting",
            "Stage & Tents"
    );
    public final List<String> serviceCategories = List.of(
            "Music",
            "Catering",
            "Photography",
            "Videography",
            "Guest Transportation",
            "Event Security"
    );
    public EventRecommendedCategoriesModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }
    public void clickCloseButton() {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".mat-mdc-dialog-component-host .container .close-button")
        ));
        closeButton.click();
    }
    public boolean areProductCategoriesPresent(List<String> expectedCategories) {
        List<String> actualCategories = getCategoriesFromRow(1);
        return actualCategories.containsAll(expectedCategories);
    }

    public boolean areServiceCategoriesPresent(List<String> expectedCategories) {
        List<String> actualCategories = getCategoriesFromRow(2);
        return actualCategories.containsAll(expectedCategories);
    }

    private List<String> getCategoriesFromRow(int rowIndex) {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".mat-mdc-dialog-component-host .container .row")
        ));

        if (rowIndex < 1 || rowIndex > rows.size()) {
            throw new IllegalArgumentException("Row index out of bounds");
        }

        WebElement targetRow = rows.get(rowIndex - 1);
        return targetRow.findElements(By.cssSelector(".recom-category"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
