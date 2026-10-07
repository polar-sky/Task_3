package praktikum;

import client.UserApiClient;
import driver.DriverExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.HomePage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import pages.RegistrationPage;

public class LoginTest {

    HomePage homePageObj;
    LoginPage loginPageObj;
    RegistrationPage regPageObj;
    RecoverPasswordPage recPassPageObj;
    private String email;
    private String password;

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    @BeforeEach
    public void setUp() {
        homePageObj = new HomePage(extension.getDriver());
        loginPageObj = new LoginPage(extension.getDriver());
        regPageObj = new RegistrationPage(extension.getDriver());
        homePageObj.clickLoginButton();
        loginPageObj.clickRegistrationButton();
        regPageObj.enterRandomCredentials();
        regPageObj.clickRegistrationButton();
        loginPageObj.waitForLoginPageLoading();
        email = regPageObj.getEmail();
        password = regPageObj.getPassword();
        homePageObj.open();
    }

    @ParameterizedTest(name = "Вход через кнопку: {0}")
    @ValueSource(strings = {"Войти в аккаунт", "Личный кабинет"})
    @DisplayName("Проверка входа по кнопке на главной странице")
    public void loginByLoginButtonHomePageTest(String value) {
        if ("Войти в аккаунт".equals(value)) homePageObj.clickLoginButton();
        if ("Личный кабинет".equals(value)) homePageObj.clickMyAccountButton();
        loginPageObj.enterEmail(email);
        loginPageObj.enterPassword(password);
        loginPageObj.clickLoginButton();
        homePageObj.waitForMakeOrderButton();
        homePageObj.checkLoginSuccess();
    }

    @Test
    @DisplayName("Проверка входа через кнопку в форме регистрации")
    public void loginByRegistrationPageTest() {
        homePageObj.clickMyAccountButton();
        loginPageObj.isLoginPageLoaded();
        loginPageObj.clickRegistrationButton();
        regPageObj.waitForRegistrationPage();
        regPageObj.clickLoginButton();
        loginPageObj.enterEmail(email);
        loginPageObj.enterPassword(password);
        loginPageObj.clickLoginButton();
        homePageObj.waitForMakeOrderButton();
        homePageObj.checkLoginSuccess();
    }

    @Test
    @DisplayName("Проверка входа через кнопку в форме восстановления пароля")
    public void loginByRecoverPasswordPageTest() {
        homePageObj.clickMyAccountButton();
        loginPageObj.isLoginPageLoaded();
        loginPageObj.clickPasswordRecoveryButton();
        recPassPageObj = new RecoverPasswordPage(extension.getDriver());
        recPassPageObj.waitForPasswordRecoveryPage();
        recPassPageObj.clickLoginButton();
        loginPageObj.isLoginPageLoaded();
        loginPageObj.enterEmail(email);
        loginPageObj.enterPassword(password);
        loginPageObj.clickLoginButton();
        homePageObj.waitForMakeOrderButton();
        homePageObj.checkLoginSuccess();
    }

    @AfterEach
    public void tearDown() {
        UserApiClient user = new UserApiClient();
        user.deleteUser(user.getBearerToken(user.loginUser(regPageObj.getEmail(), regPageObj.getPassword())));
    }

}
