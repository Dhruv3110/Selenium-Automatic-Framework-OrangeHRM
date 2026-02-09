package test;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import utils.AppURLs;
import utils.Log;
import utils.WaitUtils;

public class DashboardTest extends BaseTest {

	// =================== COMMON LOGIN ===================

	private void loginToDashboard() {

		getDriver().get(AppURLs.LOGIN_URL);

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername("Admin");
		loginPage.enterPassword("admin123");
		loginPage.clickLogin();

		// Only ensure dashboard shell is loaded
		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.waitForDashboardShell();

	}

	// =================== TESTS ===================

	@Test(priority = 1)
	public void testSearchNavigation() {

		Log.info("Starting TC019: Search Navigation Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.searchAndSelect("Admin");

		WaitUtils.waitForUrlContains(getDriver(), "admin");

		Assert.assertTrue(getDriver().getCurrentUrl().toLowerCase().contains("admin"),
				"TC019 Failed: Search did not navigate to Admin module");

		Log.info("TC019 Passed");
	}

	@Test(priority = 2)
	public void testProfileDropdownOptions() {

		Log.info("Starting TC020: Profile Dropdown Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());

		Assert.assertTrue(dashboard.areProfileOptionsVisible(), "TC020 Failed: Profile dropdown options not visible");

		Log.info("TC020 Passed");
	}

	@Test(priority = 3)
	public void testLogoutFunctionality() {

		Log.info("Starting TC021: Logout Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.clickLogout();

		WaitUtils.waitForUrlContains(getDriver(), "auth/login");

		LoginPage loginPage = new LoginPage(getDriver());
		Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Logout did not redirect to login page");

		Log.info("TC021 Passed");
	}

	@Test(priority = 4)
	public void testChangePasswordNavigation() {

		Log.info("Starting TC022: Change Password Navigation Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.clickChangePassword();

		WaitUtils.waitForUrlContains(getDriver(), "updatePassword");

		Assert.assertTrue(dashboard.isChangePasswordPageOpened(), "TC022 Failed: Change Password page URL mismatch");

		Log.info("TC022 Passed");
	}

	@Test(priority = 5)
	public void testSupportOptionNavigation() {

		Log.info("Starting TC023: Support Option Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());

		String parentWindow = getDriver().getWindowHandle();
		int windowsBefore = getDriver().getWindowHandles().size();

		dashboard.clickSupport();

		// Wait for either new window or navigation
		WaitUtils.waitForCondition(getDriver(), d -> d.getWindowHandles().size() > windowsBefore
				|| d.getCurrentUrl().toLowerCase().contains("support"));

		if (getDriver().getWindowHandles().size() > windowsBefore) {
			for (String win : getDriver().getWindowHandles()) {
				if (!win.equals(parentWindow)) {
					getDriver().switchTo().window(win);
					break;
				}
			}
		}

		WaitUtils.waitForUrlContains(getDriver(), "support");

		Assert.assertTrue(getDriver().getCurrentUrl().toLowerCase().contains("support"),
				"TC023 Failed: Support page not opened");

		// Cleanup
		if (!getDriver().getWindowHandle().equals(parentWindow)) {
			getDriver().close();
			getDriver().switchTo().window(parentWindow);
		}

		Log.info("TC023 Passed");
	}

	@Test(priority = 6)
	public void testBackButtonAfterLogout() {

		Log.info("Starting TC024: Back Button After Logout Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.clickLogout();

		getDriver().navigate().back();
		getDriver().navigate().refresh();

		LoginPage loginPage = new LoginPage(getDriver());
		Assert.assertTrue(loginPage.isLoginPageDisplayed(),
				"TC024 Failed: Dashboard accessible after logout using back button");

		Log.info("TC024 Passed");
	}

	@Test(priority = 7)
	public void testRefreshKeepsUserLoggedIn() {

		Log.info("Starting TC025: Refresh Dashboard Test");
		loginToDashboard();

		getDriver().navigate().refresh();
		DashboardPage dashboard = new DashboardPage(getDriver());

		Assert.assertTrue(dashboard.isDashboardAccessible(), "TC025 Failed: User logged out after refresh");

		Log.info("TC025 Passed");
	}
}
