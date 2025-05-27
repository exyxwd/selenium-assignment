import java.net.URL;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Map;
import java.util.HashMap;


public class TestBase {

    public WebDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        remoteDriver.setFileDetector(new LocalFileDetector());
        driver = remoteDriver;
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}