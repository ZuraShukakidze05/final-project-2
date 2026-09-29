package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.api.RegionSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("TBC Bank API")
@Feature("ATMs and Branches - Regions")
public class RegionsApiTest {
    RegionSteps regionsSteps = new RegionSteps();

    @Test
    @Description("SCRUM-T7 | Send a GET request to the regions endpoint and verify that the response status is 200 and the data contains the expected regions and their details")
    public void regionsResponseContainsValidNestedData() {
        regionsSteps
                .getRegions(Constants.LANG_EN)
                .validateContainsRegions(Constants.CITY_TBILISI, Constants.CITY_BATUMI, Constants.CITY_KUTAISI)
                .validateRegionDetails();
    }
}