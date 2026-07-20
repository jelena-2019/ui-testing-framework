package com.testsmith.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.testsmith.config.ConfigProperties;
import com.testsmith.extensions.Retry;
import com.testsmith.pages.LoginPage;
import com.testsmith.utils.ScreenshotUtils;

@Retry(maxAttempts = 2)
@Epic("User Authentication")
@Feature("Login Functionality")
@DisplayName("AC1 - User Login Tests")
public class LoginTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTests.class);
    private LoginPage loginPage;

    /**
     * Setup method for login tests
     */
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
        logger.info("Login Tests Setup Completed");
    }

    /**
     * AC1.1: Test successful login with valid credentials
     */
    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a user can successfully log in with valid credentials and is redirected away from the login page")
    @DisplayName("AC1.1 - Verify user can login with valid credentials")
    public void testLoginWithValidCredentials() {
        logger.info("Test: Login with valid credentials - STARTED");

        try {
            Allure.step("Verify login form is displayed", () -> {
                assertThat(loginPage.isLoginFormDisplayed())
                        .as("Login form should be displayed on page load")
                        .isTrue();
            });
            logger.info("Login form displayed successfully");

            Allure.step("Verify all required fields are visible", () -> {
                assertThat(loginPage.isEmailInputDisplayed())
                        .as("Email input field should be visible")
                        .isTrue();
                assertThat(loginPage.isPasswordInputDisplayed())
                        .as("Password input field should be visible")
                        .isTrue();
                assertThat(loginPage.isLoginButtonDisplayed())
                        .as("Login button should be visible")
                        .isTrue();
            });
            logger.info("All login form fields are visible");

            String validEmail = ConfigProperties.getValidUsername();
            String validPassword = ConfigProperties.getValidPassword();

            Allure.step("Perform login with valid credentials", () -> {
                loginPage.login(validEmail, validPassword);
            });
            logger.info("Attempting login with valid credentials");

            Thread.sleep(2000);

            Allure.step("Verify successful login by checking URL change", () -> {
                String currentUrl = loginPage.getCurrentUrl();
                Allure.parameter("Current URL", currentUrl);
                assertThat(currentUrl)
                        .as("User should be redirected to products or dashboard page after successful login")
                        .doesNotContain("/auth/login");
            });

            logger.info("Test: Login with valid credentials - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with valid credentials - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Screenshot", "image/png",
                    ScreenshotUtils.captureScreenshotAsStream(driver), ".png");
            throw new AssertionError(e);
        }
    }

    /**
     * AC1.2: Test login with invalid credentials displays error
     */
    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an error message is displayed when user attempts to login with invalid credentials")
    @DisplayName("AC1.2 - Verify error message appears with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        logger.info("Test: Login with invalid credentials - STARTED");

        try {
            String invalidEmail = ConfigProperties.getInvalidUsername();
            String invalidPassword = ConfigProperties.getInvalidPassword();

            Allure.step("Perform login with invalid credentials", () -> {
                loginPage.login(invalidEmail, invalidPassword);
            });
            logger.info("Attempting login with invalid credentials");

            Thread.sleep(2000);

            Allure.step("Verify error message is displayed", () -> {
                assertThat(loginPage.isErrorMessageDisplayed())
                        .as("Error message should be displayed for invalid credentials")
                        .isTrue();
            });
            logger.info("Error message displayed for invalid credentials");

            Allure.step("Verify error message content", () -> {
                String errorMessage = loginPage.getErrorMessage();
                Allure.parameter("Error Message", errorMessage);
                assertThat(errorMessage)
                        .as("Error message should contain relevant text")
                        .isNotNull()
                        .isNotEmpty();
            });

            Allure.step("Verify user stays on login page", () -> {
                assertThat(loginPage.isLoginFormDisplayed())
                        .as("User should remain on login page after failed login")
                        .isTrue();
            });

            logger.info("Test: Login with invalid credentials - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with invalid credentials - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Screenshot", "image/png",
                    ScreenshotUtils.captureScreenshotAsStream(driver), ".png");
            throw new AssertionError(e);
        }
    }

    /**
     * AC1.3: Test login page elements are loaded
     */
    @Test
    @Story("Page Elements")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all required login page elements (form, email input, password input, login button) are visible on page load")
    @DisplayName("AC1.3 - Verify login page displays all required elements")
    public void testLoginPageElementsLoaded() {
        logger.info("Test: Login page elements loaded - STARTED");

        try {
            Allure.step("Verify login form is displayed", () -> {
                assertThat(loginPage.isLoginFormDisplayed())
                        .as("Login form should be displayed")
                        .isTrue();
            });

            Allure.step("Verify email input is present", () -> {
                assertThat(loginPage.isEmailInputDisplayed())
                        .as("Email input field should be present")
                        .isTrue();
            });

            Allure.step("Verify password input is present", () -> {
                assertThat(loginPage.isPasswordInputDisplayed())
                        .as("Password input field should be present")
                        .isTrue();
            });

            Allure.step("Verify login button is present", () -> {
                assertThat(loginPage.isLoginButtonDisplayed())
                        .as("Login button should be present")
                        .isTrue();
            });

            logger.info("All login page elements are displayed correctly");
            logger.info("Test: Login page elements loaded - PASSED");
        } catch (AssertionError e) {
            logger.error("Test: Login page elements loaded - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Screenshot", "image/png",
                    ScreenshotUtils.captureScreenshotAsStream(driver), ".png");
            throw e;
        }
    }

    /**
     * AC1.4: Test empty email field validation
     */
    @Test
    @Story("Field Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the user remains on the login page when attempting to submit with an empty email field")
    @DisplayName("AC1.4 - Verify validation error when email field is empty")
    public void testLoginWithEmptyEmail() {
        logger.info("Test: Login with empty email - STARTED");

        try {
            String validPassword = ConfigProperties.getValidPassword();

            Allure.step("Enter password without email and submit", () -> {
                loginPage.enterPassword(validPassword);
                loginPage.clickLoginButton();
            });

            Thread.sleep(1000);

            Allure.step("Verify user remains on login page", () -> {
                assertThat(loginPage.isLoginFormDisplayed())
                        .as("User should remain on login page")
                        .isTrue();
            });

            logger.info("Test: Login with empty email - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with empty email - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Screenshot", "image/png",
                    ScreenshotUtils.captureScreenshotAsStream(driver), ".png");
            throw new AssertionError(e);
        }
    }

    /**
     * AC1.5: Test empty password field validation
     */
    @Test
    @Story("Field Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the user remains on the login page when attempting to submit with an empty password field")
    @DisplayName("AC1.5 - Verify validation error when password field is empty")
    public void testLoginWithEmptyPassword() {
        logger.info("Test: Login with empty password - STARTED");

        try {
            String validEmail = ConfigProperties.getValidUsername();

            Allure.step("Enter email without password and submit", () -> {
                loginPage.enterEmail(validEmail);
                loginPage.clickLoginButton();
            });

            Thread.sleep(1000);

            Allure.step("Verify user remains on login page", () -> {
                assertThat(loginPage.isLoginFormDisplayed())
                        .as("User should remain on login page")
                        .isTrue();
            });

            logger.info("Test: Login with empty password - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with empty password - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Screenshot", "image/png",
                    ScreenshotUtils.captureScreenshotAsStream(driver), ".png");
            throw new AssertionError(e);
        }
    }
}
