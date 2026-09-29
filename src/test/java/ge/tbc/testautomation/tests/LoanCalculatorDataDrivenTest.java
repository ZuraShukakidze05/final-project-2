package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.data.LoanDataProvider;
import ge.tbc.testautomation.database.models.LoanTestData;
import ge.tbc.testautomation.steps.ui.LoansPageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("Loan calculator - DB validation")
public class LoanCalculatorDataDrivenTest extends BaseTest {
    LoansPageSteps loansPageSteps;

    @BeforeMethod
    public void setUp() {
        page.navigate(Constants.BASE_URL);
        loansPageSteps = new LoansPageSteps(page);
        loansPageSteps
                .openConsumerLoanFromMenu()
                .clickConsumerLoanTerms();
    }

    @Test(dataProvider = "loanData", dataProviderClass = LoanDataProvider.class)
    @Description("SCRUM-T5 | Enter the loan amount and term from the database data and verify that the calculated monthly payment matches the expected value stored in the database")
    public void validateLoanCalculatorAgainstDbData(LoanTestData data) {
        loansPageSteps
                .enterMoneyAmount(data.getAmount())
                .enterMonthAmount(data.getTermMonths())
                .validateMonthlyPaymentMatchesExpected(data.getExpectedMonthlyPayment());
    }
}