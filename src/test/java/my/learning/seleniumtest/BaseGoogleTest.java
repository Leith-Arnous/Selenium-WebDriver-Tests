package my.learning.seleniumtest;

import my.learning.seleniumtest.page.google.form.ComputeEngineForm;
import org.testng.Assert;

import java.util.Map;

public abstract class BaseGoogleTest extends BaseWebDriverTest {

    public static final String SEARCH_QUERY = "Google Cloud Platform Pricing Calculator";

    public static final String SAMPLE_TOTAL = "1,082.77";

    Map<String, String> fillSampleForm(ComputeEngineForm form) {
        form.setInstanceCount(4);
        form.setOsType(ComputeEngineForm.OS_OPTION_FREE);
        form.setValidatableField(
            ComputeEngineForm.ComputeFields.MACHINE_CLASS,
            ComputeEngineForm.ComputeFields.MachineClass.REGULAR);
        form.setValidatableField(
            ComputeEngineForm.ComputeFields.INSTANCE_TYPE,
            ComputeEngineForm.ComputeFields.InstanceType.N1_STANDARD_8);
        form.addGpus();
        form.setGpuCount(1);
        form.setGpuType(ComputeEngineForm.GPU_TYPE_TESLA_V100);
        form.setValidatableField(
            ComputeEngineForm.ComputeFields.SSD,
            ComputeEngineForm.ComputeFields.Ssd.OPTION_2x375);
        form.setValidatableField(
            ComputeEngineForm.ComputeFields.DATA_CENTER_LOCATION,
            ComputeEngineForm.ComputeFields.DataCenterLocation.EUROPE_WEST_3);
        form.setValidatableField(
            ComputeEngineForm.ComputeFields.COMMITTED_TERM,
            ComputeEngineForm.ComputeFields.CommittedTerm.YEAR_1);
        return form.submitInstancesSection();
    }

    static void validateSampleForm(Map<String, String> results) {
        validateFormResult(results,
            ComputeEngineForm.ComputeFields.MACHINE_CLASS,
            ComputeEngineForm.ComputeFields.MachineClass.REGULAR);
        validateFormResult(results,
            ComputeEngineForm.ComputeFields.INSTANCE_TYPE,
            ComputeEngineForm.ComputeFields.InstanceType.N1_STANDARD_8);
        validateFormResult(results,
            ComputeEngineForm.ComputeFields.SSD,
            ComputeEngineForm.ComputeFields.Ssd.OPTION_2x375);
        validateFormResult(results,
            ComputeEngineForm.ComputeFields.DATA_CENTER_LOCATION,
            ComputeEngineForm.ComputeFields.DataCenterLocation.EUROPE_WEST_3);
        validateFormResult(results,
            ComputeEngineForm.ComputeFields.COMMITTED_TERM,
            ComputeEngineForm.ComputeFields.CommittedTerm.YEAR_1);
        validateFormResult(results, ComputeEngineForm.TOTAL, SAMPLE_TOTAL);
    }

    static void validateFormResult(Map<String, String> results, String fieldId, String valueId) {
        Assert.assertTrue(results.containsKey(fieldId));
        Assert.assertEquals(results.get(fieldId), valueId);
    }
}
