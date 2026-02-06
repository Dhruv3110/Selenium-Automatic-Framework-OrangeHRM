package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResetPasswordPage {
	private WebDriver driver;

	@FindBy(xpath = "//h6[text()='Reset Password']")
	WebElement resetPasswordHeading;

	@FindBy(xpath = "//input[@name='username']")
	WebElement usernameTextBox;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement resetPasswordButton;

	@FindBy(xpath = "//button[@type='button' and contains(@class,'oxd-button--ghost')]")
	WebElement cancelButton;

	@FindBy(xpath = "//span[text()='Required']")
	WebElement requiredMessage;

	public ResetPasswordPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isResetPasswordPageDisplayed() {
		return resetPasswordHeading.isDisplayed();
	}

	public void enterUsername(String username) {
		usernameTextBox.sendKeys(username);
	}

	public void clickResetPassword() {
		resetPasswordButton.click();
	}

	public void clickCancel() {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(cancelButton));
		cancelButton.click();
	}

	public boolean isRequiredMessageDisplayed() {
		return requiredMessage.isDisplayed();
	}

	public String getUsernameValue() {
		return usernameTextBox.getAttribute("value");
	}
}
