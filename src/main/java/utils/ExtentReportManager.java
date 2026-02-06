package utils;

import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	private ExtentReportManager() {
	}

	public static synchronized ExtentReports getReportInstance() {
		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timestamp + ".html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
			reporter.config().setDocumentTitle("Automation Test Report");
			reporter.config().setReportName("Test Execution Report");

			extent = new ExtentReports();
			extent.attachReporter(reporter);
		}
		return extent;
	}

	public static synchronized void createTest(String testName) {
		ExtentTest extentTest = getReportInstance().createTest(testName);
		test.set(extentTest);
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void unload() {
		test.remove();
	}

	public static String captureScreenshot(WebDriver driver, String screenshotName) {
		try {
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String path = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + timestamp + ".png";
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(path));

			return path;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
