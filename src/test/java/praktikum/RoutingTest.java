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
import pages.MyAccountPage;
import pages.RegistrationPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoutingTest {

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    HomePage homePageObj;
    LoginPage loginPageObj;
    RegistrationPage regPageObj;
    MyAccountPage myAccPageObj;

    private String email;
    private String password;

    @BeforeEach
    public void setUp(){
        homePageObj = new HomePage(extension.getDriver());
        loginPageObj = new LoginPage(extension.getDriver());
        regPageObj = new RegistrationPage(extension.getDriver());
        myAccPageObj = new MyAccountPage(extension.getDriver());
        homePageObj.clickLoginButton();
        loginPageObj.clickRegistrationButton();
        regPageObj.enterRandomCredentials();
        regPageObj.clickRegistrationButton();
        loginPageObj.waitForLoginPageLoading();
        email = regPageObj.getEmail();
        password = regPageObj.getPassword();
        loginPageObj.isLoginPageLoaded();
        loginPageObj.enterEmail(email);
        loginPageObj.enterPassword(password);
        loginPageObj.clickLoginButton();
        homePageObj.waitForMakeOrderButton();
        homePageObj.clickMyAccountButton();
        myAccPageObj.waitForMyAccountLoading();
    }

    @Test
    @DisplayName("Проверка перехода в личный кабинет")
    public void enterPersonalAccountSuccessTest() {
        homePageObj.clickMyAccountButton();
        myAccPageObj.waitForMyAccountLoading();
        assertTrue(myAccPageObj.checkPersonalAccountOpenSuccess());
    }

    @ParameterizedTest(name = "Проверка перехода по клику на {0}")
    @ValueSource(strings = {"Конструктор", "Лого"})
    @DisplayName("Переход из личного кабинета в конструктор")
    public void navigationToConstructorSuccessTest(String value) {
        if ("Конструктор".equals(value)) homePageObj.clickConstructorButton();
        if ("Лого".equals(value)) homePageObj.clickLogoButton();
        homePageObj.waitForConstructor();
        homePageObj.checkNavigationToConstructorSuccess();
    }

    @AfterEach
    public void tearDown() {
        UserApiClient user = new UserApiClient();
        user.deleteUser(user.getBearerToken(user.loginUser(regPageObj.getEmail(), regPageObj.getPassword())));
    }
}
