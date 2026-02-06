package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;

	// Page Factory Elements
	@FindBy(xpath = "//input[@name='username']")
	WebElement usernameTextBox;

	@FindBy(xpath = "//input[@type='password']")
	WebElement passwordTextBox;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement loginButton;

	@FindBy(xpath = "//input[@name = 'username']/ancestor::div[contains(@class,'oxd-input-group')]//span[text() = 'Required']")
	WebElement usernameRequiredMessage;

	@FindBy(xpath = "//input[@name = 'password']/ancestor::div[contains(@class,'oxd-input-group')]//span[text() = 'Required']")
	WebElement passwordRequiredMessage;

	@FindBy(xpath = "//div[contains(@class,'orangehrm-login-forgot')]")
	WebElement forgotPasswordLink;

	@FindBy(xpath = "//a[contains(@href,'linkedin')]")
	WebElement linkedinIcon;
	@FindBy(xpath = "//a[contains(@href,'facebook')]")
	WebElement facebookIcon;
	@FindBy(xpath = "//a[contains(@href,'twitter')]")
	WebElement twitterIcon;
	@FindBy(xpath = "//a[contains(@href,'youtube')]")
	WebElement youtubeIcon;

	@FindBy(xpath = "//p[contains(@class,'oxd-alert-content-text')]")
	List<WebElement> loginErrorMsg;

	// Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Actions

	public void enterUsername(String username) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(usernameTextBox));

		usernameTextBox.sendKeys(username);
	}

	public void enterPassword(String password) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(passwordTextBox));

		passwordTextBox.sendKeys(password);
	}

	public void clickLogin() {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(loginButton));

		loginButton.click();
	}

	public void clickForgotPasswordLink() {
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink));

		forgotPasswordLink.click();
	}

	public void pressEnterToLogin() {
		passwordTextBox.sendKeys(Keys.ENTER);
	}

	public boolean isUsernameRequiredMessageDisplayed() {
		return usernameRequiredMessage.isDisplayed();
	}

	public boolean isPasswordRequiredMessageDisplayed() {
		return passwordRequiredMessage.isDisplayed();
	}

	public String getPasswordFieldType() {
		return passwordTextBox.getAttribute("type");
	}

	public void clickLinkedInIcon() {
		linkedinIcon.click();
	}

	public void clickFacebookIcon() {
		facebookIcon.click();
	}

	public void clickTwitterIcon() {
		twitterIcon.click();
	}

	public void clickYouTubeIcon() {
		youtubeIcon.click();
	}
	public String getUsernameValue() {
		return usernameTextBox.getAttribute("value");
	}
	public String getPasswordValue() {
		return passwordTextBox.getAttribute("value");
	}

	public boolean isLoginErrorDisplayed() {
		return loginErrorMsg.size() > 0;
	}

	public String getLoginErrorMessage() {
		if (isLoginErrorDisplayed()) {
			return loginErrorMsg.get(0).getText();
		}
		return "";
	}
}
