package driver;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

public class DriverExtension implements BeforeEachCallback, AfterEachCallback {
    private DriverFactory factory = new DriverFactory();

    @Override
    public void afterEach(ExtensionContext context) {
        factory.tearDown();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        factory.setUpDriver();
        factory.openPage();
    }

    public WebDriver getDriver() {
        return factory.getDriver();
    }
}