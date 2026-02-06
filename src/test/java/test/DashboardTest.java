package test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import utils.AppURLs;
import utils.Log;

public class DashboardTest extends BaseTest {

	private WebDriverWait getWait() {
		return new WebDriverWait(getDriver(), Duration.ofSeconds(15));
	}

	// =================== COMMON LOGIN ===================

	private void loginToDashboard() {

		getDriver().get(AppURLs.LOGIN_URL);

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.enterUsername("Admin");
		loginPage.enterPassword("admin123");
		loginPage.clickLogin();
		
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search']")));
	}

	// =================== TESTS ===================

	@Test(priority = 1)
	public void testSearchNavigation() {

		Log.info("Starting TC019: Search Navigation Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.searchAndSelect("Admin");
		
		getWait().until(ExpectedConditions.urlContains("admin"));

		Assert.assertTrue(getDriver().getCurrentUrl().toLowerCase().contains("admin"),
				"TC019 Failed: Search did not navigate to Admin module");

		Log.info("TC019 Passed.");
	}

	@Test(priority = 2)
	public void testProfileDropdownOptions() {

		Log.info("Starting TC020: Profile Dropdown Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());

		Assert.assertTrue(dashboard.areProfileOptionsVisible(), "TC020 Failed: Profile dropdown options not visible");

		Log.info("TC020 Passed.");
	}

	@Test(priority = 3)
	public void testLogoutFunctionality() {

		Log.info("Starting TC021: Logout Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.clickLogout();
		
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		getWait().until(ExpectedConditions.urlContains("auth/login"));
		
		Assert.assertTrue(
				getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).isDisplayed(),
				"TC021 Failed: Logout did not redirect to login page");

		Log.info("TC021 Passed.");
	}

	@Test(priority = 4)
	public void testChangePasswordNavigation() {

		Log.info("Starting TC022: Change Password Navigation Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.clickChangePassword();

		getWait().until(ExpectedConditions.urlContains("Password"));

		Assert.assertTrue(getDriver().getCurrentUrl().contains("Password"),
				"TC022 Failed: Change Password page not opened");

		Log.info("TC022 Passed.");
	}

	@Test(priority = 5)
	public void testSupportOptionNavigation() {

		Log.info("Starting TC023: Support Option Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());

		String parentWindow = getDriver().getWindowHandle();
		int windowsBefore = getDriver().getWindowHandles().size();

		dashboard.clickSupport();

		getWait().until(driver -> driver.getWindowHandles().size() > windowsBefore
				|| driver.getCurrentUrl().toLowerCase().contains("support"));

		if (getDriver().getWindowHandles().size() > windowsBefore) {
			for (String win : getDriver().getWindowHandles()) {
				if (!win.equals(parentWindow)) {
					getDriver().switchTo().window(win);
					break;
				}
			}
		}

		getWait().until(ExpectedConditions.urlContains("support"));

		Assert.assertTrue(getDriver().getCurrentUrl().toLowerCase().contains("support"),
				"TC023 Failed: Support page not opened");

		// Cleanup
		if (!getDriver().getWindowHandle().equals(parentWindow)) {
			getDriver().close();
			getDriver().switchTo().window(parentWindow);
		}

		Log.info("TC023 Passed.");
	}

	@Test(priority = 6)
	public void testBackButtonAfterLogout() {

		Log.info("Starting TC024: Back Button After Logout Test");
		loginToDashboard();

		DashboardPage dashboard = new DashboardPage(getDriver());
		dashboard.clickLogout();

		getDriver().navigate().back();

		getDriver().navigate().refresh();

		Assert.assertTrue(
				getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).isDisplayed(),
				"TC024 Failed: Dashboard accessible after logout using back button");

		Log.info("TC024 Passed.");
	}

	@Test(priority = 7)
	public void testRefreshKeepsUserLoggedIn() {

		Log.info("Starting TC025: Refresh Dashboard Test");
		loginToDashboard();

		getDriver().navigate().refresh();

		Assert.assertTrue(getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search']")))
				.isDisplayed(), "TC025 Failed: User logged out after refresh");

		Log.info("TC025 Passed.");
	}
}
