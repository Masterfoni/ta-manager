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

public class RelatorioFrequenciaMensalSteps {

	public RelatorioFrequenciaMensalSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.RelatorioFrequenciaMensal) {
			 DbUnitUtil.selecionaDataset(Dataset.RelatorioFrequenciaMensal);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como um professor$")
	public void queOUsuarioEstaLogadoComoUmProfessor() throws Throwable {
		LoginSteps.logar("fal@a.recife.ifpe.edu.br", "FALaifpe//95");
	}

	@Dado("^esteja na pagina inicial$")
	public void estejaNaPaginaInicial() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/comum/homepage.xhtml");
	}
	
	@Quando("^selecionar o mes para a geracao do relatorio$")
	public void selecionarOMesParaAGeracaoDoRelatorio() throws Throwable {
		Select mes = new Select(BrowserManager.driver.findElement(By.id("formRelatorioMensal:mesSelector")));
		mes.selectByIndex(0);
	}
	
	@Quando("^selecionarOComponenteCurricular$")
	public void selecionarOComponenteCurricular() throws Throwable {
		Select cc = new Select(BrowserManager.driver.findElement(By.id("formRelatorioMensal:componenteSelector")));
		cc.selectByIndex(0);
	}
	
	@Quando("^clicar em gerar relatorio$")
	public void clicarEmGerarRelatorio() throws Throwable {
		BrowserManager.driver.findElement(By.id("botaoRelatorioMensal")).click();
		BrowserManager.esperar(5000);
	}
	
	@Entao("^o sistema deve mostrar a situacao de entrega de frequencia dos alunos$")
	public void oSistemaDeveMostrarASituacaoDeEntregaDeFrequenciaDosAlunos() throws Throwable {
		List<WebElement> alunos = BrowserManager.driver.findElements(By.tagName("td"));
		assertTrue(alunos.size() == 4);
	}
}

