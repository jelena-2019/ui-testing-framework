package com.testsmith.extensions;

import com.testsmith.config.DriverManager;
import com.testsmith.utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

/**
 * Screenshot Extension
 * JUnit 5 extension that captures screenshots on test failure and attaches them to Allure
 */
public class ScreenshotExtension implements TestWatcher {

    private static final Logger logger = LogManager.getLogger(ScreenshotExtension.class);

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("Test Failed: {}", context.getDisplayName());
        logger.error("Error: {}", cause.getMessage());

        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                String testName = context.getDisplayName();
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, testName);
                logger.error("Screenshot captured at: {}", screenshotPath);

                Allure.addAttachment("Failure Screenshot", "image/png",
                        ScreenshotUtils.captureScreenshotAsStream(driver), ".png");
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        logger.warn("Test Disabled: {}", context.getDisplayName());
        logger.warn("Reason: {}", reason.orElse("No reason provided"));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("Test Aborted: {}", context.getDisplayName());
        logger.warn("Cause: {}", cause.getMessage());
    }
}
