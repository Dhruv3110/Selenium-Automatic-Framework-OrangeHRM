package test;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.Log;

public class LoginTest extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void resetToLoginPage() {
		getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	}


	@Test(priority = 1)
	@Parameters({ "username", "password" })
	public void testValidLogin_XMLParameters(String username, String password) {

		Log.info("Starting TC01: Valid Login Test");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLogin();

		new WebDriverWait(getDriver(), Duration.ofSeconds(10))
				.until(ExpectedConditions.urlContains("dashboard"));

		Assert.assertTrue(
				getDriver().getCurrentUrl().contains("dashboard"),
				"TC01 Failed: Dashboard not loaded");

		Log.info("TC01 Passed");
	}


	@Test(priority = 2)
	@Parameters({ "password" })
	public void testLogin_InvalidUsername_ValidPassword(String password) {

		Log.info("Starting TC02: Invalid Username Login Test");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername("WrongAdmin");
		loginPage.enterPassword(password);
		loginPage.clickLogin();

		new WebDriverWait(getDriver(), Duration.ofSeconds(10))
				.until(driver -> loginPage.isLoginErrorDisplayed());

		Assert.assertEquals(
				loginPage.getLoginErrorMessage(),
				"Invalid credentials",
				"TC02 Failed");

		Log.info("TC02 Passed");
	}


	@Test(priority = 3)
	@Parameters({ "username" })
	public void testLogin_ValidUsername_InvalidPassword(
			@Optional("Admin") String username) {

		Log.info("Starting TC03: Invalid Password Test");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername(username);
		loginPage.enterPassword("wrongPassword123");
		loginPage.clickLogin();

		new WebDriverWait(getDriver(), Duration.ofSeconds(10))
				.until(driver -> loginPage.isLoginErrorDisplayed());

		Assert.assertEquals(
				loginPage.getLoginErrorMessage(),
				"Invalid credentials",
				"TC03 Failed");

		Log.info("TC03 Passed");
	}

	@Test(priority = 4)
	public void testLogin_InvalidUsername_InvalidPassword() {

		Log.info("Starting TC04: Invalid Login Test");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername("WrongAdmin2");
		loginPage.enterPassword("wrongPassword123");
		loginPage.clickLogin();

		new WebDriverWait(getDriver(), Duration.ofSeconds(10))
				.until(driver -> loginPage.isLoginErrorDisplayed());

		Assert.assertEquals(
				loginPage.getLoginErrorMessage(),
				"Invalid credentials",
				"TC04 Failed");

		Log.info("TC04 Passed");
	}

	@Test(priority = 5)
	public void testLogin_EmptyUsername_EmptyPassword() {

		Log.info("Starting TC05: Empty Username & Password Test");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.clickLogin();

		Assert.assertTrue(loginPage.isUsernameRequiredMessageDisplayed());
		Assert.assertTrue(loginPage.isPasswordRequiredMessageDisplayed());

		Log.info("TC05 Passed");
	}

	@Test(priority = 6)
	@Parameters({ "password" })
	public void testLogin_EmptyUsername_ValidPassword(
			@Optional("admin123")String password) {

		Log.info("Starting TC06: Empty Username + Valid Password");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterPassword(password);
		loginPage.clickLogin();

		Assert.assertTrue(loginPage.isUsernameRequiredMessageDisplayed());

		Log.info("TC06 Passed");
	}

	@Test(priority = 7)
	@Parameters({ "username" })
	public void testLogin_ValidUsername_EmptyPassword(
			@Optional("Admin") String username) {

		Log.info("Starting TC07: Valid Username + Empty Password");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername(username);
		loginPage.clickLogin();

		Assert.assertTrue(loginPage.isPasswordRequiredMessageDisplayed());

		Log.info("TC07 Passed");
	}

	@Test(priority = 8)
	public void testVerifyPasswordIsMasked() {

		Log.info("Starting TC08: Verify Password Masking");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterPassword("admin123");

		Assert.assertEquals(
				loginPage.getPasswordFieldType(),
				"password",
				"TC08 Failed");

		Log.info("TC08 Passed");
	}

	@Test(priority = 9)
	public void testForgotPasswordLinkWorks() {

		Log.info("Starting TC09: Forgot Password Link Test");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.clickForgotPasswordLink();

		new WebDriverWait(getDriver(), Duration.ofSeconds(10))
				.until(ExpectedConditions.urlContains("requestPasswordResetCode"));

		Assert.assertTrue(
				getDriver().getCurrentUrl().contains("requestPasswordResetCode"),
				"TC09 Failed");

		Log.info("TC09 Passed");
	}

	@Test(priority = 10)
	@Parameters({ "username", "password" })
	public void testLoginUsingEnterKey(
			@Optional("Admin") String username, 
			@Optional("admin123") String password) {

		Log.info("Starting TC10: Login Using ENTER Key");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.pressEnterToLogin();

		new WebDriverWait(getDriver(), Duration.ofSeconds(10))
				.until(ExpectedConditions.urlContains("dashboard"));

		Assert.assertTrue(getDriver().getCurrentUrl().contains("dashboard"));

		Log.info("TC10 Passed");
	}


	@Test(priority = 11)
	@Parameters({ "username", "password" })
	public void testRefreshKeepsUserOnLoginPage(
			@Optional("Admin") String username, 
			@Optional("admin123") String password) {

		Log.info("Starting TC11: Refresh Login Page Test");

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);

		getDriver().navigate().refresh();

		Assert.assertTrue(getDriver().getCurrentUrl().contains("auth/login"));
		Assert.assertEquals(loginPage.getUsernameValue(), "");
		Assert.assertEquals(loginPage.getPasswordValue(), "");

		Log.info("TC11 Passed");
	}
}
