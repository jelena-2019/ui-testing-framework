package com.testsmith.constants;

/**
 * Application Constants
 * Centralized place for storing all constants used across the framework
 */
public class AppConstants {

    // Application URLs
    public static final String BASE_URL = "https://practicesoftwaretesting.com";
    public static final String LOGIN_URL = BASE_URL + "/auth/login";
    public static final String PRODUCTS_URL = BASE_URL + "/products";
    public static final String CART_URL = BASE_URL + "/cart";
    public static final String CHECKOUT_URL = BASE_URL + "/checkout";

    // Timeouts (in seconds)
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 15;
    public static final int PAGE_LOAD_TIMEOUT = 20;

    // Test Data
    public static final String VALID_USERNAME = "customer@practicesoftwaretesting.com";
    public static final String VALID_PASSWORD = "welcome01";
    public static final String INVALID_USERNAME = "invalid@example.com";
    public static final String INVALID_PASSWORD = "wrongpassword";

    // Error Messages
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid email or password";
    public static final String REQUIRED_FIELD_MESSAGE = "This field is required";
    public static final String INVALID_EMAIL_MESSAGE = "Please provide a valid email address";

    // File Paths
    public static final String SCREENSHOTS_PATH = "screenshots/";
    public static final String LOGS_PATH = "logs/";
    public static final String TESTDATA_PATH = "src/test/resources/testdata/";

    // Test Execution
    public static final String TEST_REPORT_PATH = "test-output/";
    public static final String EXTENT_REPORT_PATH = "reports/";
}