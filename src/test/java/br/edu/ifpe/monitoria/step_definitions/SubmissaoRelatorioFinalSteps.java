package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class SubmissaoRelatorioFinalSteps {
	
	public SubmissaoRelatorioFinalSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.SubmissaoRelatorioFinalCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.SubmissaoRelatorioFinalCucumber);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como aluno monitor$")
	public void queOUsuarioEstaLogadoComoAlunoMonitor() throws Throwable {
		LoginSteps.logar("gemteste1@gmail.com", "");
	}

	@Dado("^esteja na pagina de relatorio final$")
	public void estejaNaPaginaDeSubmissaoDePlanoDeMonitoria() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/aluno/relatorioFinal.xhtml");
	}

	@Quando("^preencher informacoes sobre atividades desempenhadas$")
	public void preencherInformacoesSobreAtividadesDesempenhadas() throws Throwable {
		BrowserManager.driver.findElement(By.id("formRelatorioFinal:atividades")).sendKeys("MINHAS ATIVIDADES");
	}
	
	@Quando("^preencher informacoes sobre suas dificuldades$")
	public void preencherInformacoesSobreSuasDificuldades() throws Throwable {
		BrowserManager.driver.findElement(By.id("formRelatorioFinal:dificuldades")).sendKeys("MINHAS DIFICULDADES");
	}
	
	@Quando("^preencher informacoes sobre sugestoes de melhoria$")
	public void preencherInformacoesSobreSugestoesDeMelhoria() throws Throwable {
		BrowserManager.driver.findElement(By.id("formRelatorioFinal:sugestoes")).sendKeys("MINHAS SUGESTOES");
	}
	
	@Quando("^avaliar o orientador salvando o relatorio$")
	public void avaliarOOrientadorSalvandoORelatorio() throws Throwable {
		BrowserManager.driver.findElement(By.id("formRelatorioFinal:avaliacao")).sendKeys("8");
		BrowserManager.driver.findElement(By.id("formRelatorioFinal:btnSalvar")).click();
		
		BrowserManager.esperar(3000);
	}

	@Entao("^o sistema deve atualizar o unico relatorio final$")
	public void oSistemaDeveAtualizarOUnicoRelatorioFinal() throws Throwable {
		assertEquals("Atividades MINHAS ATIVIDADES", BrowserManager.driver.findElement(By.id("formRelatorioFinal:atividades")).getText());
		assertEquals("Dificuldades MINHAS DIFICULDADES", BrowserManager.driver.findElement(By.id("formRelatorioFinal:dificuldades")).getText());
		assertEquals("Sugestoes MINHAS SUGESTOES", BrowserManager.driver.findElement(By.id("formRelatorioFinal:sugestoes")).getText());
		assertEquals("8", BrowserManager.driver.findElement(By.id("formRelatorioFinal:avaliacao")).getAttribute("value"));
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
	
	@Quando("^preencher a avaliacao do orientador$")
	public void preencherAAvaliacaoDoOrientador() throws Throwable {
		BrowserManager.driver.findElement(By.id("formRelatorioFinal:avaliacao")).sendKeys("15");
	}
	
	@Quando("^a avaliacao for invalida$")
	public void aAvaliacaoForInvalida() throws Throwable {
		assertTrue(Integer.parseInt(BrowserManager.driver.findElement(By.id("formRelatorioFinal:avaliacao")).getAttribute("value")) > 10);
		BrowserManager.driver.findElement(By.id("formRelatorioFinal:btnSalvar")).click();
		BrowserManager.esperar(3000);
	}
	
	@Entao("^o sistema deve exibir uma mensagem informando os limites de nota$")
	public void oSistemaDeveExibirUmaMensagemInformandoOsLimitesDeNota() throws Throwable {
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("Avaliação deve ser igual ou inferior à 10!", lista.findElement(By.tagName("li")).getText());
	}

}