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

public class PlanoDeMonitoriaBolsasSteps {
	
	public PlanoDeMonitoriaBolsasSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.PlanoMonitoriaBolsasCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.PlanoMonitoriaBolsasCucumber);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como comissao$")
	public void queOUsuarioEstaLogadoComoProfessor() throws Throwable {
		LoginSteps.logar(LoginSteps.Tipo.PROFESSOR, "fal@a.recife.ifpe.edu.br", "");
	}

	@Dado("^esteja na pagina de edital$")
	public void estejaNaPaginaDeEdital() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/admin/gerenciaEdital.xhtml");
	}

	@Dado("^gerencie bolsas$")
	public void gerencieBolsas() throws Throwable {
		BrowserManager.driver.findElement(By.className("colapsador")).click();
	}
	
	@Quando("^escolher lançar bolsas para um determinado curso$")
	public void escolherLançarBolsasParaUmDeterminadoCurso() throws Throwable {
		BrowserManager.driver.findElement(By.id("esquemasForm:botaoCriaEsquema")).click();
	}
		
	@Quando("^nao existir lançamento criado para aquele curso")
	public void naoExistirLançamentoCriadoParaAqueleCurso() throws Throwable {
		WebElement selectCurso = BrowserManager.driver.findElement(By.className("teste"));
		WebElement cardCurso = BrowserManager.driver.findElement(By.tagName("ul"));
	}
	
	@Entao("^o sistema deve criar um novo lançamento de bolsas$")
	public void oSistemaDeveCriarUmNovoLançamentoDeBolsas() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/admin/gerenciaEdital.xhtml");
		BrowserManager.driver.findElement(By.className("colapsador")).click();
		WebElement selectCurso = BrowserManager.driver.findElement(By.className("teste"));
		WebElement cardCurso = BrowserManager.driver.findElement(By.tagName("ul"));
	}
	
}
