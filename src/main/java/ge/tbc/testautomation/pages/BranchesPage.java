package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.components.LanguageSwitcherComponent;
import ge.tbc.testautomation.constants.Constants;

public class BranchesPage {
    private final Page page;
    public final HeaderComponent header;
    public final LanguageSwitcherComponent languageSwitcher;
    public final Locator branchResultsList,
            tabMenuButtons;

    public BranchesPage(Page page) {
        this.page = page;
        header = new HeaderComponent(page);
        languageSwitcher = new LanguageSwitcherComponent(page);

        branchResultsList = page.locator("app-atm-branches-section-list-item");
        tabMenuButtons = page.locator("tbcx-pw-tab-menu button.tbcx-pw-tab-menu__item");
    }

    public Locator getTabByName(String name) {
        return tabMenuButtons.filter(new Locator.FilterOptions().setHasText(name));
    }

    public void open() {
        page.navigate(Constants.BASE_URL + "/" + Constants.LANG_KA + Constants.BRANCHES_PAGE_PATH);
    }

    public void clickTab(String name) {
        getTabByName(name).click();
    }
}