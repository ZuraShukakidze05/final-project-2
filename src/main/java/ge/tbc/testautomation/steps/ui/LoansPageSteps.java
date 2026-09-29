package ge.tbc.testautomation.steps.ui;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.pages.LoansPage;
import ge.tbc.testautomation.utils.LoanCalculationUtils;
import ge.tbc.testautomation.utils.TextUtils;
import io.qameta.allure.Step;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoansPageSteps {

    LoansPage loanPage;

    public LoansPageSteps(Page page) {
        loanPage = new LoansPage(page);
    }

    @Step("Open consumer loan page from the header menu")
    public LoansPageSteps openConsumerLoanFromMenu() {
        assertThat(loanPage.header.personalBtn).isVisible();
        loanPage.header.hoverPersonal();
        assertThat(loanPage.header.consumerLoanBtn).isVisible();
        loanPage.header.clickConsumerLoan();
        return this;
    }

    @Step("Click on the consumer loan terms button")
    public LoansPageSteps clickConsumerLoanTerms() {
        assertThat(loanPage.termsBtn).isVisible();
        loanPage.openTerms();
        return this;
    }

    @Step("Click on the consumer loan terms tab")
    public LoansPageSteps clickConsumerLoanTermsTab() {
        assertThat(loanPage.termsTab).isVisible();
        loanPage.openTermsTab();
        return this;
    }

    @Step("Validate loan terms captions list")
    public LoansPageSteps validateCaptionsList() {
        List<String> actualCaptions = TextUtils.getNormalizedTexts(loanPage.itemCaptions);
        assertEquals(actualCaptions, Constants.LOAN_TERMS_CAPTIONS);
        return this;
    }

    @Step("Validate loan terms titles list")
    public LoansPageSteps validateTitlesList() {
        List<String> actualTitles = TextUtils.getNormalizedTexts(loanPage.itemTitles);
        TextUtils.assertListMatches(actualTitles, Constants.LOAN_TERMS_TITLES, Set.of(4, 7));
        return this;
    }
    public LoansPageSteps enterMoneyAmount() {
        return enterMoneyAmount(Constants.MONEY_AMOUNT);
    }

    public LoansPageSteps enterMonthAmount() {
        return enterMonthAmount(Constants.MONTH_AMOUNT);
    }

    @Step("Enter money amount: {amount}")
    public LoansPageSteps enterMoneyAmount(double amount) {
        assertThat(loanPage.amountItem).isVisible();
        loanPage.enterAmount(String.valueOf(amount));
        return this;
    }

    @Step("Enter month amount: {months}")
    public LoansPageSteps enterMonthAmount(int months) {
        assertThat(loanPage.monthItem).isVisible();
        String before = loanPage.monthlyPaymentResult.innerText();
        loanPage.enterMonths(String.valueOf(months));
        assertThat(loanPage.monthlyPaymentResult).not().hasText(before);
        return this;
    }

    @Step("Validate monthly payment calculation against the expected formula result")
    public LoansPageSteps validateMonthlyPaymentCalculation() {
        BigDecimal expected = LoanCalculationUtils.calculateMonthlyPayment(
                Constants.MONEY_AMOUNT, Constants.NOMINAL_RATE, Constants.MONTH_AMOUNT);
        return assertMonthlyPayment(expected.doubleValue(), Constants.FORMULA_TOLERANCE);
    }

    @Step("Validate monthly payment matches expected value: {expectedPayment}")
    public LoansPageSteps validateMonthlyPaymentMatchesExpected(double expectedPayment) {
        return assertMonthlyPayment(expectedPayment, Constants.DB_TOLERANCE);
    }

    @Step("Open consumer loan page by API slug: {slug}")
    public LoansPageSteps openConsumerLoanPage(String slug) {
        loanPage.openBySlug(slug);
        return this;
    }

    @Step("Verify UI page title equals API title: {expectedTitle}")
    public LoansPageSteps verifyPageTitle(String expectedTitle) {
        assertThat(loanPage.pageTitle).hasText(expectedTitle);
        return this;
    }

    @Step("Verify UI shows all list items from API")
    public LoansPageSteps verifyCtaListItems(List<String> expectedLabels) {
        for (String label : expectedLabels) {
            assertThat(loanPage.textItem(label)).isVisible();
        }
        return this;
    }

    @Step("Assert monthly payment is {expected} (tolerance ±{tolerance})")
    public LoansPageSteps assertMonthlyPayment(double expected, double tolerance) {
        assertThat(loanPage.monthlyPaymentResult)
                .containsText(LoanCalculationUtils.toleranceRangePattern(expected, tolerance));
        return this;
    }
}