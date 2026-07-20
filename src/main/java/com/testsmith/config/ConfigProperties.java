package com.testsmith.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Properties Manager
 * Loads and manages test configuration from application.properties
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

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "https://practicesoftwaretesting.com");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static long getImplicitWait() {
        return Long.parseLong(properties.getProperty("implicit.wait", "20"));
    }

    public static long getExplicitWait() {
        return Long.parseLong(properties.getProperty("explicit.wait", "20"));
    }

    public static boolean isHeadlessMode() {
        return Boolean.parseBoolean(properties.getProperty("headless.mode", "false"));
    }

    public static boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(properties.getProperty("screenshot.on.failure", "true"));
    }

    public static String getLogsPath() {
        return properties.getProperty("logs.path", "logs/");
    }

    public static String getScreenshotsPath() {
        return properties.getProperty("screenshots.path", "screenshots/");
    }

    public static String getTestDataPath() {
        return properties.getProperty("testdata.path", "src/test/resources/testdata/");
    }

    public static String getValidUsername() {
        return properties.getProperty("valid.username", "customer@practicesoftwaretesting.com");
    }

    public static String getValidPassword() {
        return properties.getProperty("valid.password", "welcome01");
    }

    public static String getInvalidUsername() {
        return properties.getProperty("invalid.username", "invalid@example.com");
    }

    public static String getInvalidPassword() {
        return properties.getProperty("invalid.password", "wrongpassword");
    }
}