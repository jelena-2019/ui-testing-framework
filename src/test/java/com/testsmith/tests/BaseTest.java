package com.testsmith.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import com.testsmith.config.ConfigProperties;
import com.testsmith.config.DriverManager;
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
    @Step("Initialize browser and navigate to application")
    public void setUp() {
        logger.info("======================================");
        logger.info("Test Setup Started");
        logger.info("======================================");

        DriverManager.initializeDriver();
        driver = DriverManager.getDriver();

        String loginUrl = ConfigProperties.getBaseUrl() + "/auth/login";
        driver.navigate().to(loginUrl);
        Allure.parameter("URL", loginUrl);
        Allure.link("Application", loginUrl);
        logger.info("Navigated to: {}", loginUrl);
    }

    /**
     * Teardown method - runs after each test
     */
    @AfterEach
    @Step("Close browser and clean up")
    public void tearDown() {
        logger.info("======================================");
        logger.info("Test Teardown Started");
        logger.info("======================================");

        DriverManager.quitDriver();
        logger.info("WebDriver closed");
    }
}
