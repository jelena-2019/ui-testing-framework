package com.testsmith.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
    @Step("Click on element: {locator}")
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
    @Step("Enter '{text}' into element: {locator}")
    public void sendText(By locator, String text) {
        try {
            logger.debug("Sending text '{}' to element: {}", text, locator);
            WebElement element = waitUtils.waitForElementToBeVisible(locator);
            try {
                element.clear();
            } catch (Exception e) {
                logger.debug("clear() failed, using select-all instead: {}", locator);
                element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            }
            try {
                element.sendKeys(text);
            } catch (Exception e) {
                logger.debug("sendKeys() failed, using JavaScript to set value: {}", locator);
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].value = arguments[1]", element, text);
            }
        } catch (Exception e) {
            logger.error("Failed to send text to element: {}", locator, e);
            throw e;
        }
    }

    /**
     * Get text from element
     */
    @Step("Get text from element: {locator}")
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
     * Check if element is displayed (with explicit wait)
     */
    @Step("Check if element is displayed: {locator}")
    public boolean isElementDisplayed(By locator) {
        try {
            logger.debug("Checking if element is displayed: {}", locator);
            WebElement element = waitUtils.waitForElementToBeVisible(locator);
            return element.isDisplayed();
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
    @Step("Navigate to URL: {url}")
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
