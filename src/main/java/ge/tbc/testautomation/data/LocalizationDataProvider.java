package ge.tbc.testautomation.data;

import org.testng.annotations.DataProvider;
import ge.tbc.testautomation.constants.Constants;

public class LocalizationDataProvider {

    @DataProvider(name = "currencyExchangeLocalizationData")
    public static Object[][] currencyExchangeLocalizationData() {
        return new Object[][]{
                {Constants.LANG_EN, Constants.CURRENCY_TITLE_EN, Constants.BUY_LABEL_EN, Constants.SELL_LABEL_EN},
                {Constants.LANG_KA, Constants.CURRENCY_TITLE_KA, Constants.BUY_LABEL_KA, Constants.SELL_LABEL_KA}
        };
    }
}