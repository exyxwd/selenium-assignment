import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class ProfileSettingsPage extends BasePage {

    private final By localityField = By.id("Locality");
    private final By descriptionField = By.id("Description");
    private final By profilePictureInput = By.id("ProfilePicture");
    private final By participantCheckbox = By.id("Participant");
    private final By sendButton = By.xpath("//button[@type='submit']");
    private final By successMessage = By.xpath("//p[contains(@class, 'valid-creds-text')]");

    public ProfileSettingsPage(WebDriver driver) {
        super(driver);
    }

    public void goTo() {
        driver.get("https://gis.inf.elte.hu/tiszta-tisza/dashboard/profileEditor");
    }

    public void enterLocality(String city) {
        WebElement localityInput = waitAndReturnElement(localityField);
        localityInput.clear();
        localityInput.sendKeys(city);
    }

    public void enterDescription(String desc) {
        WebElement descriptionInput = waitAndReturnElement(descriptionField);
        descriptionInput.clear();
        descriptionInput.sendKeys(desc);
    }


    public void uploadProfilePicture(String filePath) {
        waitAndReturnElement(profilePictureInput).sendKeys(filePath);
    }

    public void setParticipated(boolean participated) {
        WebElement checkbox = waitAndReturnElement(participantCheckbox);
        if (checkbox.isSelected() != participated) {
            checkbox.click();
        }
    }

    public void clickSend() {
        waitAndReturnElement(sendButton).click();
    }

    public String getSuccessMessage() {
        return waitAndReturnElement(successMessage).getText();
    }

    public void toggleParticipantNotification() {
        WebElement checkbox = waitAndReturnElement(participantCheckbox);
        checkbox.click();
    }
}
