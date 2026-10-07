package praktikum;

import client.UserApiClient;
import driver.DriverExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;

public class RegistrationTest {

    HomePage homePageObj;
    LoginPage loginPageObj;
    RegistrationPage regPageObj;

    private boolean isRegistrationSuccess;

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    @BeforeEach
    public void setUp(){
        homePageObj = new HomePage(extension.getDriver());
        loginPageObj = new LoginPage(extension.getDriver());
        regPageObj = new RegistrationPage(extension.getDriver());
        homePageObj.clickLoginButton();
        loginPageObj.clickRegistrationButton();
    }

    @Test
    @DisplayName("Проверка успешной регистрации пользователя")
    public void checkUserRegistrationSuccessTest() {
        regPageObj.enterRandomCredentials();
        regPageObj.clickRegistrationButton();
        loginPageObj.waitForLoginPageLoading();
        isRegistrationSuccess = loginPageObj.isLoginPageLoaded();
    }

    @Test
    @DisplayName("Проверка ошибки для пароля")
    public void checkShortPasswordErrorTest() {
        regPageObj.enterPassword("123qw");
        regPageObj.clickRegistrationButton();
        regPageObj.waitForPasswordErrorText();
        regPageObj.checkPasswordErrorText();
    }

    @AfterEach
    public void tearDown() {
        if (isRegistrationSuccess) {
            UserApiClient user = new UserApiClient();
            user.deleteUser(user.getBearerToken(user.loginUser(regPageObj.getEmail(), regPageObj.getPassword())));
        }
    }
}
