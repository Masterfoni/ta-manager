package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class EditalSteps {

	public EditalSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.EditalCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.EditalCucumber);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado com perfil da comissao$")
	public void queOUsuarioEstaLogadoComPerfilDaComissao() throws Throwable {
		
		BrowserManager.openFirefox("http://localhost:8080/gem/publico/index.xhtml");
		esperar(5000);
		
		WebElement loginServidor = BrowserManager.driver.findElement(By.id("customBtn"));
		loginServidor.click();
		esperar(5000);
		
		String mainHandle = BrowserManager.driver.getWindowHandle();
		String[] handles = BrowserManager.driver.getWindowHandles().toArray(new String[0]);
		BrowserManager.driver.switchTo().window(handles[handles.length - 1]);
		
		WebElement email = BrowserManager.driver.findElement(By.id("identifierId"));
		email.sendKeys("fal@a.recife.ifpe.edu.br");
		
		WebElement next = BrowserManager.driver.findElement(By.id("identifierNext"));
		next.click();
		esperar(5000);
		
		WebElement pass = BrowserManager.driver.findElement(By.name("password"));
		pass.sendKeys("");
		WebElement passnext = BrowserManager.driver.findElement(By.id("passwordNext"));
		passnext.click();
		esperar(5000);
		BrowserManager.driver.switchTo().window(mainHandle);
	}

	@Dado("^esteja na pagina de gerencia de editais$")
	public void estejaNaPaginaDeGerenciaDeEditais() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/admin/gerenciaEdital.xhtml");
	}

	@Dado("^tenta criar um novo edital$")
	public void tentaCriarUmNovoEdital() throws Throwable {
		WebElement criarEdital = BrowserManager.driver.findElement(By.className("cadastrador"));
		criarEdital.click();
	}

	@Quando("^preencher o formulario com informacoes validas$")
	public void preencherOFormularioComInformacoesValidas() throws Throwable {
		BrowserManager.driver.findElement(By.id("formcadastro:numeroCadastroE")).sendKeys("2");
		BrowserManager.driver.findElement(By.id("formcadastro:anoCadastroE")).sendKeys("2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniCompE")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimCompE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniPME")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimPME")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniAlunoE")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimAlunoE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniNotaE")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimNotaE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniMonE")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimMonE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:vigenteE")).click();
		BrowserManager.driver.findElement(By.id("formcadastro:btnCadastrar")).click();
		esperar(5000);
	}

	@Entao("^o sistema deve criar um novo edital$")
	public void oSistemaDeveCriarUmNovoEdital() throws Throwable {
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("Cadastro realizado com sucesso!", lista.findElement(By.tagName("li")).getText());
	}
	
	private void esperar(long millis) {
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
