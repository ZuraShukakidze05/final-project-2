package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.LocalizationDataProvider;
import ge.tbc.testautomation.steps.ui.CurrencyExchangeSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("Currency exchange - Localization")
public class CurrencyExchangeLocalizationTest extends BaseTest {
    CurrencyExchangeSteps currencyExchangeSteps;

    @BeforeMethod
    public void setUp() {
        currencyExchangeSteps = new CurrencyExchangeSteps(page);
    }

    @Test(priority = 1)
    @Description("SCRUM-T4 | Navigate to the currency exchange page from the navigation menu and verify that the page opens")
    public void goToCurrencyExchangePage() {
        currencyExchangeSteps.openFromNavigation();
    }

    @Test(priority = 2, dataProvider = "currencyExchangeLocalizationData",
            dataProviderClass = LocalizationDataProvider.class)
    @Description("SCRUM-T4 | Switch the page language between Georgian and English and verify that the title and buy/sell labels match the expected values")
    public void verifyLocalization(String langCode, String expectedTitle,
                                   String expectedBuyLabel, String expectedSellLabel) {
        currencyExchangeSteps
                .switchLanguage()
                .verifyLocalization(langCode, expectedTitle, expectedBuyLabel, expectedSellLabel);
    }
}