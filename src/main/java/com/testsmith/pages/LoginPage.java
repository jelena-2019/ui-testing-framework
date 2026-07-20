package com.testsmith.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Login Page Object
 * Contains all locators and methods for Login page
 * AC1: User Login Functionality
 */
public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // Page Locators
    private static final By EMAIL_INPUT = By.id("email");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.className("btnSubmit");
    private static final By ERROR_MESSAGE = By.className("help-block");
    private static final By REMEMBER_ME_CHECKBOX = By.id("rememberMe");
    private static final By FORGOT_PASSWORD_LINK = By.linkText("Forgot your Password?");
    private static final By REGISTER_LINK = By.linkText("Sign up");
    private static final By LOGIN_FORM = By.cssSelector("form[data-test='login-form']");
    private static final By SUCCESS_MESSAGE = By.className("success-message");

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("Login Page initialized");
    }

    /**
     * Enter email in email field
     */
    @Step("Enter email: {email}")
    public LoginPage enterEmail(String email) {
        logger.info("Entering email: {}", email);
        sendText(EMAIL_INPUT, email);
        return this;
    }

    /**
     * Enter password in password field
     */
    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        sendText(PASSWORD_INPUT, password);
        return this;
    }

    /**
     * Click login button
     */
    @Step("Click login button")
    public void clickLoginButton() {
        logger.info("Clicking Login button");
        click(LOGIN_BUTTON);
    }

    /**
     * Check Remember Me checkbox
     */
    @Step("Check Remember Me checkbox")
    public LoginPage checkRememberMe() {
        logger.info("Checking Remember Me checkbox");
        click(REMEMBER_ME_CHECKBOX);
        return this;
    }

    /**
     * Click Forgot Password link
     */
    @Step("Click Forgot Password link")
    public void clickForgotPasswordLink() {
        logger.info("Clicking Forgot Password link");
        click(FORGOT_PASSWORD_LINK);
    }

    /**
     * Click Register link
     */
    @Step("Click Register link")
    public void clickRegisterLink() {
        logger.info("Clicking Register link");
        click(REGISTER_LINK);
    }

    /**
     * Login with credentials
     */
    @Step("Login with email: {email}")
    public void login(String email, String password) {
        logger.info("Attempting to login with email: {}", email);
        enterEmail(email);
        click(PASSWORD_INPUT);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Get error message
     */
    @Step("Get error message")
    public String getErrorMessage() {
        logger.info("Getting error message");
        try {
            return getText(ERROR_MESSAGE);
        } catch (Exception e) {
            logger.warn("No error message found");
            return null;
        }
    }

    /**
     * Check if error message is displayed
     */
    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isElementDisplayed(ERROR_MESSAGE);
    }

    /**
     * Check if login form is displayed
     */
    @Step("Check if login form is displayed")
    public boolean isLoginFormDisplayed() {
        logger.info("Checking if login form is displayed");
        return isElementDisplayed(LOGIN_FORM);
    }

    /**
     * Get success message
     */
    @Step("Get success message")
    public String getSuccessMessage() {
        logger.info("Getting success message");
        try {
            return getText(SUCCESS_MESSAGE);
        } catch (Exception e) {
            logger.warn("No success message found");
            return null;
        }
    }

    /**
     * Is email input displayed
     */
    @Step("Check if email input is displayed")
    public boolean isEmailInputDisplayed() {
        return isElementDisplayed(EMAIL_INPUT);
    }

    /**
     * Is password input displayed
     */
    @Step("Check if password input is displayed")
    public boolean isPasswordInputDisplayed() {
        return isElementDisplayed(PASSWORD_INPUT);
    }

    /**
     * Is login button displayed
     */
    @Step("Check if login button is displayed")
    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(LOGIN_BUTTON);
    }
}
