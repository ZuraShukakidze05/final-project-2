package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.components.LanguageSwitcherComponent;
import ge.tbc.testautomation.components.NavigationComponent;

public class CurrencyExchangePage {
    public final HeaderComponent header;
    public final NavigationComponent navigation;
    public final LanguageSwitcherComponent languageSwitcher;
    public final Locator mainTitle,
            sellLabel,
            buyLabel;

    public CurrencyExchangePage(Page page) {
        header = new HeaderComponent(page);
        navigation = new NavigationComponent(page);
        languageSwitcher = new LanguageSwitcherComponent(page);

        mainTitle = page.locator("tbcx-pw-popular-currencies .tbcx-pw-popular-currencies__main-title");
        sellLabel = page.locator("label[for='sell-amount']");
        buyLabel = page.locator("label[for='buy-amount']");
    }
}