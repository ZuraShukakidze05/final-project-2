package ge.tbc.testautomation.steps.ui;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.pages.HomePage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageSteps {
    HomePage homePage;

    public HomePageSteps(Page page) {
        homePage = new HomePage(page);
    }

    @Step("Verify language switcher is visible")
    public HomePageSteps verifySwitcherVisible() {
        assertThat(homePage.languageSwitcher.switcher).isVisible();
        return this;
    }

    @Step("Click language switcher")
    public HomePageSteps clickSwitcher() {
        assertThat(homePage.languageSwitcher.switcher).isVisible();
        homePage.languageSwitcher.switchLanguage();
        return this;
    }

    @Step("Verify language switched to: {langCode}")
    public HomePageSteps verifyLanguageSwitch(String langCode, String expectedDigitalBankText) {
        assertThat(homePage.languageSwitcher.htmlElement).hasAttribute(Constants.LANG_ATTRIBUTE, langCode);
        assertThat(homePage.header.digitalBankButton).containsText(expectedDigitalBankText);
        return this;
    }

    @Step("Click on the search icon")
    public HomePageSteps clickOnSearchIcon() {
        assertThat(homePage.header.searchIcon).isVisible();
        homePage.header.openSearch();
        assertThat(homePage.header.searchInput).isVisible();
        return this;
    }

    @Step("Enter search text: {text}")
    public HomePageSteps enterSearchText(String text) {
        homePage.header.typeSearchText(text);
        return this;
    }

    @Step("Verify search results contain: {searchText}")
    public HomePageSteps verifySearchResults(String searchText) {
        assertThat(homePage.header.searchResults.first()).isVisible();
        assertThat(homePage.header.resultsContaining(searchText).first()).isVisible();
        return this;
    }

    @Step("Verify no results for empty search text")
    public HomePageSteps verifyNoResultsForEmptySearchText() {
        assertThat(homePage.header.searchResults).hasCount(0);
        return this;
    }

    @Step("Verify no results message is displayed")
    public HomePageSteps verifyNoResultsMessage() {
        assertThat(homePage.header.noResultsMessage).isVisible();
        assertThat(homePage.header.noResultsMessage).containsText(Constants.NO_RESULTS_MESSAGE);
        return this;
    }
}