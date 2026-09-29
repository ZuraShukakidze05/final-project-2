package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LanguageSwitcherComponent {
    public final Locator switcher,
            htmlElement;

    public LanguageSwitcherComponent(Page page) {
        switcher = page.locator("tbcx-lang-switcher").first();
        htmlElement = page.locator("html");
    }

    public void switchLanguage() {
        switcher.click();
    }
}