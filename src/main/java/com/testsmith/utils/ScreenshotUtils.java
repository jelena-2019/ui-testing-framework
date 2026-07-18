package com.testsmith.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.testsmith.config.ConfigProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Screenshot Utilities
 * Handles screenshot capture and storage
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