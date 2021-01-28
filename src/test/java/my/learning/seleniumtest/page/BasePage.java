package my.learning.seleniumtest.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()", element);
    }
}
