package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.components.LanguageSwitcherComponent;

public class HomePage {
    public final HeaderComponent header;
    public final LanguageSwitcherComponent languageSwitcher;

    public HomePage(Page page) {
        header = new HeaderComponent(page);
        languageSwitcher = new LanguageSwitcherComponent(page);
    }
}