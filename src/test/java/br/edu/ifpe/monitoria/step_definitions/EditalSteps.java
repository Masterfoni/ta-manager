package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
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
		LoginSteps.logar(LoginSteps.Tipo.PROFESSOR, "fal@a.recife.ifpe.edu.br", "draco123#?");
	}

	@Dado("^esteja na pagina de gerencia de editais$")
	public void estejaNaPaginaDeGerenciaDeEditais() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/admin/gerenciaEdital.xhtml");
	}

	@Dado("^tenta criar um novo edital$")
	public void tentaCriarUmNovoEdital() throws Throwable {
		BrowserManager.driver.findElement(By.className("cadastrador")).click();
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
		BrowserManager.driver.findElement(By.id("formcadastro:btnCadastrar")).click();
		BrowserManager.esperar(5000);
	}

	@Quando("^informar periodos de datas inconsistentes$")
	public void informarPeriodosDeDatasInconsistentes() throws Throwable {
		BrowserManager.driver.findElement(By.id("formcadastro:numeroCadastroE")).sendKeys("2");
		BrowserManager.driver.findElement(By.id("formcadastro:anoCadastroE")).sendKeys("2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniCompE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimCompE")).sendKeys("02-03-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniPME")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimPME")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniAlunoE")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimAlunoE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniNotaE")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimNotaE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:iniMonE")).sendKeys("02-02-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:fimMonE")).sendKeys("02-06-2018");
		BrowserManager.driver.findElement(By.id("formcadastro:btnCadastrar")).click();
		BrowserManager.esperar(5000);
	}

	@Entao("^o sistema deve criar um novo edital$")
	public void oSistemaDeveCriarUmNovoEdital() throws Throwable {
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("Cadastro realizado com sucesso!", lista.findElement(By.tagName("li")).getText());
		BrowserManager.esperar(5000);
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
	

	@Entao("^o sistema informa que a data final de um periodo nao pode ser antes do inicio$")
	public void oSistemaInformaQueADataFinalDeUmPeriodoNaoPodeSerAntesDoInicio() throws Throwable {
		WebElement mensagem = BrowserManager.driver.findElement(By.className("alert"));
		WebElement lista = mensagem.findElement(By.tagName("ul"));
		assertEquals("A data para o fim de Inserção do Componente Curricular deve ser depois da data de início.", lista.findElement(By.tagName("li")).getText());
		BrowserManager.esperar(5000);
//		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
//		BrowserManager.driver.close();
//		BrowserManager.driver = null;
	}


}
