package com.testsmith.api.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * User API Client
 * Handles all User-related API operations
 */
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

    @Step("POST /users/register - Register new user")
    public Response register(Object userRequest) {
        logger.info("Registering new user");
        return post("/register", userRequest);
    }

    @Step("GET /users/me - Get current user info")
    public Response getCurrentUser() {
        logger.info("Fetching current user info");
        return get("/me");
    }

    @Step("GET /users/logout - Logout")
    public Response logout() {
        logger.info("Logging out");
        return get("/logout");
    }

    @Step("GET /users/refresh - Refresh token")
    public Response refreshToken() {
        logger.info("Refreshing token");
        return get("/refresh");
    }
}
