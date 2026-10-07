package pages;

import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.EnvConfig;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //кнопка "Конструктор"
    private By constructorButton = By.xpath(".//p[contains(@class, 'AppHeader_header') and normalize-space(text())='Конструктор']");

    //кнопка лого
    private By logoButton = By.xpath(".//div[contains(@class, 'AppHeader_header__logo')]");

    //кнопка "Личный Кабинет"
    private By myAccountButton = By.xpath(".//p[contains(@class, 'AppHeader_header') and normalize-space(text())='Личный Кабинет']");

    //кнопка "Войти в аккаунт"
    private By loginButton = By.cssSelector(".button_button__33qZ0.button_button_type_primary__1O7Bx.button_button_size_large__G21Vg");

    //кнопка "Оформить заказ"
    private By makeOrderButton = By.xpath(".//button[contains(@class, 'button_button') and normalize-space(text())='Оформить заказ']");

    //блок конструктора бургера
    private By burgerConstructor = By.cssSelector(".BurgerIngredients_ingredients__1N8v2");

    //вкладка "Булки"
    private By bunsTab = By.xpath(".//span[contains(@class, 'text text_type') and normalize-space(text())='Булки']");

    //вкладка "Соусы"
    private By saucesTab = By.xpath(".//span[contains(@class, 'text text_type') and normalize-space(text())='Соусы']");

    //вкладка "Начинки"
    private By fillingsTab = By.xpath(".//span[contains(@class, 'text text_type') and normalize-space(text())='Начинки']");

    @Step("Открытие домашней страницы")
    public void open() {
        driver.get(EnvConfig.BASE_URI);
    }

    @Step("Нажать на кнопку Личный кабинет")
    public void clickMyAccountButton() {
        driver.findElement(myAccountButton).click();
    }

    @Step("Нажать на кнопку Войти в аккаунт")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Дождаться появления кнопки Оформить заказ")
    public void waitForMakeOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(makeOrderButton));
    }

    @Step("Проверить, что кнопка заказа отображается")
    public boolean checkLoginSuccess() {
        return driver.findElement(makeOrderButton).isDisplayed();
    }

    @Step("Дождаться появления конструктора бургера")
    public void waitForConstructor() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(burgerConstructor));
    }

    @Step("Проверить, что конструктор бургера отображается")
    public boolean checkNavigationToConstructorSuccess() {
        return driver.findElement(burgerConstructor).isDisplayed();
    }

    @Step("Нажать на кнопку Конструктор")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Нажать на лого")
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }

    @Step("Нажать на раздел Булки")
    public void clickBunsTab() {
        driver.findElement(bunsTab).click();
    }

    @Step("Нажать на раздел Соусы")
    public void clickSaucesTab() {
        driver.findElement(saucesTab).click();
    }

    @Step("Нажать на раздел Начинки")
    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    @Step("Получение названия текущей активной вкладки конструктора")
    public String getActiveConstructorTabName() {
        return driver.findElement(By.xpath(".//div[contains(@class, 'tab_tab_type_current')]")).getText();
    }

    @Step("Дождаться переключения активной вкладки конструктора")
    public void waitForActiveConstructorTab(String tabName) {
        Awaitility.await()
                .atMost(EnvConfig.EXPLICIT_WAIT, TimeUnit.SECONDS)
                .pollInterval(500, TimeUnit.MILLISECONDS)
                .until(() -> tabName.equals(getActiveConstructorTabName()));
    }

    @Step("Проверка, что вкладка конструктора стала активной")
    public void checkConstructorTabIsCurrent(String tabName) {
        assertEquals(tabName, getActiveConstructorTabName(), "Некорректная активная вкладка конструктора");
    }
}
