package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.ui.HomePageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("Header search")
public class SearchFunctionalityTest extends BaseTest {
    HomePageSteps homePageSteps;

    @BeforeClass
    public void setUp() {
        homePageSteps = new HomePageSteps(page);
    }

    @Test(priority = 1)
    @Description("SCRUM-T11 | Search with valid text and verify that matching results are displayed")
    public void searchWithValidText() {
        homePageSteps
                .clickOnSearchIcon()
                .enterSearchText(Constants.VALID_SEARCH_TEXT)
                .verifySearchResults(Constants.VALID_SEARCH_TEXT);
    }

    @Test(priority = 2)
    @Description("SCRUM-T11 | Search with invalid text and verify that a 'no results found' message is displayed")
    public void searchWithInvalidText() {
        homePageSteps
                .enterSearchText(Constants.INVALID_SEARCH_TEXT)
                .verifyNoResultsMessage();
    }

    @Test(priority = 3)
    @Description("SCRUM-T11 | Clear the search text and verify that no results are displayed")
    public void searchWithEmptyText() {
        homePageSteps
                .enterSearchText(Constants.EMPTY_SEARCH_TEXT)
                .verifyNoResultsForEmptySearchText();
    }
}