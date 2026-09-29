## 9.1 Framework Architecture

### `pages/`

Responsibility — page-specific locators and UI actions. Each Page Object represents one page and also owns reusable components needed on that page.

Examples:

* `HomePage`
* `LoansPage`
* `CurrencyExchangePage`
* `BranchesPage`

The Page Objects expose actions such as `enterAmount()`, `openBySlug()` and `clickTab()` instead of allowing tests to interact with raw Playwright locators directly.

### `components/`

Responsibility — reusable UI fragments that appear across multiple pages.

Current components:

* `HeaderComponent`
* `NavigationComponent`
* `LanguageSwitcherComponent`

Components own their selectors and primitive actions, but contain no assertions.

**Why separate Components from Pages?**
The header, navigation and language switcher are shared across several pages. Keeping them inside individual Page Objects would duplicate selectors and make UI changes harder to maintain.

**Concrete reuse example:**
`LanguageSwitcherComponent` is used by `HomePage`, `LoansPage`, `CurrencyExchangePage` and `BranchesPage`. If the language-switcher selector changes, it is updated once instead of in four Page Objects.

`HeaderComponent` is also reused by `HomePage` and `LoansPage`; search functionality therefore uses the same header implementation instead of duplicating the search selector.

### `steps/`

Responsibility — business-level actions and assertions.

`steps/ui/` contains UI workflows such as:

* `HomePageSteps`
* `LoansPageSteps`
* `CurrencyExchangeSteps`
* `BranchSearchSteps`

`steps/api/` contains API workflows and assertions such as:

* `RegionSteps`
* `CurrencyExchangeSteps`
* `ConsumerLoanApiSteps`

The Steps layer is also where Allure `@Step` annotations describe the business action in the report.

Tests call Steps rather than performing low-level `click()`, `fill()` or locator operations themselves.

### `api/`

Responsibility — REST Assured communication and API response models.

`api/clients/` contains API clients such as:

* `RegionsApiClient`
* `PagesApiClient`
* `CurrencyExchangeApi`

`api/models/` contains the Java models used to deserialize responses, including `SitePage`, `SectionComponent`, `SectionInputs`, `ListItem`, `Region` and `ExchangeRate`.

### `database/`

Responsibility — H2 database configuration, MyBatis mapping and database models.

`DataBaseConfig` creates the local H2 database and initializes the required table/data.

`LoanTestDataMapper` retrieves loan test data through MyBatis and maps it to `LoanTestData`.

### `tests/`

Responsibility — define the actual test scenarios.

Test classes are intentionally thin: they chain Steps and provide the scenario-level structure. Selectors, API request implementation and database queries are kept outside the test classes.

---

## 9.2 Localization Strategy

**Scenario: `CurrencyExchangeLocalizationTest`**

**Risk** — Georgian and English versions of the same page can become inconsistent, for example when the page title or buy/sell labels are updated in one language but not the other.

**Implementation** — The test does not duplicate the scenario into separate Georgian and English test methods.

Instead, one method:

```text
verifyLocalization(langCode, expectedTitle, expectedBuyLabel, expectedSellLabel)
```

is driven by `LocalizationDataProvider`.

The flow is:

```text
Constants
    ↓
LocalizationDataProvider
    ↓
verifyLocalization(...)
    ↓
Currency Exchange Page
```

Locale-specific values are stored in `Constants`, including:

```text
CURRENCY_TITLE_EN / CURRENCY_TITLE_KA
BUY_LABEL_EN / BUY_LABEL_KA
SELL_LABEL_EN / SELL_LABEL_KA
```

The DataProvider supplies the appropriate values together with the language code.

**Validated:**

* `<html lang>` attribute
* Page title
* Buy label
* Sell label

**Why this approach:** the navigation and page interaction remain identical for both languages, so duplicating the entire test would create unnecessary maintenance.

**Adding another locale:**

1. Add the expected strings to `Constants`.
2. Add a row to `LocalizationDataProvider`.
3. Extend `LanguageSwitcherComponent` so the new language can be selected.

The actual localization test logic does not need to be duplicated.

---

## 9.3 SQL & Test Data Strategy

**Scenario: `LoanCalculatorDataDrivenTest`**

**Risk** — The loan calculator needs multiple amount/term/expected-payment combinations. Hardcoding every combination inside Java would make the test implementation difficult to maintain and would require code changes for every new data variation.

**Implementation** — Test data is stored in SQL and loaded into the H2 database.

The complete flow is:

```text
loan_test_data.sql
        ↓
      H2
        ↓
LoanTestDataMapper
        ↓
  LoanTestData
        ↓
LoanDataProvider
        ↓
LoanCalculatorDataDrivenTest
```

### Database

`loan_test_data.sql` contains the initial loan scenarios.

`DataBaseConfig` creates the table if necessary and runs the seed data when the table is empty.

### MyBatis

`LoanTestDataMapper.getAllLoanTestData()` retrieves the rows from H2 and maps them into Java objects.

The database field:

```text
term_months
```

is mapped to the Java property:

```text
termMonths
```

### Java Model

`LoanTestData` represents one database row and is passed through the test framework instead of exposing SQL results directly to the test.

### DataProvider

`LoanDataProvider` converts the database data into TestNG data and supplies each row to the test.

### Test

`LoanCalculatorDataDrivenTest` receives a `LoanTestData` object and validates the calculator using that dataset.

**Adding a new variation:**
A new row can be added to `loan_test_data.sql`:

```sql
INSERT INTO loan_test_data
    (amount, term_months, expected_monthly_payment)
VALUES
    (15000, 30, <expected_payment>);
```

No changes are required to the test method or DataProvider.

Because the seed script runs only when the table is empty, the `./data` directory must be deleted when changing the initial SQL dataset so the database can be recreated.

---

## 9.4 API → UI Strategy

**Scenario: `ConsumerLoanApiToUiTest`**

**Risk** — CMS/API content and rendered UI content can become inconsistent. A page can technically load successfully while displaying an outdated title, missing CTA or incorrect navigation URL.

**API selected:**

```text
GET /api/v1/sites/pages/{pageId}?locale=ka-GE
```

The test uses the Consumer Loan page ID from:

```text
CONSUMER_LOAN_PAGE_ID
```

The API response is deserialized through:

```text
SitePage
 → SectionComponent
 → SectionInputs
 → ListItem
```

`ConsumerLoanApiSteps` selects the section:

```text
site-loans-consumer-loan-cta-section
```

### Values compared with the UI

**1. Page slug**

The API slug is used to open the corresponding UI page.

**2. CTA section title**

The API title is compared with the page `h1`.

**3. CTA list item labels**

Every CTA label returned by the API is checked against the visible UI.

### Why these values?

These values are CMS-managed and user-visible. They are therefore useful for detecting content drift between the backend source and what is actually rendered to the customer.

The API is treated as the source of truth; the UI is validated against it.

### Inconsistencies detected

The test can detect:

* API title differs from the UI `h1`.
* API CTA item is missing from the UI.
* CTA text was changed in the API but not reflected in the UI.
* API returns an incorrect/broken page slug.
* Expected CTA section is missing from the API response.
* UI content does not match CMS-managed content.

The test therefore validates more than API availability — it validates **API content → actual UI content consistency**.

---

## 9.5 Network Validation

**Scenario: `BranchSearchNetworkTest`**

**Risk** — The ATM/branch filter can appear to work visually while sending an incorrect request, using the wrong filter value, or rendering an incomplete response.

**UI action:**
The test opens:

```text
/ka/atms&branches
```

and clicks the ATM tab:

```text
ბანკომატები
```

### Monitored request

The click triggers:

```text
POST /api/v1/atmsAndBranches/list
```

The test uses Playwright `waitForResponse()` around the click action, so the request is captured as part of the action that triggers it.

No `Thread.sleep()` or arbitrary delay is required.

### Network-level validation

The test validates:

* Endpoint
* HTTP method: `POST`
* HTTP status: `200`
* Request body
* `filter` equals:

```json
["ATM"]
```

* Response contains a non-empty list

### UI-level validation

After receiving the response, the test compares:

```text
API response list size
        ↓
UI ATM/branch item count
```

The number of rendered:

```text
app-atm-branches-section-list-item
```

elements must equal the number of records returned by the API.

This validates the complete flow:

```text
ATM filter click
      ↓
POST request
      ↓
Correct filter
      ↓
API response
      ↓
Correct number of UI results
```

---

## 9.6 Test Stability

**Scenario: `LoanCalculatorDataDrivenTest`**

This scenario combines multiple datasets with an asynchronously recalculated UI value, formatted numbers and repeated page initialization, making it a useful stability-sensitive scenario.

### Source of instability 1 — Asynchronous calculation

**Risk** — The monthly payment does not necessarily update immediately after entering a new amount/term. A fixed delay could either be too short or unnecessarily long.

**Mitigation** — The implementation reads the previous result and waits for the result to change using Playwright's web-first assertion.

Conceptually:

```text
Read previous value
        ↓
Enter new value
        ↓
Wait until result != previous value
        ↓
Validate new result
```

No `Thread.sleep()` is used.

---

### Source of instability 2 — Formatted numeric values

**Risk** — The UI can display numbers with separators or currency symbols, while the database contains plain numeric values.

For example:

```text
15,000 ₾
```

cannot safely be compared directly with a numeric database value.

**Mitigation** — `LoanCalculationUtils.parseAmount()` removes formatting characters before parsing the value.

The final calculation is compared using a tolerance of `0.5` to account for normal rounding differences.

---

### Source of instability 3 — State leaking between datasets

**Risk** — The same test implementation is executed with multiple database rows. If the previous dataset leaves values in the form, the next dataset could start from an unexpected state.

**Mitigation** — `@BeforeMethod` navigates back to the base URL and reopens the loan page before every dataset execution.

`BaseTest` also uses instance-level:

```text
Playwright
Browser
BrowserContext
Page
```

so parallel test classes do not share browser state.

---

### Source of instability 4 — Elements not ready

**Risk** — After navigation or a calculation, an element can exist in the DOM before it is ready for interaction.

**Mitigation** — Steps verify visibility with Playwright web-first assertions before interacting with elements.

This allows Playwright's built-in waiting/retry behavior to handle normal rendering delays instead of adding fixed waits.

---

### Stability approach

The framework intentionally avoids using retries to hide failures and avoids arbitrary waits such as:

```java
Thread.sleep(2000);
```

Instead, each wait is tied to an actual application condition:

```text
Element becomes visible
        ↓
Interact

Calculation result changes
        ↓
Validate result

Page state is reset
        ↓
Execute next dataset
```

This makes the test wait for the **condition it actually depends on**, rather than waiting for an arbitrary amount of time.
