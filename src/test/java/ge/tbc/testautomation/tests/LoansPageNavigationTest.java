package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.ui.LoansPageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("Loans page - Navigation")
public class LoansPageNavigationTest extends BaseTest {
    LoansPageSteps loansPageSteps;

    @BeforeClass
    public void setUp() {
        loansPageSteps = new LoansPageSteps(page);
    }

    @Test(priority = 1)
    @Description("SCRUM-T3 | Navigate to the consumer loan page from the navigation menu and verify that the page opens")
    public void navigateToConsumerLoanPage() {
        loansPageSteps.openConsumerLoanFromMenu();
    }

    @Test(priority = 2)
    @Description("SCRUM-T3 | Open the consumer loan terms page and verify that the user is redirected to the terms tab")
    public void openConsumerLoanTermsTab() {
        loansPageSteps.clickConsumerLoanTerms()
                .clickConsumerLoanTermsTab();
    }

    @Test(priority = 3)
    @Description("SCRUM-T3 | Verify that the page title and the loan terms table are displayed with the expected captions and titles")
    public void validateConsumerLoanTermsContent() {
        loansPageSteps.validateCaptionsList()
                .validateTitlesList();
    }
}