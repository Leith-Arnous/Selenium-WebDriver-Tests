package my.learning.seleniumtest.page.google;

import my.learning.seleniumtest.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleCloudPage extends BasePage {

    @FindBy(name = "q")
    private WebElement searchInput;

    public GoogleCloudPage(WebDriver driver) {
        super(driver);
    }

    public void searchFor(String searchQuery) {
        searchInput.click();
        searchInput.sendKeys(searchQuery);
        searchInput.submit();
    }

    public WebElement findSearchResult(String title) {
        return driver.findElement(By.linkText(title));
    }
}
