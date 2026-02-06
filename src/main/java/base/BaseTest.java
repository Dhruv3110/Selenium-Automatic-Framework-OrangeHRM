package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;

import utils.ExtentReportManager;
import utils.Log;

public abstract class BaseTest {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	@BeforeSuite(alwaysRun = true)
	public void setupReport() {
		ExtentReportManager.getReportInstance();
	}

	@BeforeMethod(alwaysRun = true)
	public void setup(ITestResult result) {

		Log.info("Starting WebDriver...");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
//		options.addArguments("--window-size=1920,1080");
//		options.addArguments("--disable-gpu");
//		options.addArguments("--no-sandbox");
//		options.addArguments("--disable-dev-shm-usage");
//		options.addArguments("--disable-notifications");
//		options.addArguments("--disable-infobars");
//		options.addArguments("--disable-extensions");

		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		webDriver.manage().window().maximize();

		driver.set(webDriver);

		ExtentReportManager.createTest(result.getMethod().getMethodName());
	}

	protected WebDriver getDriver() {
		return driver.get();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			Log.error("FAILED: " + result.getMethod().getMethodName());

			String screenshotPath = ExtentReportManager.captureScreenshot(getDriver(),
					result.getMethod().getMethodName());
			ExtentReportManager.getTest().fail(result.getThrowable(),
					MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		}

		if (getDriver() != null) {
			Log.info("Closing the browser...");
			getDriver().quit();
			driver.remove();
		}
		ExtentReportManager.unload();
	}

	@AfterSuite(alwaysRun = true)
	public void tearDownReport() {
		ExtentReportManager.getReportInstance().flush();
	}
}