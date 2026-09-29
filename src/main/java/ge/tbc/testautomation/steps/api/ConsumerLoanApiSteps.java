package ge.tbc.testautomation.steps.api;

import ge.tbc.testautomation.api.clients.PagesApiClient;
import ge.tbc.testautomation.api.models.ListItem;
import ge.tbc.testautomation.api.models.SectionComponent;
import ge.tbc.testautomation.api.models.SectionInputs;
import ge.tbc.testautomation.api.models.SitePage;
import ge.tbc.testautomation.constants.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConsumerLoanApiSteps {

    PagesApiClient pagesApiClient = new PagesApiClient();
    SitePage sitePage;
    SectionInputs ctaSection;

    @Step("Get page {pageId} for locale {locale} and deserialize into POJO")
    public ConsumerLoanApiSteps getPage(String pageId, String locale) {
        Response response = pagesApiClient.getPage(pageId, locale);
        assertThat(response.statusCode(), equalTo(Constants.HTTP_OK));
        sitePage = response.as(SitePage.class);

        ctaSection = sitePage.getSectionComponents().stream()
                .filter(c -> Constants.CONSUMER_LOAN_CTA_SECTION_KEY.equals(c.getKey()))
                .map(SectionComponent::getInputs)
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        Constants.ERROR_CTA_SECTION_NOT_FOUND + Constants.CONSUMER_LOAN_CTA_SECTION_KEY));
        return this;
    }

    @Step("Verify API response is not empty")
    public ConsumerLoanApiSteps verifyResponseNotEmpty() {
        assertThat(Constants.ERROR_SITE_PAGE_NOT_DESERIALIZED, sitePage, notNullValue());
        assertThat(Constants.ERROR_SLUG_EMPTY, sitePage.getSlug(), not(blankOrNullString()));
        assertThat(Constants.ERROR_CTA_TITLE_EMPTY, ctaSection.getTitle(), not(blankOrNullString()));
        assertThat(Constants.ERROR_CTA_LIST_EMPTY, ctaSection.getList(), not(empty()));
        assertThat(Constants.ERROR_CTA_LIST_BLANK_LABELS,
                getCtaListLabels(), everyItem(not(blankOrNullString())));
        return this;
    }

    public String getSlug() {
        return sitePage.getSlug();
    }

    public String getCtaTitle() {
        return ctaSection.getTitle();
    }

    public List<String> getCtaListLabels() {
        return ctaSection.getList().stream().map(ListItem::getLabel).toList();
    }
}