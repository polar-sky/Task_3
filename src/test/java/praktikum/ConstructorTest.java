package praktikum;

import driver.DriverExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.HomePage;

public class ConstructorTest {

    private HomePage homePageObj;

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    @BeforeEach
    public void setUp() {
        homePageObj = new HomePage(extension.getDriver());
        homePageObj.open();
    }

    @ParameterizedTest(name = "Переход к разделу {0}")
    @ValueSource(strings = {"Начинки", "Соусы", "Булки"})
    @DisplayName("Проверка переходов к разделам конструктора")
    public void navigateToConstructorTabsSuccessTest(String tabName) {
        if ("Булки".equals(tabName)) {
            //кликаем сначала на другую табу, потому что "Булки" активны по умолчанию, что не дает проверить переход к разделу
            homePageObj.clickSaucesTab();
            homePageObj.clickBunsTab();
        }
        if ("Соусы".equals(tabName)) homePageObj.clickSaucesTab();
        if ("Начинки".equals(tabName)) homePageObj.clickFillingsTab();
        homePageObj.waitForActiveConstructorTab(tabName);
        homePageObj.checkConstructorTabIsCurrent(tabName);
    }
}
