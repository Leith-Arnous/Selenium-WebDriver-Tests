package my.learning.seleniumtest.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class TabManager {

    private final WebDriver driver;

    private int tabCount = 0;

    public TabManager(WebDriver driver) {
        this.driver = driver;
    }

    public String openInNewTab(String url) {
        tabCount++;
        if (tabCount == 1) {
            driver.get(url);
            return driver.getWindowHandle();
        } else {
            ((JavascriptExecutor)driver).executeScript("window.open()");
            driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(tabCount - 1));
            driver.get(url);
            return driver.getWindowHandle();
        }
    }

    public void useTab(String handle) {
        driver.switchTo().window(handle);
    }
}
