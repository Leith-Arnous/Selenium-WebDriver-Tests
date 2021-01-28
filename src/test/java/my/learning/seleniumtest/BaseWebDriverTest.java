package my.learning.seleniumtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseWebDriverTest {

    protected WebDriver driver;

    protected abstract String getPageUrl();

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        if (getPageUrl() != null) {
            driver.get(getPageUrl());
        }
    }

    @AfterMethod
    public void cleanup() {
        driver.quit();
    }
}
