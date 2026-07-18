package com.testsmith.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import com.testsmith.config.DriverManager;
import com.testsmith.config.ConfigProperties;
import com.testsmith.extensions.ScreenshotExtension;

/**
 * Base Test Class
 * Contains setup and teardown methods for all tests
 */
@ExtendWith(ScreenshotExtension.class)
public class BaseTest {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    /**
     * Setup method - runs before each test
     */
    @BeforeEach
    public void setUp() {
        logger.info("======================================");
        logger.info("Test Setup Started");
        logger.info("======================================");

        DriverManager.initializeDriver();
        driver = DriverManager.getDriver();

        // Navigate to base URL
        driver.navigate().to(ConfigProperties.getBaseUrl());
        logger.info("Navigated to: {}", ConfigProperties.getBaseUrl());
    }

    /**
     * Teardown method - runs after each test
     */
    @AfterEach
    public void tearDown() {
        logger.info("======================================");
        logger.info("Test Teardown Started");
        logger.info("======================================");

        DriverManager.quitDriver();
        logger.info("WebDriver closed");
    }
}