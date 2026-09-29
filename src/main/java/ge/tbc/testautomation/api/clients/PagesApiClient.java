package ge.tbc.testautomation.api.clients;

import ge.tbc.testautomation.constants.Constants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PagesApiClient {

    public Response getPage(String pageId, String locale) {
        return given()
                .baseUri(Constants.API_BASE_URL)
                .queryParam(Constants.LOCALE_PARAM, locale)
                .pathParam(Constants.PAGE_ID_PATH_PARAM, pageId)
                .filter(new AllureRestAssured())
                .log().uri()
                .when()
                .get(Constants.PAGES_ENDPOINT + "{" + Constants.PAGE_ID_PATH_PARAM + "}");
    }
}