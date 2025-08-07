package wedoevents.eventplanner.eventManagement;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver webDriver() {
        // WebDriverManager.chromedriver().setup();
        WebDriverManager.chromiumdriver().setup();
        return new ChromeDriver();

        //headless setup
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        return new ChromeDriver(options);
    }
}