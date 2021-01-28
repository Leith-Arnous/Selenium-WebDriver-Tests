package my.learning.seleniumtest.page.google;

import my.learning.seleniumtest.page.BasePage;
import my.learning.seleniumtest.page.google.form.ComputeEngineForm;
import my.learning.seleniumtest.page.google.form.EmailEstimateForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.function.Consumer;

public class PricingCalculatorFrame extends BasePage {

    @FindBy(xpath = "//md-tab-item[div/@title='Compute Engine']")
    private WebElement computeEngineTab;

    @FindBy(id = "email_quote")
    private WebElement emailEstimatesButton;

    public PricingCalculatorFrame(WebDriver driver) {
        super(driver);
    }

    public void selectComputeEngineTab(Consumer<ComputeEngineForm> callback) {
        jsClick(computeEngineTab);
        callback.accept(new ComputeEngineForm(driver));
    }

    public void showEmailDialog(Consumer<EmailEstimateForm> callback) {
        jsClick(emailEstimatesButton);
        callback.accept(new EmailEstimateForm(driver));
    }
}
