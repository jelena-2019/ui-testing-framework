---
name: add-test-scenarios
description: Use when adding new test scenarios or extending existing tests in the ui-testing-framework repo. Covers UI tests (Selenium Page Object Model + JUnit 5), API tests (REST Assured), config changes, Allure annotations, retry wiring, and verification. Front-load triggers: "add test", "new test", "extend tests", "add API test", "add UI test", "AC1.x", "test scenario".
---

# Adding Test Scenarios — ui-testing-framework

Framework conventions for adding or extending test scenarios. Follow these patterns exactly so new code stays consistent with the existing suite. Do NOT add code comments unless the user asks.

## Project Overview

Java 8, Maven, JUnit 5.9.3, Selenium 3.141.59, REST Assured 5.3.2, AssertJ 3.24.1, Allure 2.25.0, Log4j2 2.21.1, WebDriverManager 5.6.3. Targets `https://practicesoftwaretesting.com` (UI) and `https://api.practicesoftwaretesting.com` (API). Test naming: class `XxxTests`, files matching `**/*Tests.java` or `**/*Test.java` (surefire includes).

### Directory Layout

```
src/main/java/com/testsmith/
├── config/       # DriverManager, ConfigProperties
├── constants/    # AppConstants
├── pages/        # BasePage + concrete page objects (UI)
├── api/clients/  # ApiClient + per-resource API clients
├── api/models/   # Jackson POJOs for requests/responses
└── utils/        # WaitUtils, ScreenshotUtils

src/test/java/com/testsmith/
├── tests/        # BaseTest, LoginTests (UI tests)
├── tests/api/    # BaseApiTest, UserApiTests (API tests)
└── extensions/   # Retry, RetryExtension, ScreenshotExtension

src/main/resources/       # application.properties, log4j2.xml
src/test/resources/       # allure.properties
```

## UI Test Pattern (Selenium + Page Object Model)

### Page Objects (`src/main/java/com/testsmith/pages/`)

Extend `BasePage`. Constructor takes `WebDriver` and calls `super(driver)`. Pattern:

```java
public class MyPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(MyPage.class);

    private static final By MY_BUTTON = By.id("myButton");

    public MyPage(WebDriver driver) {
        super(driver);
        logger.info("My Page initialized");
    }

    @Step("Click my button")
    public void clickMyButton() {
        logger.info("Clicking My button");
        click(MY_BUTTON);
    }
}
```

Rules:
- Locators: `private static final By` constants at top of class.
- Use `BasePage` methods: `click(By)`, `sendText(By, String)`, `getText(By)`, `isElementDisplayed(By)` — these already add explicit waits, logging, `@Step`, and fallbacks.
- Annotate every public action/query method with `@Step("human readable description")`.
- Fluent methods (`enterEmail`, etc.) return `this` for chaining.
- Log start of each action with `logger.info(...)`.

### UI Test Classes (`src/test/java/com/testsmith/tests/`)

Extend `BaseTest` (which registers `@ExtendWith(ScreenshotExtension.class)` and handles driver init/navigate/quit in setUp/tearDown). Pattern:

```java
@Retry(maxAttempts = 2)
@Epic("User Authentication")
@Feature("Login Functionality")
@DisplayName("AC1 - User Login Tests")
public class MyTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(MyTests.class);
    private MyPage myPage;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        myPage = new MyPage(driver);
    }

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that ...")
    @DisplayName("AC1.1 - Verify ...")
    public void testSomething() {
        logger.info("Test: ... - STARTED");
        try {
            Allure.step("Verify ...", () -> {
                assertThat(myPage.isSomethingDisplayed())
                        .as("description")
                        .isTrue();
            });
            logger.info("Test: ... - PASSED");
        } catch (AssertionError e) {
            logger.error("Test: ... - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Screenshot", "image/png",
                    ScreenshotUtils.captureScreenshotAsStream(driver), ".png");
            throw e;
        }
    }
}
```

Rules:
- Use AssertJ `assertThat(...).as("description")`.
- Wrap logical steps in `Allure.step("name", () -> { ... })` lambdas.
- Add `Allure.parameter("key", value)` for data visibility.
- try/catch pattern: on `AssertionError`, log error, attach failure screenshot, rethrow.
- Avoid `Thread.sleep` where an explicit wait exists; use `WaitUtils` instead.
- Reuse credentials from `ConfigProperties.getValidUsername()` / `getValidPassword()` / `getInvalidUsername()` / `getInvalidPassword()` — never hardcode.

## API Test Pattern (REST Assured)

### API Clients (`src/main/java/com/testsmith/api/clients/`)

Extend `ApiClient` (which configures base URI from `ConfigProperties.getApiBaseUrl()` and JSON content type). Constructor passes the resource base path. Pattern:

```java
public class UserApiClient extends ApiClient {
    private static final Logger logger = LogManager.getLogger(UserApiClient.class);
    private static final String USERS_PATH = "/users";

    public UserApiClient() {
        super(USERS_PATH);
    }

    @Step("POST /users/login - Login with credentials")
    public Response login(Object loginRequest) {
        logger.info("Attempting login");
        return post("/login", loginRequest);
    }
}
```

Use `get(path)`, `post(path, body)`, `put(path, body)`, `patch(path, body)`, `delete(path)`. For paths with params, use `getRequest().put("/{id}", pathParam, body)` style with REST Assured path templates. Annotate with `@Step` and log each call.

### Model POJOs (`src/main/java/com/testsmith/api/models/`)

Jackson POJOs. Map snake_case JSON to camelCase fields with `@JsonProperty`:

```java
public class LoginResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private double expiresIn;

    // getters + setters
}
```

Requests need a no-arg constructor plus an all-args convenience constructor.

### API Test Classes (`src/test/java/com/testsmith/tests/api/`)

Extend `BaseApiTest` (sets `RestAssured.baseURI` and request/response logging filters in setUp). Pattern:

```java
@Epic("API Testing")
@Feature("User Authentication")
@DisplayName("AC1.1 - API User Login Tests")
public class UserApiTests extends BaseApiTest {
    private static final Logger logger = LogManager.getLogger(UserApiTests.class);
    private UserApiClient userApiClient;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        userApiClient = new UserApiClient();
    }

    @Test
    @Story("Valid API Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that ...")
    @DisplayName("AC1.1 - Verify ...")
    public void testApiLoginWithValidCredentials() {
        try {
            Response response = userApiClient.login(loginRequest);
            Allure.step("Verify HTTP status is 200", () -> {
                assertThat(response.getStatusCode()).isEqualTo(200);
            });
            Allure.step("Verify access_token is present", () -> {
                assertThat(response.jsonPath().getString("access_token")).isNotNull();
            });
        } catch (AssertionError e) {
            logger.error("FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Details", "text/plain", e.getMessage());
            throw e;
        }
    }
}
```

Rules:
- Assertions: AssertJ over REST Assured `response.jsonPath().getString/getDouble/getList(...)`.
- Annotate `@Epic("API Testing")` at class level, per-test `@Story` + `@Severity`.
- Extract data via `jsonPath`; use model POJOs only when deserializing complex bodies.

## Config Changes

Adding a new configurable value requires three edits:
1. `src/main/resources/application.properties` — add `key=value`
2. `src/main/java/com/testsmith/config/ConfigProperties.java` — add `public static <Type> getKey()` with a sensible default (see existing getters)
3. `src/main/java/com/testsmith/constants/AppConstants.java` — add a constant if it's a fixed URL/path/credential used across the suite

## Extensions

- **Retry**: annotate a test class or method with `@Retry(maxAttempts = N)` for flaky tests. Uses reflection-based InvocationInterceptor; first attempt runs via normal lifecycle, later attempts re-run setUp/test/tearDown via reflection.
- **Screenshot**: `ScreenshotExtension` (TestWatcher) automatically captures and attaches a screenshot to Allure on failure for classes extending `BaseTest`. No action needed per-test.

## API Reference

- Swagger UI: `https://api.practicesoftwaretesting.com/api/documentation`
- OpenAPI spec: `https://api.practicesoftwaretesting.com/docs?api-docs.json`
- Auth: `POST /users/login` with `{"email", "password"}` → `{"access_token", "token_type", "expires_in"}` (JWT Bearer; note `token_type` is lowercase `"bearer"`).
- Endpoint groups: Brand, Cart, Category, Contact, Favorite, Image, Invoice, Payment, Postcode, Product, Product Spec, Report, TOTP, User. Fetch the spec before writing tests for a new resource and check each endpoint's auth requirements.

## Verification

After adding tests, run in order:

```bash
mvn compile test-compile              # ensure everything compiles
mvn test -Dtest=MyTests               # run just the new test class
mvn test                              # full suite (UI + API) must stay green
```

## Common Pitfalls

- REST Assured deps must be `compile` scope (not `test`) because API clients live in `src/main/java`.
- Don't break the `@Retry` / `InvocationInterceptor` constraint: `invocation.proceed()` can only be called once — retries are reflection-based.
- `BaseTest.setUp()` always navigates to the login page first; if a test needs a different page, navigate after `super.setUp()`.
- Keep Allure severity levels meaningful: BLOCKER for critical flows, CRITICAL for important validations, NORMAL for element/page checks.
