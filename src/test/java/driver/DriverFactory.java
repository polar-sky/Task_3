package driver;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.EnvConfig;

import java.time.Duration;

public class DriverFactory {
    private WebDriver driver;
    private WebDriverWait wait;

    public void setUpDriver() {
        if ("yandex".equalsIgnoreCase(System.getProperty("browser"))) {
            setUpYandex();
        } else {
            setUpChrome();
        }
    }

    public void setUpChrome() {
        driver = new ChromeDriver();
    }

    public void setUpYandex() {
        WebDriverManager.chromedriver().driverVersion(System.getProperty("driver.version")).setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary(System.getProperty("yandex.binary.path"));

        driver = new ChromeDriver(options);
    }

    void openPage() {
        driver.get(EnvConfig.BASE_URI);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void tearDown() {
        driver.quit();
    }
}
