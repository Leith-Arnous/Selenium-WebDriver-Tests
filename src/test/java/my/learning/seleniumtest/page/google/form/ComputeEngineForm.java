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
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComputeEngineForm extends BasePage {

    public static class ValidatableValue {
        private String optionId;
        private String readableValue;

        public ValidatableValue(String optionId, String readableValue) {
            this.optionId = optionId;
            this.readableValue = readableValue;
        }

        public String getOptionId() {
            return optionId;
        }

        public String getReadableValue() {
            return readableValue;
        }
    }

    public static class ValidatableField {
        private Map<String, ValidatableValue> values;
        private String readableName;
        private WebElement select;

        public ValidatableField(String readableName, WebElement select, ValidatableValue... values) {
            this.values = Stream.of(values).collect(
                Collectors.toUnmodifiableMap(ValidatableValue::getOptionId, v -> v));
            this.readableName = readableName;
            this.select = select;
        }

        public WebElement getSelect() {
            return select;
        }

        public Map<String, ValidatableValue> getValues() {
            return values;
        }

        public String getReadableName() {
            return readableName;
        }
    }

    public static class ComputeFields {
        public static final String MACHINE_CLASS = "MACHINE_CLASS";
        public static final String INSTANCE_TYPE = "INSTANCE_TYPE";
        public static final String DATA_CENTER_LOCATION = "DATA_CENTER_LOCATION";
        public static final String SSD = "SSD";
        public static final String COMMITTED_TERM = "COMMITTED_TERM";

        public static class MachineClass {
            public static final String REGULAR = "select_option_67";
        }

        public static class InstanceType {
            public static final String N1_STANDARD_8 = "select_option_217";
        }

        public static class DataCenterLocation {
            public static final String EUROPE_WEST_3 = "select_option_185";
        }

        public static class Ssd {
            public static final String OPTION_2x375 = "select_option_171";
        }

        public static class CommittedTerm {
            public static final String YEAR_1 = "select_option_83";
        }
    }

    public static final String TOTAL = "TOTAL";
    public static final String OS_OPTION_FREE = "select_option_55";
    public static final String GPU_TYPE_TESLA_V100 = "select_option_360";

    @FindBy(name = "quantity")
    private WebElement instanceCountInput;
    @FindBy(id = "select_65")
    private WebElement osTypeInput;
    @FindBy(id = "select_69")
    private WebElement machineClassInput;
    @FindBy(id = "select_76")
    private WebElement instanceTypeInput;
    @FindBy(xpath = "//md-checkbox[@aria-label='Add GPUs']")
    private WebElement addGpusCheckbox;
    @FindBy(id = "select_348")
    private WebElement numberOfGpusInput;
    @FindBy(id = "select_350")
    private WebElement gpuTypeInput;
    @FindBy(id = "select_78")
    private WebElement ssdInput;
    @FindBy(id = "select_80")
    private WebElement dataCenterLocationInput;
    @FindBy(id = "select_85")
    private WebElement committedUsageInput;
    @FindBy(xpath = "//form[@name='ComputeEngineForm']//button[contains(@class, 'md-primary')]")
    private WebElement instancesSectionSubmit;

    private final Pattern totalPattern = Pattern.compile("([0-9,.]+) per");
    private final WebDriverWait selectClickWait = new WebDriverWait(driver, 10);

    private final Map<String, ValidatableField> validatableFields;

    public ComputeEngineForm(WebDriver driver) {
        super(driver);

        validatableFields = Collections.unmodifiableMap(Map.of(
            ComputeFields.MACHINE_CLASS, new ValidatableField(
                "VM class", machineClassInput,
                new ValidatableValue(ComputeFields.MachineClass.REGULAR, "regular")),
            ComputeFields.INSTANCE_TYPE, new ValidatableField(
                "Instance type", instanceTypeInput,
                new ValidatableValue(ComputeFields.InstanceType.N1_STANDARD_8, "n1-standard-8")),
            ComputeFields.DATA_CENTER_LOCATION, new ValidatableField(
                "Region", dataCenterLocationInput,
                new ValidatableValue(ComputeFields.DataCenterLocation.EUROPE_WEST_3, "Frankfurt")),
            ComputeFields.SSD, new ValidatableField(
                "Total available local SSD space", ssdInput,
                new ValidatableValue(ComputeFields.Ssd.OPTION_2x375, "2x375 GB")),
            ComputeFields.COMMITTED_TERM, new ValidatableField(
                "Commitment term", committedUsageInput,
                new ValidatableValue(ComputeFields.CommittedTerm.YEAR_1, "1 Year"))
        ));
    }

    public Map<String, ValidatableField> getValidatableFields() {
        return validatableFields;
    }

    public void setValidatableField(String fieldId, String valueId) {
        ValidatableField field = validatableFields.get(fieldId);
        jsClick(angularShowOptions(field.getSelect(), By.id(valueId)));
    }

    private WebElement angularShowOptions(WebElement input, By optionLocator) {
        jsClick(input);
        selectClickWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionLocator));
        return driver.findElement(optionLocator);
    }

    private void angularSelectOption(WebElement select, String optionId) {
        jsClick(angularShowOptions(select, By.id(optionId)));
    }

    public void setInstanceCount(int count) {
        instanceCountInput.sendKeys(String.valueOf(count));
    }

    public void setOsType(String optionId) {
        angularSelectOption(osTypeInput, optionId);
    }

    public void addGpus() {
        angularShowOptions(
            addGpusCheckbox,
            By.xpath("//div[@ng-if='listingCtrl.computeServer.addGPUs']"));
    }

    public void setGpuCount(int count) {
        jsClick(angularShowOptions(
            numberOfGpusInput,
            By.xpath(String.format("//div[@id='select_container_349']//md-option[@value='%d']", count))));
    }

    public void setGpuType(String optionId) {
        angularSelectOption(gpuTypeInput, optionId);
    }

    public Map<String, String> submitInstancesSection() {
        jsClick(instancesSectionSubmit);
        List<WebElement> lines = driver.findElements(
                By.xpath("//md-content[@id='compute']//md-list-item/div"));
        HashMap<String, String> results = new HashMap<>();
        for (WebElement line : lines) {
            String text = line.getText();
            for (Map.Entry<String, ValidatableField> fieldPair : validatableFields.entrySet()) {
                if (text.startsWith(fieldPair.getValue().getReadableName())) {
                    for (Map.Entry<String, ValidatableValue> valuePair : fieldPair.getValue().getValues().entrySet()) {
                        if (text.endsWith(valuePair.getValue().getReadableValue())) {
                            results.put(fieldPair.getKey(), valuePair.getKey());
                        }
                    }
                }
            }
        }
        String totalText = driver.findElement(By.xpath(
                "//h2/b[contains(text(),'Total Estimated Cost')]")).getText();
            Matcher matcher = totalPattern.matcher(totalText);
            if (matcher.find()) {
            results.put(TOTAL, matcher.group(1));
        }
        return Collections.unmodifiableMap(results);
    }
}
