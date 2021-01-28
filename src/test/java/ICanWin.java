import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class ICanWin {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://pastebin.com");
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
        driver.quit();
    }
}
