package com.testsmith.tests.api;

import com.testsmith.api.clients.UserApiClient;
import com.testsmith.api.models.LoginRequest;
import com.testsmith.api.models.LoginResponse;
import com.testsmith.config.ConfigProperties;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    @Description("Verify that a user can successfully login via API with valid credentials and receive an access token")
    @DisplayName("AC1.1 - Verify API login with valid credentials returns access token")
    public void testApiLoginWithValidCredentials() {
        logger.info("API Test: Login with valid credentials - STARTED");

        try {
            LoginRequest loginRequest = new LoginRequest(
                    ConfigProperties.getValidUsername(),
                    ConfigProperties.getValidPassword()
            );

            Allure.step("Send login request with valid credentials", () -> {
                Allure.parameter("email", loginRequest.getEmail());
                Allure.parameter("password", "****");
            });

            Response response = userApiClient.login(loginRequest);

            Allure.step("Verify HTTP status is 200", () -> {
                int statusCode = response.getStatusCode();
                Allure.parameter("Status Code", statusCode);
                Allure.parameter("Response Time", response.getTime() + "ms");
                assertThat(statusCode)
                        .as("Login should return HTTP 200 OK")
                        .isEqualTo(200);
            });

            Allure.step("Verify response contains access_token", () -> {
                String accessToken = response.jsonPath().getString("access_token");
                Allure.parameter("Token Present", accessToken != null);
                assertThat(accessToken)
                        .as("Response should contain a non-null access_token")
                        .isNotNull()
                        .isNotEmpty();
            });

            Allure.step("Verify token_type is Bearer", () -> {
                String tokenType = response.jsonPath().getString("token_type");
                Allure.parameter("Token Type", tokenType);
                assertThat(tokenType.toLowerCase())
                        .as("token_type should be 'bearer'")
                        .isEqualTo("bearer");
            });

            Allure.step("Verify expires_in is present and positive", () -> {
                double expiresIn = response.jsonPath().getDouble("expires_in");
                Allure.parameter("Expires In", expiresIn + " seconds");
                assertThat(expiresIn)
                        .as("expires_in should be greater than 0")
                        .isGreaterThan(0);
            });

            logger.info("API Test: Login with valid credentials - PASSED");
        } catch (AssertionError e) {
            logger.error("API Test: Login with valid credentials - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Details", "text/plain", e.getMessage());
            throw e;
        }
    }

    @Test
    @Story("Invalid API Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that API login with invalid credentials returns an error and no access token")
    @DisplayName("AC1.1 - Verify API login with invalid credentials fails")
    public void testApiLoginWithInvalidCredentials() {
        logger.info("API Test: Login with invalid credentials - STARTED");

        try {
            LoginRequest loginRequest = new LoginRequest(
                    ConfigProperties.getInvalidUsername(),
                    ConfigProperties.getInvalidPassword()
            );

            Allure.step("Send login request with invalid credentials", () -> {
                Allure.parameter("email", loginRequest.getEmail());
                Allure.parameter("password", "****");
            });

            Response response = userApiClient.login(loginRequest);

            Allure.step("Verify HTTP status is not 200", () -> {
                int statusCode = response.getStatusCode();
                Allure.parameter("Status Code", statusCode);
                assertThat(statusCode)
                        .as("Login with invalid credentials should not return 200")
                        .isNotEqualTo(200);
            });

            Allure.step("Verify response does not contain access_token", () -> {
                String accessToken = response.jsonPath().getString("access_token");
                assertThat(accessToken)
                        .as("Invalid login should not return an access_token")
                        .isNull();
            });

            logger.info("API Test: Login with invalid credentials - PASSED");
        } catch (AssertionError e) {
            logger.error("API Test: Login with invalid credentials - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Details", "text/plain", e.getMessage());
            throw e;
        }
    }

    @Test
    @Story("Empty Credentials API Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that API login with empty email returns an error")
    @DisplayName("AC1.1 - Verify API login with empty email fails")
    public void testApiLoginWithEmptyEmail() {
        logger.info("API Test: Login with empty email - STARTED");

        try {
            LoginRequest loginRequest = new LoginRequest("", ConfigProperties.getValidPassword());

            Response response = userApiClient.login(loginRequest);

            Allure.step("Verify HTTP status is not 200", () -> {
                int statusCode = response.getStatusCode();
                Allure.parameter("Status Code", statusCode);
                assertThat(statusCode)
                        .as("Login with empty email should not return 200")
                        .isNotEqualTo(200);
            });

            logger.info("API Test: Login with empty email - PASSED");
        } catch (AssertionError e) {
            logger.error("API Test: Login with empty email - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Details", "text/plain", e.getMessage());
            throw e;
        }
    }

    @Test
    @Story("Empty Credentials API Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that API login with empty password returns an error")
    @DisplayName("AC1.1 - Verify API login with empty password fails")
    public void testApiLoginWithEmptyPassword() {
        logger.info("API Test: Login with empty password - STARTED");

        try {
            LoginRequest loginRequest = new LoginRequest(ConfigProperties.getValidUsername(), "");

            Response response = userApiClient.login(loginRequest);

            Allure.step("Verify HTTP status is not 200", () -> {
                int statusCode = response.getStatusCode();
                Allure.parameter("Status Code", statusCode);
                assertThat(statusCode)
                        .as("Login with empty password should not return 200")
                        .isNotEqualTo(200);
            });

            logger.info("API Test: Login with empty password - PASSED");
        } catch (AssertionError e) {
            logger.error("API Test: Login with empty password - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Details", "text/plain", e.getMessage());
            throw e;
        }
    }

    @Test
    @Story("Empty Credentials API Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that API login with empty credentials returns an error")
    @DisplayName("AC1.1 - Verify API login with empty email and password fails")
    public void testApiLoginWithEmptyCredentials() {
        logger.info("API Test: Login with empty credentials - STARTED");

        try {
            LoginRequest loginRequest = new LoginRequest("", "");

            Response response = userApiClient.login(loginRequest);

            Allure.step("Verify HTTP status is not 200", () -> {
                int statusCode = response.getStatusCode();
                Allure.parameter("Status Code", statusCode);
                assertThat(statusCode)
                        .as("Login with empty credentials should not return 200")
                        .isNotEqualTo(200);
            });

            logger.info("API Test: Login with empty credentials - PASSED");
        } catch (AssertionError e) {
            logger.error("API Test: Login with empty credentials - FAILED: {}", e.getMessage());
            Allure.addAttachment("Failure Details", "text/plain", e.getMessage());
            throw e;
        }
    }
}
