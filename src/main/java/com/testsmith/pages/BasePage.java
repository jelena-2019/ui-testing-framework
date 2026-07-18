package com.testsmith.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.testsmith.utils.WaitUtils;

/**
 * Base Page Class
 * Contains common methods and properties for all page objects
 */
public class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    /**
     * Click on element
     */
    public void click(By locator) {
        try {
            logger.debug("Clicking on element: {}", locator);
            WebElement element = waitUtils.waitForElementToBeClickable(locator);
            element.click();
        } catch (Exception e) {
            logger.error("Failed to click on element: {}", locator, e);
            throw e;
        }
    }

    /**
     * Send text to element
     */
    public void sendText(By locator, String text) {
        try {
            logger.debug("Sending text '{}' to element: {}", text, locator);
            WebElement element = waitUtils.waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to send text to element: {}", locator, e);
            throw e;
        }
    }

    /**
     * Get text from element
     */
    public String getText(By locator) {
        try {
            logger.debug("Getting text from element: {}", locator);
            WebElement element = waitUtils.waitForElementToBeVisible(locator);
            return element.getText().trim();
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator, e);
            throw e;
        }
    }

    /**
     * Check if element is displayed
     */
    public boolean isElementDisplayed(By locator) {
        try {
            logger.debug("Checking if element is displayed: {}", locator);
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not displayed: {}", locator);
            return false;
        }
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElement(By locator) {
        return waitUtils.waitForElementToBeVisible(locator);
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Navigate to URL
     */
    public void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.navigate().to(url);
    }

    /**
     * Wait for URL to contain specific text
     */
    public boolean waitForUrl(String urlText) {
        return waitUtils.waitForUrlContains(urlText);
    }
}