package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitManager {
	private static final int DEFAULT_TIMEOUT = 30;
	
	private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
	
	private WaitManager() {}
	
	public static WebDriverWait getWait(WebDriver driver) {
		if(wait.get() == null) {
			wait.set(new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT)));
		}
		return wait.get();
	}
	
	public static void removeWait() {
		wait.remove();
	}
}
