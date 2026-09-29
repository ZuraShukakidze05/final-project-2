package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.api.ConsumerLoanApiSteps;
import ge.tbc.testautomation.steps.ui.LoansPageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("API to UI consistency - Consumer Loan")
public class ConsumerLoanApiToUiTest extends BaseTest {
    ConsumerLoanApiSteps apiSteps = new ConsumerLoanApiSteps();
    LoansPageSteps loansPageSteps;

    @BeforeMethod
    public void setUp() {
        loansPageSteps = new LoansPageSteps(page);
        apiSteps.getPage(Constants.CONSUMER_LOAN_PAGE_ID, Constants.LOCALE_KA_GE);
    }

    @Test(priority = 1)
    @Description("SCRUM-T8 | Call the API and verify that the response has status 200, is deserialized into a POJO, and contains a non-empty slug, CTA title and CTA list")
    public void apiResponseIsValid() {
        apiSteps.verifyResponseNotEmpty();
    }

    @Test(priority = 2)
    @Description("SCRUM-T8 | Open the consumer loan page using the slug received from the API and verify that the page is fully loaded")
    public void consumerLoanPageOpens() {
        loansPageSteps
                .openConsumerLoanPage(apiSteps.getSlug())
                .verifyPageTitle(apiSteps.getCtaTitle());
    }

    @Test(priority = 3)
    @Description("SCRUM-T8 | Compare the title and list items displayed on the UI with the API data and verify that the values match the API response exactly")
    public void uiMatchesApi() {
        loansPageSteps
                .openConsumerLoanPage(apiSteps.getSlug())
                .verifyPageTitle(apiSteps.getCtaTitle())
                .verifyCtaListItems(apiSteps.getCtaListLabels());
    }
}