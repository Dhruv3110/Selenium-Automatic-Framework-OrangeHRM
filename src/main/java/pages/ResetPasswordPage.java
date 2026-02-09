package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class ResetPasswordPage {

    private WebDriver driver;

    // =================== LOCATORS ===================

    private By resetPasswordHeading =
            By.xpath("//h6[text()='Reset Password']");

    private By usernameTextBox =
            By.xpath("//input[@name='username']");

    private By resetPasswordButton =
            By.xpath("//button[@type='submit']");

    private By cancelButton =
            By.xpath("//button[@type='button' and contains(@class,'oxd-button--ghost')]");

    private By requiredMessage =
            By.xpath("//span[text()='Required']");

    // =================== CONSTRUCTOR ===================

    public ResetPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    // =================== VALIDATIONS ===================

    public boolean isResetPasswordPageDisplayed() {
        return WaitUtils
                .waitForVisible(driver, resetPasswordHeading)
                .isDisplayed();
    }

    public boolean isRequiredMessageDisplayed() {
        return !driver.findElements(requiredMessage).isEmpty()
                && driver.findElement(requiredMessage).isDisplayed();
    }

    // =================== ACTIONS ===================

    public void enterUsername(String username) {
        WebElement user =
                WaitUtils.waitForVisible(driver, usernameTextBox);
        user.clear();
        user.sendKeys(username);
    }

    public void clickResetPassword() {
        WaitUtils.waitForClickable(driver, resetPasswordButton).click();
    }

    public void clickCancel() {
        WaitUtils.waitForClickable(driver, cancelButton).click();
    }

    // =================== GETTERS ===================

    public String getUsernameValue() {
        return driver.findElement(usernameTextBox)
                .getAttribute("value");
    }
}
