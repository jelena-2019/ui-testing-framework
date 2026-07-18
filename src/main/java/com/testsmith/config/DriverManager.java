package com.testsmith.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * WebDriver Manager
 * Handles creation and destruction of WebDriver instances
 */
public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initialize WebDriver
     */
    public static void initializeDriver() {
        try {
            String browser = ConfigProperties.getBrowser().toLowerCase();

            switch (browser) {
                case "chrome":
                    initializeChromeDriver();
                    break;
                default:
                    logger.warn("Browser '{}' not recognized. Initializing Chrome as default.", browser);
                    initializeChromeDriver();
            }

            driver.get().manage().window().maximize();
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    /**
     * Initialize Chrome WebDriver
     */
    private static void initializeChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (ConfigProperties.isHeadlessMode()) {
            options.addArguments("--headless");
            logger.info("Running in headless mode");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");

        driver.set(new ChromeDriver(options));
    }

    /**
     * Get WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            logger.warn("WebDriver is null. Initializing...");
            initializeDriver();
        }
        return driver.get();
    }

    /**
     * Quit WebDriver and clean up
     */
    public static void quitDriver() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
                logger.info("WebDriver closed successfully");
            }
        } catch (Exception e) {
            logger.error("Error while closing WebDriver: {}", e.getMessage(), e);
        }
    }
}