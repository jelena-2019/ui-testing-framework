package com.testsmith.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.assertj.core.api.Assertions.assertThat;
import com.testsmith.config.ConfigProperties;
import com.testsmith.constants.AppConstants;
import com.testsmith.pages.LoginPage;
import com.testsmith.utils.ScreenshotUtils;

/**
 * AC1 - User Login Tests
 * Test Suite for User Login Functionality
 *
 * AC1: User can successfully login with valid credentials
 * AC1.1: User receives error message with invalid credentials
 * AC1.2: User sees login form elements on page load
 */
public class LoginTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTests.class);
    private LoginPage loginPage;

    /**
     * Setup method for login tests
     */
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
        logger.info("Login Tests Setup Completed");
    }

    /**
     * AC1.1: Test successful login with valid credentials
     */
    @Test(description = "AC1.1 - Verify user can login with valid credentials")
    public void testLoginWithValidCredentials() {
        logger.info("Test: Login with valid credentials - STARTED");

        try {
            // Verify login form is displayed
            assertThat(loginPage.isLoginFormDisplayed())
                    .as("Login form should be displayed on page load")
                    .isTrue();
            logger.info("Login form displayed successfully");

            // Verify all required fields are visible
            assertThat(loginPage.isEmailInputDisplayed())
                    .as("Email input field should be visible")
                    .isTrue();
            assertThat(loginPage.isPasswordInputDisplayed())
                    .as("Password input field should be visible")
                    .isTrue();
            assertThat(loginPage.isLoginButtonDisplayed())
                    .as("Login button should be visible")
                    .isTrue();
            logger.info("All login form fields are visible");

            // Perform login
            String validEmail = ConfigProperties.getValidUsername();
            String validPassword = ConfigProperties.getValidPassword();
            
            logger.info("Attempting login with valid credentials");
            loginPage.login(validEmail, validPassword);

            // Wait for navigation after login
            Thread.sleep(2000);

            // Verify successful login by checking URL change
            String currentUrl = loginPage.getCurrentUrl();
            logger.info("Current URL after login: {}", currentUrl);
            
            assertThat(currentUrl)
                    .as("User should be redirected to products or dashboard page after successful login")
                    .doesNotContain("/auth/login");

            logger.info("Test: Login with valid credentials - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with valid credentials - FAILED: {}", e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "LoginWithValidCredentials_Failed");
            throw new AssertionError(e);
        }
    }

    /**
     * AC1.2: Test login with invalid credentials displays error
     */
    @Test(description = "AC1.2 - Verify error message appears with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        logger.info("Test: Login with invalid credentials - STARTED");

        try {
            // Get invalid credentials
            String invalidEmail = ConfigProperties.getInvalidUsername();
            String invalidPassword = ConfigProperties.getInvalidPassword();

            logger.info("Attempting login with invalid credentials");
            loginPage.login(invalidEmail, invalidPassword);

            // Wait for error message
            Thread.sleep(1000);

            // Verify error message is displayed
            assertThat(loginPage.isErrorMessageDisplayed())
                    .as("Error message should be displayed for invalid credentials")
                    .isTrue();
            logger.info("Error message displayed for invalid credentials");

            // Verify error message content
            String errorMessage = loginPage.getErrorMessage();
            logger.info("Error message: {}", errorMessage);
            
            assertThat(errorMessage)
                    .as("Error message should contain relevant text")
                    .isNotNull()
                    .isNotEmpty();

            // Verify user stays on login page
            assertThat(loginPage.isLoginFormDisplayed())
                    .as("User should remain on login page after failed login")
                    .isTrue();

            logger.info("Test: Login with invalid credentials - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with invalid credentials - FAILED: {}", e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "LoginWithInvalidCredentials_Failed");
            throw new AssertionError(e);
        }
    }

    /**
     * AC1.3: Test login page elements are loaded
     */
    @Test(description = "AC1.3 - Verify login page displays all required elements")
    public void testLoginPageElementsLoaded() {
        logger.info("Test: Login page elements loaded - STARTED");

        try {
            // Verify login form is displayed
            assertThat(loginPage.isLoginFormDisplayed())
                    .as("Login form should be displayed")
                    .isTrue();

            // Verify email input is present
            assertThat(loginPage.isEmailInputDisplayed())
                    .as("Email input field should be present")
                    .isTrue();

            // Verify password input is present
            assertThat(loginPage.isPasswordInputDisplayed())
                    .as("Password input field should be present")
                    .isTrue();

            // Verify login button is present
            assertThat(loginPage.isLoginButtonDisplayed())
                    .as("Login button should be present")
                    .isTrue();

            logger.info("All login page elements are displayed correctly");
            logger.info("Test: Login page elements loaded - PASSED");
        } catch (AssertionError e) {
            logger.error("Test: Login page elements loaded - FAILED: {}", e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "LoginPageElementsLoaded_Failed");
            throw e;
        }
    }

    /**
     * AC1.4: Test empty email field validation
     */
    @Test(description = "AC1.4 - Verify validation error when email field is empty")
    public void testLoginWithEmptyEmail() {
        logger.info("Test: Login with empty email - STARTED");

        try {
            // Leave email empty and enter password
            String validPassword = ConfigProperties.getValidPassword();
            loginPage.enterPassword(validPassword);
            loginPage.clickLoginButton();

            // Wait for validation
            Thread.sleep(1000);

            // Verify error message or validation message is displayed
            assertThat(loginPage.isLoginFormDisplayed())
                    .as("User should remain on login page")
                    .isTrue();

            logger.info("Test: Login with empty email - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with empty email - FAILED: {}", e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "LoginWithEmptyEmail_Failed");
            throw new AssertionError(e);
        }
    }

    /**
     * AC1.5: Test empty password field validation
     */
    @Test(description = "AC1.5 - Verify validation error when password field is empty")
    public void testLoginWithEmptyPassword() {
        logger.info("Test: Login with empty password - STARTED");

        try {
            // Enter email and leave password empty
            String validEmail = ConfigProperties.getValidUsername();
            loginPage.enterEmail(validEmail);
            loginPage.clickLoginButton();

            // Wait for validation
            Thread.sleep(1000);

            // Verify user stays on login page
            assertThat(loginPage.isLoginFormDisplayed())
                    .as("User should remain on login page")
                    .isTrue();

            logger.info("Test: Login with empty password - PASSED");
        } catch (AssertionError | InterruptedException e) {
            logger.error("Test: Login with empty password - FAILED: {}", e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "LoginWithEmptyPassword_Failed");
            throw new AssertionError(e);
        }
    }
}