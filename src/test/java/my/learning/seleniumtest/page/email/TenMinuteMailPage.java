package my.learning.seleniumtest.page.email;

import my.learning.seleniumtest.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TenMinuteMailPage extends BasePage {

    @FindBy(id = "mailAddress")
    private WebElement emailInput;
    @FindBy(id = "messagesList")
    private WebElement inboxList;

    public TenMinuteMailPage(WebDriver driver) {
        super(driver);
    }

    public String getEmail() {
        return emailInput.getAttribute("value");
    }

    public void waitForEmail() {
        WebDriverWait wait = new WebDriverWait(driver, 10 * 60);
        wait.until(ExpectedConditions.visibilityOf(inboxList));
    }

    public WebElement selectEmailByIndex(int index) {
        WebElement emailHeader = inboxList.findElements(By.tagName("h3")).get(index);
        String emailBodyId = emailHeader.getAttribute("aria-controls");
        jsClick(emailHeader);
        return inboxList.findElement(By.id(emailBodyId));
    }
}
