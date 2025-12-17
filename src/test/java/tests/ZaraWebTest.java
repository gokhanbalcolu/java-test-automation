package tests;

import base.baseTest;
import org.junit.jupiter.api.Test;
import pages.HomePage;

public class ZaraWebTest extends baseTest {

    @Test
    void searchTest() {

        driver.get("https://www.zara.com/tr/");

        HomePage home = new HomePage(driver);
        home.acceptCookiesIfPresent();

        // LOGIN STEP (SCENARIO REQUIREMENT)
        // loginPage.goToLogin();
        // loginPage.login("dummy@mail.com", "dummyPass");


    }
}
