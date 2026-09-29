package ge.tbc.testautomation.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Collections;

import static ge.tbc.testautomation.constants.Constants.BASE_URL;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    protected boolean isHeadless() {
        return false;
    }

    @BeforeClass
    public void setUpBrowser() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless()).setSlowMo(1000)
                .setChannel("chrome");

        browser = playwright.chromium().launch(launchOptions);

        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080).setPermissions(Collections.emptyList()));
        page = browserContext.newPage();
        page.navigate(BASE_URL);
    }

    @AfterClass
    public void tearDownBrowser() {
        if (page != null) page.close();
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}