package com.testsmith.utils;

import com.testsmith.config.ConfigProperties;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Screenshot Utilities
 * Handles screenshot capture, storage, and Allure attachment
 */
public class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);

    /**
     * Take screenshot and save to file
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            String screenshotPath = ConfigProperties.getScreenshotsPath();
            createDirectoryIfNotExists(screenshotPath);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = testName + "_" + timestamp + ".png";
            String fullPath = screenshotPath + filename;

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(fullPath));

            logger.info("Screenshot captured: {}", fullPath);
            return fullPath;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Capture screenshot as InputStream for Allure attachment
     */
    public static InputStream captureScreenshotAsStream(WebDriver driver) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            return new ByteArrayInputStream(screenshot);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot stream: {}", e.getMessage(), e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    /**
     * Capture screenshot and attach to Allure report
     */
    public static void captureAndAttachScreenshot(WebDriver driver, String name) {
        try {
            Allure.addAttachment(name, "image/png",
                    captureScreenshotAsStream(driver), ".png");
            logger.info("Screenshot attached to Allure: {}", name);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Allure: {}", e.getMessage(), e);
        }
    }

    /**
     * Create directory if it doesn't exist
     */
    public static void createDirectoryIfNotExists(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            logger.error("Failed to create directory: {}", path, e);
        }
    }
}
