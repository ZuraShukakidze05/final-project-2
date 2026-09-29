package ge.tbc.testautomation.api.clients;

import ge.tbc.testautomation.constants.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CurrencyExchangeApi {

    public Response getExchangeRate(String iso1, String iso2) {
        return getExchangeRateFromEndpoint(Constants.GET_EXCHANGE_RATE_ENDPOINT, iso1, iso2);
    }

    public Response getExchangeRateFromEndpoint(String endpoint, String iso1, String iso2) {
        return given()
                .spec(BaseConfiguration.exchangeRateApiSpec())
                .queryParam(Constants.ISO1_PARAM, iso1)
                .queryParam(Constants.ISO2_PARAM, iso2)
                .when()
                .get(endpoint);
    }
}