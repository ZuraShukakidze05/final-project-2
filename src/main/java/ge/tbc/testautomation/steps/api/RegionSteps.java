package ge.tbc.testautomation.steps.api;

import ge.tbc.testautomation.api.clients.RegionsApiClient;
import ge.tbc.testautomation.api.models.Point;
import ge.tbc.testautomation.api.models.Region;
import ge.tbc.testautomation.constants.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RegionSteps {

    private final RegionsApiClient regionsApiClient = new RegionsApiClient();
    private List<Region> regions;

    @Step("Get regions for locale {locale} and assert status 200")
    public RegionSteps getRegions(String locale) {
        Response response = regionsApiClient.getRegions(locale);
        assertThat(response.statusCode(), equalTo(Constants.HTTP_OK));
        regions = Arrays.asList(response.as(Region[].class));
        return this;
    }

    @Step("Validate region list contains expected cities")
    public RegionSteps validateContainsRegions(String... expectedNames) {
        assertThat(regions, not(empty()));
        assertThat(regions.stream().map(Region::getName).toList(), hasItems(expectedNames));
        return this;
    }

    @Step("Validate every region has a name, a positive count and consistent nested coordinates")
    public RegionSteps validateRegionDetails() {
        for (Region r : regions) {
            assertThat(r.getName(), not(blankOrNullString()));
            assertThat(r.getName() + Constants.REGION_COUNT_MESSAGE, r.getCount(), greaterThan(0));
            assertThat(r.getName() + Constants.REGION_COORDINATES_MESSAGE, r.getCoordinates(), notNullValue());

            Point ne = r.getCoordinates().getNorthEast();
            Point sw = r.getCoordinates().getSouthWest();
            assertThat(r.getName() + Constants.REGION_NORTH_EAST_MESSAGE, ne, notNullValue());
            assertThat(r.getName() + Constants.REGION_SOUTH_WEST_MESSAGE, sw, notNullValue());

            assertThat(r.getName(), ne.getLatitude(), greaterThanOrEqualTo(sw.getLatitude()));
            assertThat(r.getName(), ne.getLongitude(), greaterThanOrEqualTo(sw.getLongitude()));

            assertThat(r.getName(), ne.getLatitude(),
                    allOf(greaterThan(Constants.GEORGIA_LAT_MIN), lessThan(Constants.GEORGIA_LAT_MAX)));
            assertThat(r.getName(), ne.getLongitude(),
                    allOf(greaterThan(Constants.GEORGIA_LON_MIN), lessThan(Constants.GEORGIA_LON_MAX)));
        }
        return this;
    }
}