package br.edu.ifpe.monitoria.step_definitions;

import org.openqa.selenium.By;

import br.edu.ifpe.monitoria.testutils.BrowserManager;

public class LoginSteps {
	
	public enum Tipo {
		PROFESSOR("professor"),
		ALUNO("aluno");
		
		private String label;
		
		private Tipo(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}
	
	public static void logar(Tipo tipo, String login, String senha) {
		
		switch (tipo) {
		case PROFESSOR:
			
			BrowserManager.openFirefox("http://localhost:8080/gem/publico/index.xhtml");
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
			break;

		case ALUNO:
			BrowserManager.openFirefox("http://localhost:8080/gem/publico/index.xhtml");
			BrowserManager.esperar(5000);
			BrowserManager.driver.findElement(By.id("loginform:emailUsuario")).sendKeys(login);
			BrowserManager.driver.findElement(By.id("loginform:senhaUsuario")).sendKeys(senha);
			BrowserManager.driver.findElement(By.id("loginform:loginaluno")).click();
			BrowserManager.esperar(3000);
			break;
		}
		
	}
	
}
