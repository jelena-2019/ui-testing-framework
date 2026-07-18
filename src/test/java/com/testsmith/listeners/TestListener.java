package com.testsmith.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.testsmith.config.DriverManager;
import com.testsmith.utils.ScreenshotUtils;

/**
 * Test Listener
 * Handles test lifecycle events
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("=====================================");
        logger.info("Test Suite Started: {}", context.getName());
        logger.info("=====================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("=====================================");
        logger.info("Test Suite Finished: {}", context.getName());
        logger.info("Total Tests: {}", context.getAllTestMethods().length);
        logger.info("Passed: {}", context.getPassedTestCount());
        logger.info("Failed: {}", context.getFailedTestCount());
        logger.info("Skipped: {}", context.getSkippedTestCount());
        logger.info("=====================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("");
        logger.info("TEST STARTED: {}", result.getName());
        logger.info("Description: {}", result.getMethod().getDescription());
        logger.info("Class: {}", result.getTestClass().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("TEST PASSED: {}", result.getName());
        logger.info("Duration: {} ms", result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("TEST FAILED: {}", result.getName());
        logger.error("Error: {}", result.getThrowable());

        // Capture screenshot on failure
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
                logger.error("Screenshot captured at: {}", screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("TEST SKIPPED: {}", result.getName());
        logger.warn("Reason: {}", result.getSkipCausesByTestClass());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("TEST PASSED BUT WITHIN SUCCESS PERCENTAGE: {}", result.getName());
    }
}