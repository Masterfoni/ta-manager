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

public class PlanoMonitoriaBolsasSteps {
	
	public PlanoMonitoriaBolsasSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.PlanoMonitoriaBolsasCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.PlanoMonitoriaBolsasCucumber);
	         DbUnitUtil.inserirDados();
		}
	}

	@Dado("^que o usuario esta logado como comissao$")
	public void queOUsuarioEstaLogadoComoComissao() throws Throwable {
		LoginSteps.logar("fal@a.recife.ifpe.edu.br", "");
	}

	@Dado("^esteja na pagina de edital$")
	public void estejaNaPaginaDeEdital() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/admin/gerenciaEdital.xhtml");
	}
	
	@Dado("^crie um edital valido$")
	public void crieUmEditalValido() throws Throwable {
		BrowserManager.driver.findElement(By.className("cadastrador")).click();
		
		BrowserManager.driver.findElement(By.id("formcadastro:numeroCadastroE")).sendKeys("2");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("formcadastro:anoCadastroE")).sendKeys("2019");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("iniCompE")).sendKeys("2019-02-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("fimCompE")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("iniPME")).sendKeys("2019-02-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("fimPME")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("iniProvaE")).sendKeys("2019-02-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("fimProvaE")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("iniAlunoE")).sendKeys("2019-02-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("fimAlunoE")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("iniNotaE")).sendKeys("2019-02-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("fimNotaE")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("publiClassE")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("publiSelecE")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("iniMonE")).sendKeys("2019-02-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("fimMonE")).sendKeys("2019-06-02");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("formcadastro:notaSelecaoE")).sendKeys("7.0");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("formcadastro:mediaComponenteE")).sendKeys("7.0");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("formcadastro:btnCadastrar")).click();
		
		BrowserManager.esperar(5000);
		
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("Cadastro realizado com sucesso!", lista.findElement(By.tagName("li")).getText());
	}
	
	@Quando("^gerenciar um edital especifico$")
	public void gerenciarUmEditalEspecifico() throws Throwable {
		BrowserManager.driver.findElement(By.className("colapsador")).click();
		BrowserManager.esperar(1000);
	}
	
	@Quando("^definir quantidade de bolsas para um curso$")
	public void definirQuantidadeDeBolsasParaUmCurso() throws Throwable {
		BrowserManager.driver.findElement(By.id("globalform:repeatEsquemas:0:bInputter")).sendKeys("2");
		BrowserManager.driver.findElement(By.id("globalform:repeatEsquemas:0:atualizaBolsasBtn")).click();
	}
	
	@Entao("^o sistema deve especificar aquela quantidade para o esquema$")
	public void oSistemaDeveEspecificarAquelaQuantidadeParaOEsquema() throws Throwable {
		BrowserManager.esperar(5000);
		assertEquals("2", BrowserManager.driver.findElement(By.id("globalform:repeatEsquemas:0:bInputter")).getAttribute("value"));
		BrowserManager.esperar(1000);
		DbUnitUtil.inserirDados();
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
	
	@Quando("^ja existir um lancamento criado para determinado curso$")
	public void jaExistirUmLancamentoCriadoParaDeterminadoCurso() throws Throwable {
		assertTrue(BrowserManager.driver.findElement(By.id("esquemaTituloCC")).getText().contains("Tecnologia em Análise e Desenvolvimento de Sistemas"));
	}

	@Quando("^escolher lancar bolsas para aquele curso$")
	public void escolherLancarBolsasParaAqueleCurso() throws Throwable {
  		BrowserManager.driver.findElement(By.id("esquemasForm:botaoCriaEsquema")).click();
  		BrowserManager.esperar(1000);
	}	
	  	
	
	@Entao("^o sistema informa que ja existe um esquema criado$")
	public void oSistemaInformaQueJaExisteUmEsquemaCriado() throws Throwable {
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("Já existe um esquema de bolsas deste curso para este edital!", lista.findElement(By.tagName("li")).getText());
		BrowserManager.esperar(1000);
		DbUnitUtil.inserirDados();
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}

	@Quando("^existirem cursos sem bolsas explicitamente lancadas$")
	public void existiremCursosSemBolsasExplicitamenteLancadas() throws Throwable {
		BrowserManager.driver.findElement(By.className("colapsador")).click();
		BrowserManager.esperar(1000);
		assertEquals("0", BrowserManager.driver.findElement(By.id("globalform:repeatEsquemas:0:bInputter")).getAttribute("value"));
	}

	@Quando("^definir um edital como vigente$")
	public void definirUmEditalComoVigente() throws Throwable {
  		BrowserManager.driver.findElement(By.id("repeatEditais:0:formEditais:botaoUpdate")).click();
		BrowserManager.esperar(1000);
  		BrowserManager.driver.findElement(By.id("formAtualizador:vigente")).click();
  		BrowserManager.driver.findElement(By.name("formAtualizador:salvarAlteracao")).click();
	}	
	
	@Entao("^o sistema deve informar que se deve definir uma quantidade de bolsas$")
	public void oSistemaDeveInformarQueSeDeveDefinirUmaQuantidadeDeBolsas() throws Throwable {
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("Você deve explicitar o número de bolsas para cada curso!", lista.findElement(By.tagName("li")).getText());
		BrowserManager.esperar(1000);
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
}
