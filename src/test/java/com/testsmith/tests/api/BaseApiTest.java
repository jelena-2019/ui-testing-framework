package com.testsmith.tests.api;

import com.testsmith.config.ConfigProperties;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base API Test Class
 * Provides setup/teardown for API test classes
 */
public class BaseApiTest {

    protected static final Logger logger = LogManager.getLogger(BaseApiTest.class);

    @BeforeEach
    @Step("Configure API test environment")
    public void setUp() {
        logger.info("======================================");
        logger.info("API Test Setup Started");
        logger.info("======================================");

        RestAssured.baseURI = ConfigProperties.getApiBaseUrl();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Allure.parameter("API Base URL", ConfigProperties.getApiBaseUrl());
        logger.info("API Base URI set to: {}", ConfigProperties.getApiBaseUrl());
    }

    @AfterEach
    @Step("API test teardown")
    public void tearDown() {
        logger.info("======================================");
        logger.info("API Test Teardown Completed");
        logger.info("======================================");
    }
}
