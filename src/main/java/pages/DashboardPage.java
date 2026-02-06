package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

	private WebDriver driver;
	private WebDriverWait wait;

	// =================== LOCATORS ===================

	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement searchBox;

	@FindBy(xpath = "//ul[contains(@class,'oxd-main-menu')]//a[contains(@class,'oxd-main-menu-item')]//span")
	private List<WebElement> menuItems;

	@FindBy(xpath = "//p[contains(@class,'oxd-userdropdown-name')]")
	private WebElement profileDropdown;

	@FindBy(xpath = "//a[@class='oxd-userdropdown-link' and normalize-space()='About']")
	private WebElement aboutOption;

	@FindBy(xpath = "//a[@class='oxd-userdropdown-link' and normalize-space()='Support']")
	private WebElement supportOption;

	@FindBy(xpath = "//a[@class='oxd-userdropdown-link' and contains(@href,'updatePassword')]")
	private WebElement changePasswordOption;

	@FindBy(xpath = "//a[@class='oxd-userdropdown-link' and contains(@href,'logout')]")
	private WebElement logoutOption;

	// =================== CONSTRUCTOR ===================

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	// =================== COMMON WAITS ===================

	private void waitForDashboardToLoad() {
		wait.until(ExpectedConditions.visibilityOf(searchBox));
		wait.until(ExpectedConditions.visibilityOf(profileDropdown));
	}

	// =================== SEARCH ===================

	public void searchAndSelect(String moduleName) {
	    waitForDashboardToLoad();
	    
	    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    searchBox.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
	    searchBox.sendKeys(moduleName);

	    By menuItemLocator = By.xpath(
	        "//ul[contains(@class,'oxd-main-menu')]//span[contains(@class,'oxd-main-menu-item--name')]"
	    );

	    // Wait until menu is re-rendered after search
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(menuItemLocator));

	    List<WebElement> freshMenuItems = driver.findElements(menuItemLocator);

	    for (WebElement item : freshMenuItems) {
	        if (item.getText().trim().equalsIgnoreCase(moduleName)) {
	            wait.until(ExpectedConditions.elementToBeClickable(item)).click();
	            return;
	        }
	    }

	    throw new RuntimeException("Menu item not found after search: " + moduleName);
	}


	// =================== PROFILE DROPDOWN ===================

	public void openProfileDropdown() {
		waitForDashboardToLoad();
		wait.until(ExpectedConditions.elementToBeClickable(profileDropdown)).click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(logoutOption));
		} catch (Exception e) {
			profileDropdown.click();
			wait.until(ExpectedConditions.elementToBeClickable(logoutOption));
		}
	}

	public boolean areProfileOptionsVisible() {
		try {
			openProfileDropdown();
			return aboutOption.isDisplayed() && supportOption.isDisplayed() && changePasswordOption.isDisplayed()
					&& logoutOption.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// =================== ACTIONS ===================

	public void clickLogout() {
		openProfileDropdown();
		wait.until(ExpectedConditions.elementToBeClickable(logoutOption)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
	}

	public void clickChangePassword() {
		openProfileDropdown();
		wait.until(ExpectedConditions.elementToBeClickable(changePasswordOption)).click();
	}

	public void clickSupport() {
		openProfileDropdown();
		wait.until(ExpectedConditions.elementToBeClickable(supportOption)).click();
	}
}
