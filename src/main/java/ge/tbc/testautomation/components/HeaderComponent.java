package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HeaderComponent {
    public final Locator personalBtn,
            consumerLoanBtn,
            digitalBankButton,
            searchIcon,
            searchInput,
            noResultsMessage,
            searchResults;

    public HeaderComponent(Page page) {
        personalBtn = page.locator("a[href='/ka'] .tbcx-pw-navigation-item__link," +
                " a[href='/en'] .tbcx-pw-navigation-item__link");
        consumerLoanBtn = page.locator("a[href$='/consumer-loan']").first();
        digitalBankButton = page.locator(".tbcx-pw-header__actions__row--first button");
        searchIcon = page.locator(".tbcx-pw-search__button");
        searchInput = page.locator(".tbcx-text-input input");
        noResultsMessage = page.locator(".global-search__bottom-content__container__not-fount-result__title");
        searchResults = page.locator(".search-result-item");
    }

    public void hoverPersonal() {
        personalBtn.hover();
    }

    public void clickConsumerLoan() {
        consumerLoanBtn.click();
    }

    public void openSearch() {
        searchIcon.click();
    }

    public void typeSearchText(String text) {
        searchInput.fill(text);
    }

    public Locator resultsContaining(String text) {
        return searchResults.filter(new Locator.FilterOptions().setHasText(text));
    }
}