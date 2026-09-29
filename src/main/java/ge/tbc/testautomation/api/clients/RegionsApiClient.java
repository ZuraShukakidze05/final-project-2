package ge.tbc.testautomation.api.clients;

import ge.tbc.testautomation.constants.Constants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RegionsApiClient {

    public Response getRegions(String locale) {
        return given()
                .baseUri(Constants.API_BASE_URL)
                .queryParam(Constants.LOCALE_PARAM, locale)
                .filter(new AllureRestAssured())
                .log().uri()
                .when()
                .get(Constants.REGIONS_ENDPOINT)
                .then()
                .log().status()
                .extract().response();
    }
}