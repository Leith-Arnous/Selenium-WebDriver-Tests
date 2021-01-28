package my.learning.seleniumtest;

import my.learning.seleniumtest.page.pastebin.CodeFormatting;
import my.learning.seleniumtest.page.pastebin.PastebinPage;
import my.learning.seleniumtest.page.pastebin.PastebinResultPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class PastebinTest extends BaseWebDriverTest {

    private static final String SAMPLE_CODE =
        "git config --global user.name  \"New Sheriff in Town\"\n" +
        "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
        "git push origin master --force";

    private static final String SAMPLE_TITLE = "how to gain dominance among developers";

    @Override
    protected String getPageUrl() {
        return "https://pastebin.com";
    }

    @Test
    public void ICanWin() {
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
}
