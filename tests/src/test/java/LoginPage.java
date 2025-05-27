import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {

    private static final String AUTH_COOKIE_NAME = ".AspNetCore.Identity.Application";

    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By logoutButton = By.xpath("//*[contains(@class, 'logout-area')]//*[contains(@class, 'logout-btn')]");
    private final By errorNotification = By.xpath("//*[contains(@class, 'notification-panel') and contains(@class, 'error-notification')]//*[contains(@class, 'notification-msg')]");

    private final By rescueCheckbox = By.id("Rescue");
    private final By successMessage = By.xpath("//p[contains(@class, 'valid-creds-text')]");
    private final By sendButton = By.xpath("//button[contains(@class, 'btn') and contains(@class, 'btn-primary') and @type='button']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void goTo() {
        driver.get("https://gis.inf.elte.hu/tiszta-tisza/dashboard/login");
    }

    public void enterValidUsername() {
        waitAndReturnElement(usernameField).sendKeys(Config.get("username"));
    }

    public void enterValidPassword() {
        waitAndReturnElement(passwordField).sendKeys(Config.get("password"));
    }

    public void enterInValidUsername() {
        waitAndReturnElement(usernameField).sendKeys("InvalidUsername");
    }

    public void enterInValidPassword() {
        waitAndReturnElement(passwordField).sendKeys("InvalidPassword");
    }

    public void clickLogin() {
        waitAndReturnElement(loginButton).click();
    }

    public String getErrorMessage() {
        try {
            return waitAndReturnElement(errorNotification).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void clickLogout() {
        waitAndReturnElement(logoutButton).click();
    }

    public void goToNotificationSettings() {
        driver.get("https://gis.inf.elte.hu/tiszta-tisza/dashboard/notifications");
    }

    public void toggleRescueNotification() {
        WebElement checkbox = waitAndReturnElement(rescueCheckbox);
        checkbox.click();
    }

    public void clickSend() {
        waitAndReturnElement(sendButton).click();
    }

    public String getSuccessMessage() {
        try {
            return waitAndReturnElement(successMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void waitForSuccessfulLogin() {
        wait.until(ExpectedConditions.not(
            ExpectedConditions.urlContains("login")
        ));
    }

    public void waitForLoginRedirect() {
        wait.until(ExpectedConditions.urlContains("login"));
    }

    public void invalidateAuthCookie() {
        String scriptDeleteCookie = "document.cookie = '.AspNetCore.Identity.Application=invalidvalue; path=/; domain=gis.inf.elte.hu;';";
        ((JavascriptExecutor) driver).executeScript(scriptDeleteCookie);

        // Alternative approach without JSExecutor:

        // Cookie invalidCookie = new Cookie.Builder(AUTH_COOKIE_NAME, "invalidvalue")
        //     .domain("gis.inf.elte.hu")
        //     .path("/")
        //     .isHttpOnly(true)
        //     .isSecure(true)
        //     .build();

        // driver.manage().deleteCookieNamed(AUTH_COOKIE_NAME);
        // driver.manage().addCookie(invalidCookie);
    }
}
