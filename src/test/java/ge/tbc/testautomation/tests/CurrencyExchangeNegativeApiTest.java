package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.api.CurrencyExchangeSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("TBC Bank API")
@Feature("Currency Exchange Rate - Negative")
public class CurrencyExchangeNegativeApiTest {
    CurrencyExchangeSteps currencyExchangeSteps = new CurrencyExchangeSteps();

    @Test
    @Description("SCRUM-T10 | Send a GET request to a non-existing endpoint and verify that the response status is 404")
    public void invalidEndpointReturnsNotFound() {
        currencyExchangeSteps.sendRequestToInvalidEndpointAndValidateError(
                Constants.INVALID_ENDPOINT,
                Constants.FROM_CURRENCY_USD,
                Constants.TO_CURRENCY_GEL);
    }
}