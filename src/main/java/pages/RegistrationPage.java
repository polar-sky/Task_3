package pages;

import io.qameta.allure.Step;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.EnvConfig;
import resources.ErrorMessageConfig;

import java.time.Duration;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationPage {

    private WebDriver driver;

    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    //Заголовок "Вход"
    private By entryTitle = By.xpath("//h2[contains(text(), 'Регистрация')]");

    //Кнопка "Зарегистрироваться"
    private By registrationButton = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");

    //Поле ввода "Имя"
    private By nameField = By.xpath("//label[normalize-space()='Имя']/following::input[1]");

    //Поле ввода "Email"
    private By emailField = By.xpath("//label[normalize-space()='Email']/following::input[1]");

    //Поле ввода "Пароль"
    private By passwordField = By.xpath("//input[@name='Пароль']");

    //Текст ошибки "Некорректный пароль"
    private By passwordErrorText = By.cssSelector(".input__error.text_type_main-default");

    //кнопка "Войти"
    private By loginButton = By.xpath(".//a[@href='/login']");

    @Step("Дождаться загрузки страницы Регистрация")
    public void waitForRegistrationPage() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(entryTitle));
    }

    @Step("Заполнить поле Имя")
    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    @Step("Заполнить поле Email")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        this.email = email;
    }

    @Step("Заполнить поле Пароль")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        this.password = password;
    }

    @Step("Нажать кнопку Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Дождаться появления ошибки Некорректный пароль")
    public void waitForPasswordErrorText() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(passwordErrorText));
    }

    @Step("Проверить текст ошибки для некорректного пароля")
    public void checkPasswordErrorText() {
        String actualErrorMessage = driver.findElement(passwordErrorText).getText();
        assertEquals(ErrorMessageConfig.INCORRECT_PASSWORD, actualErrorMessage, "Текст ошибки не корректный");
    }

    @Step("Заполнение данных пользователя радомными значениями")
    public void enterRandomCredentials() {
        Faker f = new Faker(new Locale("ru"));
        String name = f.name().firstName();
        String email = f.bothify("???????###@ya.ru");
        String password = f.bothify("???##!");
        enterName(name);
        enterEmail(email);
        enterPassword(password);
    }

    @Step("Нажать кнопку Войти")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

}
