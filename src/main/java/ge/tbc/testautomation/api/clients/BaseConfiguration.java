package ge.tbc.testautomation.api.clients;

import ge.tbc.testautomation.constants.Constants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public class BaseConfiguration {

    public static RequestSpecification exchangeRateApiSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Constants.API_BASE_URL + Constants.EXCHANGE_RATES_BASE_PATH)
                .log(LogDetail.ALL)
                .addFilter(new ErrorLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }
}