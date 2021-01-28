package my.learning.seleniumtest.page.pastebin;

import my.learning.seleniumtest.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PastebinPage extends BasePage {

    private static final String EXPIRATION_TIME_TEN_MINUTES = "10M";

    @FindBy(className = "new_paste_button")
    private WebElement newPasteButton;
    @FindBy(name = "paste_name")
    private WebElement pasteNameInput;
    @FindBy(className = "paste_textarea")
    private WebElement codeTextArea;
    @FindBy(id = "submit")
    private WebElement submitButton;

    private final By expirationDateLocator = By.name("paste_expire_date");
    private final By pasteFormatLocator = By.name("paste_format");

    private Select getSelect(By locator) {
        return new Select(driver.findElement(locator));
    }

    public PastebinPage(WebDriver driver) {
        super(driver);
    }

    public void fillInTenMinutePaste(String title, String text) {
        newPasteButton.click();
        codeTextArea.sendKeys(text);
        setExpiration(EXPIRATION_TIME_TEN_MINUTES);
        pasteNameInput.sendKeys(title);
    }

    public PastebinResultPage completePaste() {
        submitButton.click();
        return PastebinResultPage.createWhenReady(driver);
    }


    public void setFormatting(CodeFormatting formatting) {
        getSelect(pasteFormatLocator).selectByValue(formatting.getValue());
    }


    private void setExpiration(String expirationTimeValue) {
        getSelect(expirationDateLocator).selectByValue(expirationTimeValue);
    }
}
