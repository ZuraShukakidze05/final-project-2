package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.components.LanguageSwitcherComponent;
import ge.tbc.testautomation.constants.Constants;

public class LoansPage {
    private final Page page;
    public final HeaderComponent header;
    public final LanguageSwitcherComponent languageSwitcher;
    public final Locator termsBtn,
            termsTab,
            itemCaptions,
            itemTitles,
            amountItem,
            monthItem,
            monthlyPaymentResult,
            pageTitle;

    public LoansPage(Page page) {
        this.page = page;
        this.header = new HeaderComponent(page);
        this.languageSwitcher = new LanguageSwitcherComponent(page);

        termsBtn = page.locator("a[href$='/digital']");
        termsTab = page.locator(
                ".tbcx-pw-tab-menu__item:has-text('Terms'), " +
                        ".tbcx-pw-tab-menu__item:has-text('პირობები')");
        itemCaptions = page.locator(".tbcx-pw-details-list__item-caption");
        itemTitles = page.locator(".tbcx-pw-details-list__item-title");
        amountItem = page.locator(".tbcx-pw-calculator-form__input:first-of-type input");
        monthItem = page.locator(".tbcx-pw-calculator-form__input:last-of-type input");
        monthlyPaymentResult = page.locator("div.tbcx-pw-calculated-info__top-title .tbcx-pw-calculated-info__number--new");
        pageTitle = page.locator("h1").first();
    }

    public Locator textItem(String text) {
        return page.getByText(text).first();
    }

    public void openBySlug(String slug) {
        page.navigate(Constants.BASE_URL + "/" + Constants.LANG_KA + slug);
    }

    public void openTerms() {
        termsBtn.click();
    }

    public void openTermsTab() {
        termsTab.click();
    }

    public void enterAmount(String value) {
        amountItem.fill(value);
    }

    public void enterMonths(String value) {
        monthItem.fill(value);
    }
}