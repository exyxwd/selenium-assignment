import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;


public class StaticPage extends BasePage {
    public StaticPage(String url, WebDriver driver) {
        super(driver);
        driver.get(url);
    }

    public boolean bodyContains(String text) {
        return getBodyText().toLowerCase().contains(text.toLowerCase());
    }

    public List<WebElement> getVisibleImagesOutsideNav() {
        List<WebElement> imagesOutsideNav = driver.findElements(By.xpath("//img[not(ancestor::nav)]"));
        return imagesOutsideNav.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
    }

    public boolean hasVisibleImageOutsideNav() {
        return !getVisibleImagesOutsideNav().isEmpty();
    }

    public WebElement getBody() {
        WebElement bodyElement = waitAndReturnElement(By.tagName("body"));
        return bodyElement;
    }
}