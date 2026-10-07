package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.EnvConfig;

import java.time.Duration;

public class RecoverPasswordPage {

    private WebDriver driver;

    public RecoverPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    //Заголовок "Восстановление пароля"
    private By recoverPasswordTitle = By.xpath("//h2[contains(text(), 'Восстановление пароля')]");

    //кнопка "Войти"
    private By loginButton = By.xpath(".//a[@href='/login']");

    @Step("Дождаться загрузки страницы Восстановление пароля")
    public void waitForPasswordRecoveryPage() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(recoverPasswordTitle));
    }

    @Step("Нажать на кнопку Войти")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
