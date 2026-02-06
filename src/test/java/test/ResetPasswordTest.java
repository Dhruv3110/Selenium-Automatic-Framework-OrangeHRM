package test;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ResetPasswordPage;
import utils.AppURLs;
import utils.Log;

public class ResetPasswordTest extends BaseTest {

	@Test(priority = 1)
	public void testResetPasswordWithEmptyUsername() {

		Log.info("Starting TC014: Empty Username Reset Test");

		getDriver().get(AppURLs.REST_PASSWORD_URL);

		ResetPasswordPage resetPage = new ResetPasswordPage(getDriver());

		resetPage.clickResetPassword();

		Assert.assertTrue(resetPage.isRequiredMessageDisplayed(), "	TC014 Failed: Required message not displayed!");

		Log.info("TC014 Passed.");
	}

	@Test(priority = 2)
	public void testCancelButtonRedirectsToLogin() {

		Log.info("Starting TC015: Cancel Button Redirects Back to Login Page");

		getDriver().get(AppURLs.REST_PASSWORD_URL);

		ResetPasswordPage resetPage = new ResetPasswordPage(getDriver());

		resetPage.clickCancel();

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("auth/login"));

		Assert.assertTrue(getDriver().getCurrentUrl().contains("auth/login"),
				"TC015 Failed: Cancel button did not redirect to Login page!");

		Log.info("TC015 Passed.");
	}

	@Test(priority = 3)
	public void testRefreshResetsUsernameField() {

		Log.info("Starting TC016: Refresh Clears Username Field Test");

		getDriver().get(AppURLs.REST_PASSWORD_URL);

		ResetPasswordPage resetPage = new ResetPasswordPage(getDriver());

		resetPage.enterUsername("Admin");

		getDriver().navigate().refresh();

		resetPage = new ResetPasswordPage(getDriver());

		Assert.assertEquals(resetPage.getUsernameValue(), "",
				"TC016 Failed: Username field not cleared after refresh!");

		Log.info("TC016 Passed.");
	}

	@Test(priority = 4)
	public void testDirectAccessToResetPageWithoutLogin() {

		Log.info("Starting TC017: Direct Access Reset Page Test");

		getDriver().get(AppURLs.REST_PASSWORD_URL);

		ResetPasswordPage resetPage = new ResetPasswordPage(getDriver());

		Assert.assertTrue(resetPage.isResetPasswordPageDisplayed(),
				"TC017 Failed: Reset Password page not accessible without login!");

		Log.info("TC017 Passed.");
	}

	@Test(priority = 5)
	public void testResetPasswordRedirectsToConfirmationPage() {
		Log.info("Starting TC018: Reset Password Redirect Page to Send Password Reset page ");
		getDriver().get(AppURLs.REST_PASSWORD_URL);
		ResetPasswordPage resetPage = new ResetPasswordPage(getDriver());
		resetPage.enterUsername("RandomUser123");
		resetPage.clickResetPassword();

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("sendPasswordReset"));
		Assert.assertTrue(getDriver().getCurrentUrl().contains("sendPasswordReset"),
				"TC018 Failed: Reset confirmation page not opened!");

		Log.info("TC018 Passed.");
	}
}
