package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.api.CurrencyExchangeSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("TBC Bank API")
@Feature("Currency Exchange Rate")
public class CurrencyExchangeApiTest {
    CurrencyExchangeSteps currencyExchangeSteps = new CurrencyExchangeSteps();

    @Test
    @Description("SCRUM-T6 | Send a GET request to the currency exchange rate endpoint and verify that the response status is 200")
    public void getRequestToExchangeRateEndpointReturnsStatus200() {
        currencyExchangeSteps.sendGetExchangeRateRequestAndAssertStatus200(
                Constants.FROM_CURRENCY_USD, Constants.TO_CURRENCY_GEL);
    }

    @Test
    @Description("SCRUM-T6 | Deserialize the response into a POJO class and verify that the currency list is not empty and each currency has a buy/sell rate")
    public void deserializeResponseAndValidateFields() {
        currencyExchangeSteps.getExchangeRateAndValidateFields(
                Constants.FROM_CURRENCY_USD, Constants.TO_CURRENCY_GEL);
    }
}