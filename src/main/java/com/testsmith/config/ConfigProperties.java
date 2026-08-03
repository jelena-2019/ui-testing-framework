package com.testsmith.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Properties Manager
 * Loads and manages test configuration from application.properties.
 * System properties (e.g. -Dheadless.mode=true from the Maven CLI or CI)
 * always take precedence over values defined in application.properties.
 */
public class ConfigProperties {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream file = new FileInputStream("src/main/resources/application.properties");
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.err.println("Failed to load properties file: " + e.getMessage());
        }
    }

    /**
     * Resolves a configuration value. System properties take precedence over
     * values defined in application.properties, enabling runtime overrides
     * for CI/CD pipelines.
     */
    private static String getProperty(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        return systemValue != null ? systemValue : properties.getProperty(key, defaultValue);
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://practicesoftwaretesting.com");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static long getImplicitWait() {
        return Long.parseLong(getProperty("implicit.wait", "20"));
    }

    public static long getExplicitWait() {
        return Long.parseLong(getProperty("explicit.wait", "20"));
    }

    public static boolean isHeadlessMode() {
        return Boolean.parseBoolean(getProperty("headless.mode", "false"));
    }

    public static boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public static String getLogsPath() {
        return getProperty("logs.path", "logs/");
    }

    public static String getScreenshotsPath() {
        return getProperty("screenshots.path", "screenshots/");
    }

    public static String getTestDataPath() {
        return getProperty("testdata.path", "src/test/resources/testdata/");
    }

    public static String getValidUsername() {
        return getProperty("valid.username", "customer@practicesoftwaretesting.com");
    }

    public static String getValidPassword() {
        return getProperty("valid.password", "welcome01");
    }

    public static String getInvalidUsername() {
        return getProperty("invalid.username", "invalid@example.com");
    }

    public static String getInvalidPassword() {
        return getProperty("invalid.password", "wrongpassword");
    }

    public static String getApiBaseUrl() {
        return getProperty("api.base.url", "https://api.practicesoftwaretesting.com");
    }
}