package br.edu.ifpe.monitoria.step_definitions;

import org.openqa.selenium.By;

import br.edu.ifpe.monitoria.testutils.BrowserManager;

public class LoginSteps {
	public static void logar(String login, String senha) {
		BrowserManager.openFirefox("http://localhost:8080/welcome.xhtml");
		BrowserManager.esperar(5000);
		
		BrowserManager.driver.findElement(By.id("customBtn")).click();
		BrowserManager.esperar(5000);
		
		String mainHandle = BrowserManager.driver.getWindowHandle();
		String[] handles = BrowserManager.driver.getWindowHandles().toArray(new String[0]);
		BrowserManager.driver.switchTo().window(handles[handles.length - 1]);
		
		BrowserManager.driver.findElement(By.id("identifierId")).sendKeys(login);
		BrowserManager.driver.findElement(By.id("identifierNext")).click();
		BrowserManager.esperar(5000);
		
		BrowserManager.driver.findElement(By.name("password")).sendKeys(senha);
		BrowserManager.driver.findElement(By.id("passwordNext")).click();
		BrowserManager.esperar(5000);
		
		BrowserManager.driver.switchTo().window(mainHandle);
	}
}
