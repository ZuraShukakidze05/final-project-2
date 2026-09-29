package ge.tbc.testautomation.steps.api;

import ge.tbc.testautomation.api.clients.CurrencyExchangeApi;
import ge.tbc.testautomation.api.models.ExchangeRate;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CurrencyExchangeSteps {

    CurrencyExchangeApi currencyExchangeApi = new CurrencyExchangeApi();

    @Step("Send GET request to getExchangeRate endpoint and assert status 200")
    public Response sendGetExchangeRateRequestAndAssertStatus200(String iso1, String iso2) {
        Response response = currencyExchangeApi.getExchangeRate(iso1, iso2);
        assertThat(response.statusCode(), equalTo(200));
        return response;
    }

    @Step("Get exchange rate and validate deserialized fields")
    public ExchangeRate getExchangeRateAndValidateFields(String iso1, String iso2) {
        Response response = currencyExchangeApi.getExchangeRate(iso1, iso2);

        ExchangeRate rate = deserializeExchangeRate(response);
        validateExchangeRate(rate, iso1, iso2);

        return rate;
    }


    @Step("Deserialize response into ExchangeRate")
    private ExchangeRate deserializeExchangeRate(Response response) {
        return response.as(ExchangeRate.class);
    }

    @Step("Validate exchange rate fields")
    private void validateExchangeRate(ExchangeRate rate, String expectedIso1, String expectedIso2) {
        assertThat(rate.getIso1(), equalTo(expectedIso1));
        assertThat(rate.getIso2(), equalTo(expectedIso2));
        assertThat(rate.getBuyRate(), greaterThan(0.0));
        assertThat(rate.getSellRate(), greaterThan(0.0));
        assertThat(rate.getSellRate(), greaterThan(rate.getBuyRate()));
        assertThat(rate.getUpdateDate(), not(blankOrNullString()));
    }

    @Step("Send request to invalid endpoint {endpoint} and validate error")
    public Response sendRequestToInvalidEndpointAndValidateError(String endpoint, String iso1, String iso2) {
        Response response = currencyExchangeApi.getExchangeRateFromEndpoint(endpoint, iso1, iso2);
        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.asString(), emptyString());
        return response;
    }
}