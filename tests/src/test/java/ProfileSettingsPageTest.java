import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfileSettingsPageTest extends TestBase {

    private LoginPage loginPage;
    private ProfileSettingsPage profilePage;

    @Before
    public void setUp() {
        loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.enterValidUsername();
        loginPage.enterValidPassword();
        loginPage.clickLogin();

        loginPage.wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));

        profilePage = new ProfileSettingsPage(driver);
        profilePage.goTo();
    }

    // Filling textarea content, file upload
    @Test
    public void testEditProfileSuccess() throws Exception {
        profilePage.enterLocality("Test city");
        profilePage.enterDescription("Test description.");
        profilePage.setParticipated(true);

        URL resource = getClass().getClassLoader().getResource("profile-picture.jpg");
        assertNotNull("Test image not found in resources", resource);
        File file = Paths.get(resource.toURI()).toFile();
        profilePage.uploadProfilePicture(file.getAbsolutePath());

        profilePage.clickSend();

        String msg = profilePage.getSuccessMessage().toLowerCase();
        assertTrue("Expected success confirmation", msg.contains("successful modification"));
    }
}
