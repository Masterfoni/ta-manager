package br.edu.ifpe.monitoria.testutils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
}
