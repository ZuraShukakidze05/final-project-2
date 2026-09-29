package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NavigationComponent {
    public final Locator moreOptionsButton,
            currencyExchangeButton;

    public NavigationComponent(Page page) {
        moreOptionsButton = page.locator("app-quick-action-button[tbcxpwtogglecontent] button");
        currencyExchangeButton = page.locator("a[href$='/valutis-kursi']").last();
    }

    public void openMoreOptions() {
        moreOptionsButton.click();
    }

    public void openCurrencyExchange() {
        currencyExchangeButton.click();
    }
}