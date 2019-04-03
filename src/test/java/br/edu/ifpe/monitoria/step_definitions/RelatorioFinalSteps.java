package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class RelatorioFinalSteps {

	public RelatorioFinalSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.RelatorioFinal) {
			 DbUnitUtil.selecionaDataset(Dataset.RelatorioFinal);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como um usuario professor$")
	public void queOUsuarioEstaLogadoComoUmUsuarioProfessor() throws Throwable {
		LoginSteps.logar("fal@a.recife.ifpe.edu.br", "");
	}

	@Dado("^esteja na homepage$")
	public void estejaNaHomepage() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/comum/homepage.xhtml");
	}
	
	@Quando("^selecionar o componente curricular do relatorio final$")
	public void selecionarOMesParaAGeracaoDoRelatorio() throws Throwable {
		Select cc = new Select(BrowserManager.driver.findElement(By.id("formRelatorioFinal:componenteFinalSelector")));
		cc.selectByIndex(0);
	}
	
	@Quando("^clicar para gerar o relatorio final$")
	public void clicarParaGerarORelatorioFinal() throws Throwable {
		BrowserManager.driver.findElement(By.id("botaoRelatorioFinal")).click();
		BrowserManager.esperar(5000);
	}
	
	@Entao("^o sistema deve mostrar quais monitores entregaram ou nao o relatorio final de monitoria$")
	public void oSistemaDeveMostrarQuaisMonitoresEntregaramOuNaoORelatorioFinalDeMonitoria() throws Throwable {
		List<WebElement> alunos = BrowserManager.driver.findElements(By.tagName("td"));
		assertTrue(alunos.size() == 4);
	}
}

