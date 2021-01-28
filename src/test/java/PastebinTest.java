import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.CodeFormatting;
import page.PastebinPage;
import page.PastebinResultPage;

public class PastebinTest {

    private static final String SAMPLE_CODE =
        "git config --global user.name  \"New Sheriff in Town\"\n" +
        "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
        "git push origin master --force";

    private static final String SAMPLE_TITLE = "how to gain dominance among developers";

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://pastebin.com");
    }

    @Test
    public void ICanWinSimple() {
        WebElement newPaste = driver.findElement(By.className("new_paste_button"));
        newPaste.click();
        WebElement pasteCode = driver.findElement(By.className("paste_textarea"));
        pasteCode.sendKeys("Hello from WebDriver");
        Select pasteExpiration = new Select(driver.findElement(By.name("paste_expire_date")));
        pasteExpiration.selectByValue("10M");
        WebElement pastName = driver.findElement(By.name("paste_name"));
        pastName.sendKeys("helloweb");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        System.out.println("Paste created: " + driver.getCurrentUrl());
    }

    @Test
    public void ICanWinUpdated() {
        PastebinPage page = new PastebinPage(driver);
        page.fillInTenMinutePaste("helloweb", "Hello from WebDriver");
        page.completePaste();
    }

    @Test
    public void BringItOn() {
        PastebinPage page = new PastebinPage(driver);
        page.fillInTenMinutePaste(SAMPLE_TITLE, SAMPLE_CODE);
        page.setFormatting(CodeFormatting.BASH);
        PastebinResultPage createdPage = page.completePaste();
        createdPage.checkTitle(SAMPLE_TITLE);
        createdPage.checkText(SAMPLE_CODE);
        createdPage.checkFormatting(CodeFormatting.BASH);
    }

    @AfterTest
    public void cleanup() {
        driver.quit();
    }
}
