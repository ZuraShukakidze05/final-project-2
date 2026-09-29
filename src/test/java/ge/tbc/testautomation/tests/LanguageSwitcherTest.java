package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.ui.HomePageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("TBC Bank")
@Feature("Language switcher")
public class LanguageSwitcherTest extends BaseTest {
    HomePageSteps homePageSteps;

    @BeforeMethod
    public void setUp() {
        homePageSteps = new HomePageSteps(page);
    }

    @Test(priority = 1)
    @Description("SCRUM-T1 | Verify that the language switcher is visible on the page")
    public void verifySwitcherIsVisible() {
        homePageSteps.verifySwitcherVisible();
    }

    @Test(priority = 2)
    @Description("SCRUM-T1 | Click the language switcher to change the site language to English")
    public void clickSwitcher() {
        homePageSteps.clickSwitcher();
    }

    @Test(priority = 3)
    @Description("SCRUM-T1 | Verify that the page switches to the English version and the interface texts change to the selected language")
    public void verifyContentAfterSwitch() {
        homePageSteps.verifyLanguageSwitch(Constants.LANG_EN, Constants.DIGITAL_BANK_EN);
    }
}