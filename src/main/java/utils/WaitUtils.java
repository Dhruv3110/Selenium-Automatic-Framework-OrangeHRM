package utils;

import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public final class WaitUtils {

    private WaitUtils() {}

    // =================== VISIBILITY ===================

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return WaitManager.getWait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return WaitManager.getWait(driver)
                .until(ExpectedConditions.visibilityOf(element));
    }

    // =================== CLICKABLE ===================

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return WaitManager.getWait(driver)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return WaitManager.getWait(driver)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    // =================== PRESENCE ===================

    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return WaitManager.getWait(driver)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static List<WebElement> waitForPresenceOfAll(WebDriver driver, By locator) {
        return WaitManager.getWait(driver)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // =================== URL ===================

    public static void waitForUrlContains(WebDriver driver, String value) {
        WaitManager.getWait(driver)
                .until(ExpectedConditions.urlContains(value));
    }

    // =================== INVISIBILITY ===================

    public static void waitForInvisibility(WebDriver driver, By locator) {
        WaitManager.getWait(driver)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // =================== CUSTOM CONDITIONS ===================

    public static void waitForCondition(
            WebDriver driver,
            Function<WebDriver, Boolean> condition) {

        new FluentWait<>(driver)
                .pollingEvery(java.time.Duration.ofMillis(500))
                .ignoring(Exception.class)
                .until(condition);
    }

    // =================== STALE SAFE ===================

    public static boolean retryingFindClick(
            WebDriver driver, WebElement element) {

        try {
            WaitManager.getWait(driver)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(element))
                    .click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =================== OVERLAY ===================

    public static void waitForOverlayToStopBlocking(
            WebDriver driver, By overlayLocator) {

        WaitManager.getWait(driver).until(d -> {
            List<WebElement> overlays = d.findElements(overlayLocator);
            if (overlays.isEmpty()) {
                return true;
            }

            WebElement overlay = overlays.get(0);
            String pointerEvents = overlay.getCssValue("pointer-events");
            String opacity = overlay.getCssValue("opacity");

            return "none".equals(pointerEvents) || "0".equals(opacity);
        });
    }

    // =================== JS ===================

    public static void jsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
}
