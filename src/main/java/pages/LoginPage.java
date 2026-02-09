package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;

    // =================== LOCATORS ===================

    private By usernameTextBox = By.xpath("//input[@name='username']");
    private By passwordTextBox = By.xpath("//input[@type='password']");
    private By loginButton = By.xpath("//button[@type='submit']");

    private By usernameRequiredMessage = By.xpath(
            "//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']");

    private By passwordRequiredMessage = By.xpath(
            "//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']");

    private By forgotPasswordLink = By.xpath("//div[contains(@class,'orangehrm-login-forgot')]");

    private By linkedinIcon = By.xpath("//a[contains(@href,'linkedin')]");
    private By facebookIcon = By.xpath("//a[contains(@href,'facebook')]");
    private By twitterIcon = By.xpath("//a[contains(@href,'twitter')]");
    private By youtubeIcon = By.xpath("//a[contains(@href,'youtube')]");

    private By loginErrorMsg = By.xpath("//p[contains(@class,'oxd-alert-content-text')]");

    // =================== CONSTRUCTOR ===================

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // =================== ACTIONS ===================
    
    public boolean isLoginPageDisplayed() {
        return WaitUtils.waitForVisible(driver,
            By.name("username")
        ).isDisplayed();
    }


    public void enterUsername(String username) {
        WebElement user = WaitUtils.waitForVisible(driver, usernameTextBox);
        user.clear();
        user.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement pass = WaitUtils.waitForVisible(driver, passwordTextBox);
        pass.clear();
        pass.sendKeys(password);
    }

    public void clickLogin() {
        WaitUtils.waitForClickable(driver, loginButton).click();
    }

    public void pressEnterToLogin() {
        WaitUtils.waitForVisible(driver, passwordTextBox)
                .sendKeys(Keys.ENTER);
    }

    public void clickForgotPasswordLink() {
        WaitUtils.waitForClickable(driver, forgotPasswordLink).click();
    }

    // =================== VALIDATIONS ===================

    public boolean isUsernameRequiredMessageDisplayed() {
        return !driver.findElements(usernameRequiredMessage).isEmpty()
                && driver.findElement(usernameRequiredMessage).isDisplayed();
    }

    public boolean isPasswordRequiredMessageDisplayed() {
        return !driver.findElements(passwordRequiredMessage).isEmpty()
                && driver.findElement(passwordRequiredMessage).isDisplayed();
    }

    public boolean isLoginErrorDisplayed() {
        return !driver.findElements(loginErrorMsg).isEmpty();
    }

    public String getLoginErrorMessage() {
        List<WebElement> errors = driver.findElements(loginErrorMsg);
        return errors.isEmpty() ? "" : errors.get(0).getText();
    }

    // =================== GETTERS ===================

    public String getUsernameValue() {
        return driver.findElement(usernameTextBox).getAttribute("value");
    }

    public String getPasswordValue() {
        return driver.findElement(passwordTextBox).getAttribute("value");
    }

    public String getPasswordFieldType() {
        return driver.findElement(passwordTextBox).getAttribute("type");
    }

    // =================== SOCIAL LINKS ===================

    public void clickLinkedInIcon() {
        WaitUtils.waitForClickable(driver, linkedinIcon).click();
    }

    public void clickFacebookIcon() {
        WaitUtils.waitForClickable(driver, facebookIcon).click();
    }

    public void clickTwitterIcon() {
        WaitUtils.waitForClickable(driver, twitterIcon).click();
    }

    public void clickYouTubeIcon() {
        WaitUtils.waitForClickable(driver, youtubeIcon).click();
    }
}
