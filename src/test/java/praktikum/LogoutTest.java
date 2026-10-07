package praktikum;

import client.UserApiClient;
import driver.DriverExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pages.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutTest {

    HomePage homePageObj;
    LoginPage loginPageObj;
    RegistrationPage regPageObj;
    MyAccountPage myAccPageObj;

    private String email;
    private String password;

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

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
    @DisplayName("Проверка выхода по кнопке «Выйти» в личном кабинете")
    public void exitFromPersonalAccountSuccessTest() {
        myAccPageObj.clickLogoutButton();
        loginPageObj.waitForLoginPageLoading();
        assertTrue(myAccPageObj.checkPersonalAccountExitSuccess());
    }

    @AfterEach
    public void tearDown() {
        UserApiClient user = new UserApiClient();
        user.deleteUser(user.getBearerToken(user.loginUser(regPageObj.getEmail(), regPageObj.getPassword())));
    }
}
