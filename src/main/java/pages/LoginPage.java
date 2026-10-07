package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.EnvConfig;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //Заголовок "Вход"
    private By entryTitle = By.xpath("//h2[contains(text(), 'Вход')]");

    //Поле ввода "Email"
    private By emailField = By.xpath("//label[normalize-space()='Email']/following::input[1]");

    //Поле ввода "Пароль"
    private By passwordField = By.xpath("//input[@name='Пароль']");

    //кнопка "Войти"
    //private By loginButton = By.xpath(".//button[contains(@class, 'button_button') and normalize-space(text())='Войти']");
    private By loginButton = By.xpath("//button[contains(text(), 'Войти')]");

    //Кнопка "Зарегистрироваться"
    private By registrationButton = By.xpath(".//a[@href='/register']");

    //Кнопка "Восстановить пароль"
    private By recoverPasswordButton = By.xpath(".//a[@href='/forgot-password']");


    @Step("Дождаться загрузки страницы Вход")
    public void waitForLoginPageLoading() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(entryTitle));
    }

    @Step("Нажать кнопку Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Проверка открытия экрана Вход")
    public boolean isLoginPageLoaded() {
        return driver.findElement(entryTitle).isDisplayed();
    }

    @Step("Заполнить поле Email")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнить поле Пароль")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать на кнопку Войти")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Нажать кнопку Восстановить пароль")
    public void clickPasswordRecoveryButton() {
        driver.findElement(recoverPasswordButton).click();
    }


}
