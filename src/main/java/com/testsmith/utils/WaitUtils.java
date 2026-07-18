package com.testsmith.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.testsmith.config.ConfigProperties;

import java.time.Duration;

/**
 * Wait Utilities
 * Provides explicit wait methods for various conditions
 */
public class WaitUtils {

    private static final Logger logger = LogManager.getLogger(WaitUtils.class);

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigProperties.getExplicitWait()));
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementToBeVisible(By locator) {
        try {
            logger.debug("Waiting for element to be visible: {}", locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible within timeout: {}", locator);
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementToBeClickable(By locator) {
        try {
            logger.debug("Waiting for element to be clickable: {}", locator);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable within timeout: {}", locator);
            throw e;
        }
    }

    /**
     * Wait for element to be present in DOM
     */
    public WebElement waitForElementPresence(By locator) {
        try {
            logger.debug("Waiting for element presence: {}", locator);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not present within timeout: {}", locator);
            throw e;
        }
    }

    /**
     * Wait for text to be present in element
     */
    public boolean waitForTextPresentInElement(By locator, String text) {
        try {
            logger.debug("Waiting for text '{}' in element: {}", text, locator);
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (Exception e) {
            logger.error("Text '{}' not found in element within timeout: {}", text, locator);
            return false;
        }
    }

    /**
     * Wait for element to be invisible
     */
    public boolean waitForElementToBeInvisible(By locator) {
        try {
            logger.debug("Waiting for element to be invisible: {}", locator);
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element still visible within timeout: {}", locator);
            return false;
        }
    }

    /**
     * Wait for URL to contain specific text
     */
    public boolean waitForUrlContains(String url) {
        try {
            logger.debug("Waiting for URL to contain: {}", url);
            return wait.until(ExpectedConditions.urlContains(url));
        } catch (Exception e) {
            logger.error("URL does not contain '{}' within timeout", url);
            return false;
        }
    }

    /**
     * Wait for title to contain specific text
     */
    public boolean waitForTitleContains(String title) {
        try {
            logger.debug("Waiting for title to contain: {}", title);
            return wait.until(ExpectedConditions.titleContains(title));
        } catch (Exception e) {
            logger.error("Title does not contain '{}' within timeout", title);
            return false;
        }
    }
}