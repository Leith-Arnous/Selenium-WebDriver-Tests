package my.learning.seleniumtest;

import my.learning.seleniumtest.page.TabManager;
import my.learning.seleniumtest.page.email.TenMinuteMailPage;
import my.learning.seleniumtest.page.google.GoogleCloudPage;
import my.learning.seleniumtest.page.google.PricingCalculatorPage;
import my.learning.seleniumtest.page.google.form.ComputeEngineForm;
import my.learning.seleniumtest.page.google.form.EmailEstimateForm;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MultiTabGoogleTests extends BaseGoogleTest {

    private TabManager tabManager;

    @Override
    protected String getPageUrl() {
        return null;
    }

    @BeforeMethod
    @Override
    public void setup() {
        super.setup();
        tabManager = new TabManager(driver);
    }

    @Test
    public void hardcore() {

        String googleTabHandle = tabManager.openInNewTab("https://cloud.google.com");
        GoogleCloudPage cloudPage = new GoogleCloudPage(driver);
        cloudPage.searchFor(SEARCH_QUERY);
        cloudPage.findSearchResult(SEARCH_QUERY).click();

        AtomicReference<String> calculatedCost = new AtomicReference<>();
        PricingCalculatorPage calculatorPage = PricingCalculatorPage.createWhenReady(driver);
        calculatorPage.useFrame(
            frame -> frame.selectComputeEngineTab(form -> {
                Map<String, String> results = fillSampleForm(form);
                calculatedCost.set(results.get(ComputeEngineForm.TOTAL));
            }));

        String mailTabHandle = tabManager.openInNewTab("https://10minutemail.com/");
        TenMinuteMailPage tenMinuteMailPage = new TenMinuteMailPage(driver);
        String email = tenMinuteMailPage.getEmail();

        tabManager.useTab(googleTabHandle);
        calculatorPage.useFrame(frame ->
            frame.showEmailDialog(form -> {
                form.setEmail(email);
                form.submit();
            }));

        tabManager.useTab(mailTabHandle);
        tenMinuteMailPage.waitForEmail();
        WebElement emailBody = tenMinuteMailPage.selectEmailByIndex(0);
        Map<String, String> results = EmailEstimateForm.parseEmail(driver, emailBody);

        validateFormResult(results, EmailEstimateForm.EMAIL_FIELD_TOTAL_COST, calculatedCost.get());
    }
}
