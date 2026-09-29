package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.ui.BranchSearchSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("Network validation - ATM filter")
public class BranchSearchNetworkTest extends BaseTest {
    BranchSearchSteps branchSearchSteps;

    @BeforeClass
    public void setUp() {
        branchSearchSteps = new BranchSearchSteps(page);
        branchSearchSteps.openBranchesPage();
    }

    @Test(priority = 1)
    @Description("SCRUM-T9 | Select the ATM tab and verify that a network request is sent")
    public void selectAtmTab() {
        branchSearchSteps.selectAtmTabAndCaptureRequest();
    }

    @Test(priority = 2)
    @Description("SCRUM-T9 | Verify that the captured request is sent to the expected endpoint with the correct method and a 200 response status")
    public void networkRequestIsValid() {
        branchSearchSteps.verifyRequestEndpointMethodAndStatus();
    }

    @Test(priority = 3)
    @Description("SCRUM-T9 | Verify that the request contains the ATM filter and that the number of items in the response matches the ATMs displayed on the UI")
    public void requestBodyAndUiMatch() {
        branchSearchSteps.verifyRequestBodyAndUi();
    }
}