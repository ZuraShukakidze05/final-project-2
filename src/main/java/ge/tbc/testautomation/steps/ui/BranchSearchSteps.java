package ge.tbc.testautomation.steps.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.pages.BranchesPage;
import ge.tbc.testautomation.utils.JsonUtils;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

public class BranchSearchSteps {
    Page page;
    BranchesPage branchesPage;
    Response capturedResponse;

    public BranchSearchSteps(Page page) {
        this.page = page;
        this.branchesPage = new BranchesPage(page);
    }

    @Step("Open branches page")
    public BranchSearchSteps openBranchesPage() {
        page.navigate(Constants.BASE_URL + "/" + Constants.LANG_KA + Constants.BRANCHES_PAGE_PATH);
        return this;
    }

    @Step("Select ATM tab and capture network request")
    public BranchSearchSteps selectAtmTabAndCaptureRequest() {
        capturedResponse = page.waitForResponse(
                r -> r.url().contains(Constants.BRANCH_SEARCH_ENDPOINT)
                        && r.request().method().equals(Constants.BRANCH_SEARCH_METHOD)
                        && JsonUtils.bodyContains(r.request().postData(), Constants.BRANCH_FILTER_ATM),
                () -> branchesPage.getTabByName(Constants.BRANCH_ATM_TAB).click());
        assertNotNull(capturedResponse, Constants.ERROR_NO_ATM_REQUEST);
        return this;
    }

    @Step("Verify request endpoint, method and response status")
    public BranchSearchSteps verifyRequestEndpointMethodAndStatus() {
        assertTrue(capturedResponse.url().contains(Constants.BRANCH_SEARCH_ENDPOINT),
                Constants.ERROR_UNEXPECTED_ENDPOINT + capturedResponse.url());
        assertEquals(capturedResponse.request().method(), Constants.BRANCH_SEARCH_METHOD);
        assertEquals(capturedResponse.status(), 200);
        return this;
    }

    @Step("Verify request filter and response list against UI")
    public BranchSearchSteps verifyRequestBodyAndUi() {
        JsonNode requestBody = JsonUtils.parse(capturedResponse.request().postData());
        JsonNode filter = requestBody.get("filter");
        assertTrue(filter != null && filter.isArray() && filter.size() == 1
                        && Constants.BRANCH_FILTER_ATM.equals(filter.get(0).asText()),
                Constants.ERROR_UNEXPECTED_FILTER + requestBody);

        JsonNode list = JsonUtils.findList(JsonUtils.parse(capturedResponse.text()));
        assertNotNull(list, Constants.ERROR_NO_LIST_IN_RESPONSE);
        assertTrue(list.size() > 0, Constants.ERROR_EMPTY_RESPONSE_LIST);

        assertThat(branchesPage.branchResultsList).hasCount(list.size());
        return this;
    }
}