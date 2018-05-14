package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class PlanodeMonitoriaSteps {
	
	public PlanodeMonitoriaSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.PlanoMonitoriaCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.PlanoMonitoriaCucumber);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como professor$")
	public void queOUsuarioEstaLogadoComoProfessor() throws Throwable {
		LoginSteps.logar(LoginSteps.Tipo.PROFESSOR, "fal@a.recife.ifpe.edu.br", "");
	}

	@Dado("^esteja na pagina de submissao de plano de monitoria$")
	public void estejaNaPaginaDeSubmissaoDePlanoDeMonitoria() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/professor/gerenciaPlanoMonitoria.xhtml");
	}

	@Dado("^tenta submeter um novo plano de monitoria$")
	public void tentaSubmeterUmNovoPlanoDeMonitoria() throws Throwable {
		BrowserManager.driver.findElement(By.className("cadastrador")).click();
	}

	@Quando("^preencher o formulario de plano de monitoria com informacoes validas$")
	public void preencherOFormularioDePlanoDeMonitoriaComInformacoesValidas() throws Throwable {
		Select edital = new Select(BrowserManager.driver.findElement(By.id("cadastro:edital")));
		edital.selectByValue("1");
		Select cc = new Select(BrowserManager.driver.findElement(By.id("cadastro:cc")));
		cc.selectByValue("1");
		BrowserManager.driver.findElement(By.id("cadastro:nbolsistas")).sendKeys("1");
		BrowserManager.driver.findElement(By.id("cadastro:voluntarios")).sendKeys("2");
		BrowserManager.driver.findElement(By.id("cadastro:justificativa")).sendKeys("justificativa");
		BrowserManager.driver.findElement(By.id("cadastro:objetivo")).sendKeys("objetivo");
		BrowserManager.driver.findElement(By.id("cadastro:atividades")).sendKeys("atividades");
		BrowserManager.esperar(3000);
		BrowserManager.driver.findElement(By.id("cadastro:cadastrar")).click();
		BrowserManager.esperar(3000);
	}

	@Entao("^o sistema deve submeter o plano de monitoria$")
	public void oSistemaDeveSubmeterOPlanoDeMonitoria() throws Throwable {
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("Cadastro realizado com sucesso!", lista.findElement(By.tagName("li")).getText());
	}
	
}
