package wedoevents.eventplanner.eventManagement;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver webDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();

        //headless setup
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        return new ChromeDriver(options);
    }
}