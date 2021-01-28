package my.learning.seleniumtest.page.google.form;

import my.learning.seleniumtest.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailEstimateForm extends BasePage {

    public static final String EMAIL_FIELD_TOTAL_COST = "total_cost";

    private static Pattern totalCostPattern = Pattern.compile("[0-9.,]+");

    public static Map<String, String> parseEmail(WebDriver driver, WebElement emailContainer) {
        HashMap<String, String> results = new HashMap<>();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mobilepadding")));
        String totalCost = emailContainer.findElement(By.xpath(
            "//table[@class='quote']//tr[td/h3[text()='Total Estimated Monthly Cost']]/td[2]/h3")).getText();
        Matcher matcher = totalCostPattern.matcher(totalCost);
        if (matcher.find()) {
            results.put(EMAIL_FIELD_TOTAL_COST, matcher.group(0));
        }
        return Collections.unmodifiableMap(results);
    }

    @FindBy(id = "input_415")
    private WebElement emailInput;
    @FindBy(xpath = "//form[@name='emailForm']//button[contains(@class, 'md-primary')]")
    private WebElement submitButton;

    public EmailEstimateForm(WebDriver driver) {
        super(driver);
    }

    public void setEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void submit() {
        jsClick(submitButton);
    }
}
