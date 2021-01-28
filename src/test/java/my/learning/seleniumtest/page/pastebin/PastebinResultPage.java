package my.learning.seleniumtest.page.pastebin;

import my.learning.seleniumtest.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PastebinResultPage extends BasePage {

    public static PastebinResultPage createWhenReady(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("paste_code")));
        return new PastebinResultPage(driver);
    }

    @FindBy(id = "paste_code")
    private WebElement rawTextArea;

    public PastebinResultPage(WebDriver driver) {
        super(driver);
    }

    public void checkTitle(String expectedTitle) {
        String title = driver.getTitle();
        Assert.assertTrue(
            title != null && title.contains(expectedTitle),
            String.format("Error: title [%s] doesn't contain [%s]\n", title, expectedTitle));
    }

    public void checkText(String expectedText) {
        String text = rawTextArea.getText();
        Assert.assertTrue(
            text != null && text.equals(expectedText),
            String.format("Error: text [%s] doesn't match [%s]\n", text, expectedText)
        );
    }

    public void checkFormatting(CodeFormatting formatting) {
        Assert.assertFalse(
            driver.findElements(By.className(formatting.getCssClass())).isEmpty(),
            String.format("There is no element with [%s] formatting", formatting.getCssClass()));
    }
}
