package br.edu.ifpe.monitoria.testutils;

import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserManager {
	public static WebDriver driver;

    public static void openFirefox(String url)
    {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        if (driver == null)
        {
            driver = new FirefoxDriver();
            driver.manage().window();
        }
        driver.get(url);
    }

	public static void esperar(int millis) {
		Wait<WebDriver> wait = new WebDriverWait(BrowserManager.driver, 30);
	    wait.until(new Function<WebDriver, Boolean>() {
	        public Boolean apply(WebDriver driver) {
	        	long end = System.currentTimeMillis() + millis;
	        	while (System.currentTimeMillis() < end) { }
	        	return true;
	        }
	    });
	}
}
