package my.learning.seleniumtest.page.google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Consumer;

public class PricingCalculatorPage {

    public static PricingCalculatorPage createWhenReady(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("myFrame")));
        return new PricingCalculatorPage(driver);
    }

    private final WebDriver driver;

    public PricingCalculatorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void useFrame(Consumer<PricingCalculatorFrame> callback) {
        driver.switchTo().frame("myFrame");
        callback.accept(new PricingCalculatorFrame(driver));
        driver.switchTo().defaultContent();
    }
}
