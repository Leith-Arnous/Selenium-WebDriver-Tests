package my.learning.seleniumtest;

import my.learning.seleniumtest.page.google.GoogleCloudPage;
import my.learning.seleniumtest.page.google.PricingCalculatorPage;
import org.testng.annotations.Test;

import java.util.Map;

public class GoogleTests extends BaseGoogleTest {

    @Override
    protected String getPageUrl() {
        return "https://cloud.google.com";
    }

    @Test
    public void hurtMePlenty() {
        GoogleCloudPage cloudPage = new GoogleCloudPage(driver);
        cloudPage.searchFor(SEARCH_QUERY);
        cloudPage.findSearchResult(SEARCH_QUERY).click();

        PricingCalculatorPage calculatorPage = PricingCalculatorPage.createWhenReady(driver);
        calculatorPage.useFrame(frame ->
            frame.selectComputeEngineTab(form -> {
                Map<String, String> results = fillSampleForm(form);
                validateSampleForm(results);
            }));
    }
}
