package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.ui.LoansPageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("Loan calculator - Monthly payment")
public class LoanCalculatorTest extends BaseTest {
    LoansPageSteps loansPageSteps;

    @BeforeClass
    public void setUp() {
        loansPageSteps = new LoansPageSteps(page);
    }

    @Test(priority = 1)
    @Description("SCRUM-T2 | Navigate to the consumer loan page from the navigation menu and verify that the page opens")
    public void navigateToConsumerLoanPage() {
        loansPageSteps.openConsumerLoanFromMenu();
    }

    @Test(priority = 2)
    @Description("SCRUM-T2 | Open the consumer loan terms page and verify that the user is redirected to the loan terms page")
    public void openConsumerLoanTermsTab() {
        loansPageSteps.clickConsumerLoanTerms();
    }

    @Test(priority = 3)
    @Description("SCRUM-T2 | Enter the loan amount of 10,000 GEL in the calculator and verify that the value is accepted with no validation error")
    public void enterMoney() {
        loansPageSteps.enterMoneyAmount();
    }

    @Test(priority = 4)
    @Description("SCRUM-T2 | Enter the loan term of 24 months in the calculator and verify that the value is accepted and the monthly payment updates")
    public void enterMonth() {
        loansPageSteps.enterMonthAmount();
    }

    @Test(priority = 5)
    @Description("SCRUM-T2 | Verify that the monthly payment displayed in the calculator matches the expected calculated value")
    public void validateMonthlyPaymentIsCalculatedCorrectly() {
        loansPageSteps.validateMonthlyPaymentCalculation();
    }
}