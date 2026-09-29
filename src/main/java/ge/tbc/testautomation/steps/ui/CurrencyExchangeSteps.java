package ge.tbc.testautomation.steps.ui;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.pages.CurrencyExchangePage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CurrencyExchangeSteps {
    CurrencyExchangePage currencyExchangePage;

    public CurrencyExchangeSteps(Page page) {
        currencyExchangePage = new CurrencyExchangePage(page);
    }

    @Step("Open currency exchange page from the navigation menu")
    public CurrencyExchangeSteps openFromNavigation() {
        assertThat(currencyExchangePage.navigation.moreOptionsButton).isVisible();
        currencyExchangePage.navigation.openMoreOptions();
        assertThat(currencyExchangePage.navigation.currencyExchangeButton).isVisible();
        currencyExchangePage.navigation.openCurrencyExchange();
        return this;
    }

    @Step("Click language switcher")
    public CurrencyExchangeSteps switchLanguage() {
        assertThat(currencyExchangePage.languageSwitcher.switcher).isVisible();
        currencyExchangePage.languageSwitcher.switchLanguage();
        return this;
    }

    @Step("Verify page is localized to '{langCode}' with title '{expectedTitle}'")
    public CurrencyExchangeSteps verifyLocalization(String langCode, String expectedTitle,
                                                    String expectedBuyLabel, String expectedSellLabel) {
        assertThat(currencyExchangePage.languageSwitcher.htmlElement)
                .hasAttribute(Constants.LANG_ATTRIBUTE, langCode);
        assertThat(currencyExchangePage.mainTitle).containsText(expectedTitle);
        assertThat(currencyExchangePage.buyLabel).containsText(expectedBuyLabel);
        assertThat(currencyExchangePage.sellLabel).containsText(expectedSellLabel);
        return this;
    }
}