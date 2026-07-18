package com.testsmith.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import com.testsmith.config.DriverManager;
import com.testsmith.config.ConfigProperties;

/**
 * Base Test Class
 * Contains setup and teardown methods for all tests
 */
public class BaseTest {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    /**
     * Setup method - runs before each test
     */
    @BeforeMethod
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
    @AfterMethod
    public void tearDown() {
        logger.info("======================================");
        logger.info("Test Teardown Started");
        logger.info("======================================");

        DriverManager.quitDriver();
        logger.info("WebDriver closed");
    }
}