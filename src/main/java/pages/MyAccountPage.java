package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.EnvConfig;

import java.time.Duration;

public class MyAccountPage {

    private WebDriver driver;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    //Заголовок
    private By pageInfo = By.xpath(".//p[contains(@class, 'Account_text') and normalize-space(text())='В этом разделе вы можете изменить свои персональные данные']");

    //Меню личного кабинета
    private By accountNavigation = By.className("Account_nav__Lgali");

    //кнопка "Выход"
    private By logoutButton = By.xpath(".//button[contains(@class, 'Account_button') and normalize-space(text())='Выход']");

    @Step("Дождаться загрузки личного кабинета")
    public void waitForMyAccountLoading() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(accountNavigation));
    }

    @Step("Проверка входа в личный кабинет")
    public boolean checkPersonalAccountOpenSuccess() {
        return driver.findElement(accountNavigation).isDisplayed();
    }

    @Step("Нажать на кнопку Выход")
    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    @Step("Проверка выхода из личного кабинета")
    public boolean checkPersonalAccountExitSuccess() {
        return driver.findElements(pageInfo).isEmpty();
    }

}
