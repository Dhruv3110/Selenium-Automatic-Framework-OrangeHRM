package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Log;
import utils.WaitUtils;

public class DashboardPage {

    private WebDriver driver;

    // =================== LOCATORS ===================

    private By dashboardLayout =
            By.xpath("//div[contains(@class,'oxd-layout')]");

    private By topBar =
            By.xpath("//header[contains(@class,'oxd-topbar')]");

    private By searchBox =
            By.xpath("//input[@placeholder='Search']");

    private By hamburgerMenu =
            By.xpath("//i[contains(@class,'oxd-topbar-header-hamburger')]");

    private By sidePanel =
            By.xpath("//aside[contains(@class,'oxd-sidepanel')]");

    private By menuItems =
            By.xpath("//ul[contains(@class,'oxd-main-menu')]//span[contains(@class,'oxd-main-menu-item--name')]");

    private By profileDropdown =
            By.xpath("//span[contains(@class,'oxd-userdropdown-tab')]");

    private By dropdownMenu =
            By.xpath("//ul[contains(@class,'oxd-dropdown-menu')]");

    private By aboutOption =
            By.xpath("//a[@class='oxd-userdropdown-link' and normalize-space()='About']");

    private By supportOption =
            By.xpath("//a[@class='oxd-userdropdown-link' and normalize-space()='Support']");

    private By changePasswordOption =
            By.xpath("//a[@class='oxd-userdropdown-link' and contains(@href,'updatePassword')]");

    private By logoutOption =
            By.xpath("//a[@class='oxd-userdropdown-link' and contains(@href,'logout')]");

    // =================== CONSTRUCTOR ===================

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // =================== DASHBOARD SYNC ===================

    private void waitForDashboardToLoad() {

        WaitUtils.waitForVisible(driver, topBar);
        WaitUtils.waitForVisible(driver, dashboardLayout);
        WaitUtils.waitForPresence(driver, sidePanel);

        ensureMenuExpanded();
    }

    private void ensureMenuExpanded() {

        if (!driver.findElements(searchBox).isEmpty()
                && driver.findElement(searchBox).isDisplayed()) {
            return;
        }

        if (!driver.findElements(hamburgerMenu).isEmpty()
                && driver.findElement(hamburgerMenu).isDisplayed()) {

            WaitUtils.waitForClickable(driver, hamburgerMenu).click();
        }
    }
    
    public void waitForDashboardShell() {
        WaitUtils.waitForVisible(driver,
            By.xpath("//header[contains(@class,'oxd-topbar')]")
        );
    }
    
    public boolean isDashboardAccessible() {
        return !driver.findElements(searchBox).isEmpty()
               || !driver.findElements(hamburgerMenu).isEmpty();
    }


    // =================== SEARCH ===================

    public void searchAndSelect(String moduleName) {

        Log.info("[Search] Searching module: " + moduleName);

        waitForDashboardToLoad();

        WebElement search = WaitUtils.waitForClickable(driver, searchBox);
        search.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        search.sendKeys(moduleName);

        List<WebElement> items =
                WaitUtils.waitForPresenceOfAll(driver, menuItems);

        for (WebElement item : items) {
            if (item.getText().trim().equalsIgnoreCase(moduleName)) {
                item.click();
                return;
            }
        }

        throw new RuntimeException("Menu item not found: " + moduleName);
    }

    // =================== PROFILE ===================

    public void openProfileDropdown() {

        Log.info("[Profile] Opening profile dropdown");

        waitForDashboardToLoad();

        WebElement profile =
                WaitUtils.waitForVisible(driver, profileDropdown);

        try {
            profile.click();
        } catch (ElementClickInterceptedException e) {
            Log.warn("[Profile] Click intercepted, using JS click");
            WaitUtils.jsClick(driver, profile);
        }

        WaitUtils.waitForPresence(driver, dropdownMenu);
    }

    public boolean areProfileOptionsVisible() {
        try {
            openProfileDropdown();

            return WaitUtils.waitForVisible(driver, aboutOption).isDisplayed()
                    && WaitUtils.waitForVisible(driver, supportOption).isDisplayed()
                    && WaitUtils.waitForVisible(driver, changePasswordOption).isDisplayed()
                    && WaitUtils.waitForVisible(driver, logoutOption).isDisplayed();

        } catch (Exception e) {
            Log.error("[Profile] Options not visible: " + e.getMessage());
            return false;
        }
    }

    // =================== ACTIONS ===================

    public void clickLogout() {

        Log.info("[Logout] Logging out");

        openProfileDropdown();

        WebElement logout =
                WaitUtils.waitForVisible(driver, logoutOption);

        try {
            logout.click();
        } catch (ElementClickInterceptedException e) {
            WaitUtils.jsClick(driver, logout);
        }
    }

    public void clickChangePassword() {

        openProfileDropdown();

        WebElement changePwd =
                WaitUtils.waitForVisible(driver, changePasswordOption);

        try {
            changePwd.click();
        } catch (ElementClickInterceptedException e) {
            WaitUtils.jsClick(driver, changePwd);
        }
    }

    public void clickSupport() {

        openProfileDropdown();

        WebElement support =
                WaitUtils.waitForVisible(driver, supportOption);

        try {
            support.click();
        } catch (ElementClickInterceptedException e) {
            WaitUtils.jsClick(driver, support);
        }
    }
    public boolean isChangePasswordPageOpened() {
        return driver.getCurrentUrl()
                     .toLowerCase()
                     .contains("updatepassword");
    }

}
