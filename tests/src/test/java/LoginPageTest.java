import org.junit.Test;
import org.junit.Before;
import org.junit.Assume;
import static org.junit.Assert.*;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Instant;


public class LoginPageTest extends TestBase {

    private LoginPage loginPage;
    private boolean loginSuccessful = true;

    @Before
    public void setUp() {
        loginPage = new LoginPage(driver);
        loginPage.goTo();
    }

    // Fill simple form and send
    @Test
    public void testValidLogin() {
        loginPage.enterValidUsername();
        loginPage.enterValidPassword();

        loginPage.clickLogin();

        loginPage.waitForSuccessfulLogin();

        String currentUrl = driver.getCurrentUrl();
        loginSuccessful = !currentUrl.toLowerCase().contains("login");

        assertTrue("Login should succeed", loginSuccessful);
    }

    // Fill simple form and send
    @Test
    public void testInvalidLogin() {
        loginPage.enterInValidUsername();
        loginPage.enterInValidPassword();
        loginPage.clickLogin();

        String error = loginPage.getErrorMessage();

        assertTrue("Expected invalid credentials message", error.contains("Invalid username or password"));
    }

    // Logout; test case dependency
    @Test
    public void testLogout() {
        Assume.assumeTrue("Skipping testLogout because login failed", loginSuccessful);

        loginPage.enterValidUsername();
        loginPage.enterValidPassword();
        loginPage.clickLogin();

        loginPage.waitForSuccessfulLogin();

        loginPage.clickLogout();

        loginPage.waitForLoginRedirect();

        assertTrue("After logout user should be redirected to login page", driver.getCurrentUrl().toLowerCase().contains("login"));
    }

    // Send a form: fill up a form and send it to a server (user notification settings change); test case dependencies
    @Test
    public void testNotificationSettingsToggle() {
        Assume.assumeTrue("Skipping testNotificationSettingsToggle because login failed", loginSuccessful);

        loginPage.enterValidUsername();
        loginPage.enterValidPassword();
        loginPage.clickLogin();

        loginPage.waitForSuccessfulLogin();

        loginPage.goToNotificationSettings();

        loginPage.toggleRescueNotification();
        loginPage.clickSend();

        String successMsg = loginPage.getSuccessMessage();
        assertTrue("Expected success message after modifying notification settings", successMsg.toLowerCase().contains("successful modification"));
    }

    // Manipulate the cookie to force user logout; test case dependencies
    @Test
    public void testLogoutOnInvalidCookie() throws InterruptedException {
        Assume.assumeTrue("Skipping testLogoutOnInvalidCookie because login failed", loginSuccessful);

        loginPage.enterValidUsername();
        loginPage.enterValidPassword();
        loginPage.clickLogin();

        loginPage.waitForSuccessfulLogin();

        loginPage.invalidateAuthCookie();

        driver.navigate().refresh();

        loginPage.waitForLoginRedirect();

        assertTrue("With invalid auth cookie, user gets logged out",
            driver.getCurrentUrl().toLowerCase().contains("login"));
    }
}
