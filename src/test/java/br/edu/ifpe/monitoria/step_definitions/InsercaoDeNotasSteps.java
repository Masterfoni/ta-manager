package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class InsercaoDeNotasSteps {
	
	public InsercaoDeNotasSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.InsercaoDeNotasCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.InsercaoDeNotasCucumber);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado com perfil de professor$")
	public void queOUsuarioEstaLogadoComPerfilDeProfessor() throws Throwable {
		LoginSteps.logar("fal@a.recife.ifpe.edu.br", "");
	}

	@Dado("^esteja na pagina de gerencia dos planos de monitoria$")
	public void estejaNaPaginaDeGerenciaDosPlanosDeMonitoria() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/professor/gerenciaPlanoMonitoria.xhtml");
	}

	@Dado("^seleciona a opcao de inserir notas$")
	public void selecionaAOpcaoDeInserirNotas() throws Throwable {
		BrowserManager.driver.findElement(By.id("botaoNotas")).click();
	}

	@Quando("^o professor inserir as notas de selecao e as medias e salvar$")
	public void oProfessorInserirAsNotasDeSelecaoEAsMediasESalvar() throws Throwable {
		
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:0:notaSelecao")).sendKeys("7");
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:0:mediaComponente")).sendKeys("7");
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:1:notaSelecao")).sendKeys("8");
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:1:mediaComponente")).sendKeys("7");
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:2:notaSelecao")).sendKeys("8.5");
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:2:mediaComponente")).sendKeys("7");
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:3:notaSelecao")).sendKeys("9");
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:3:mediaComponente")).sendKeys("7");
		BrowserManager.driver.findElement(By.id("formNotas:salvar")).click();
	}

	@Entao("^deve mostrar a classificacao atualizada de cada aluno$")
	public void deveMostrarAClassificacaoAtualizadaDeCadaAluno() throws Throwable {
		List<WebElement> lista = BrowserManager.driver.findElements(By.tagName("tr"));
		
		List<WebElement> linha = lista.get(1).findElements(By.tagName("td"));
		assertEquals("1", linha.get(0).getText());
		assertEquals("Eduardo Amaral", linha.get(1).getText());
		
		linha = lista.get(2).findElements(By.tagName("td"));
		assertEquals("2", linha.get(0).getText());
		assertEquals("Raphael Almeida", linha.get(1).getText());
		
		linha = lista.get(3).findElements(By.tagName("td"));
		assertEquals("3", linha.get(0).getText());
		assertEquals("Iury Drayton", linha.get(1).getText());
		
		linha = lista.get(4).findElements(By.tagName("td"));
		assertEquals("4", linha.get(0).getText());
		assertEquals("Danilo Pereira", linha.get(1).getText());
		
		BrowserManager.esperar(5000);
		
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}

	@Quando("^o professor indicar que aluno possui reprovacao e salvar$")
	public void oProfessorIndicarQueAlunoPossuiReprovacaoESalvar() throws Throwable {
		BrowserManager.driver.findElement(By.id("formNotas:repeatPlanos:2:checkReprovacao")).click();
		BrowserManager.driver.findElement(By.id("formNotas:salvar")).click();
	}

	@Entao("^deve desclassificar o aluno$")
	public void deveDesclassificarOAluno() throws Throwable {
		List<WebElement> lista = BrowserManager.driver.findElements(By.tagName("tr"));
		List<WebElement> linha = lista.get(4).findElements(By.tagName("td"));
		assertEquals("", linha.get(0).getText());
		assertEquals("Iury Drayton", linha.get(1).getText());
	}
	
}
