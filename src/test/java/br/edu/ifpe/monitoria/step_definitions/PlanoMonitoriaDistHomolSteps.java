package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class PlanoMonitoriaDistHomolSteps {
	
	public PlanoMonitoriaDistHomolSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.PlanoMonitoriaDistHomolCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.PlanoMonitoriaDistHomolCucumber);
	         DbUnitUtil.inserirDados();
		}
	}

	@Dado("^que o usuario esta logado como coordenador e comissao$")
	public void queOUsuarioEstaLogadoComoCoordenadorEComissao() throws Throwable {
		LoginSteps.logar("fal@a.recife.ifpe.edu.br", "");
	}

	@Dado("^esteja na pagina de gerencia de planos$")
	public void estejaNaPaginaDeGerenciaDePlanos() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/professor/gerenciaPlanoMonitoria.xhtml");
	}
	
	@Quando("^tentar diminuir o numero de bolsas de um plano para um numero invalido$")
	public void tentarDiminuirONomuroDeBolsasDeUmPlanoParaUmNumeroInvalido() throws Throwable {
		BrowserManager.driver.findElement(By.id("formTablePlanos:repeatPlanos:0:botaoRemoveBolsas")).click();
		BrowserManager.esperar(2000);
	}
	
	@Entao("^o sistema nao deve permitir a alteracao do plano$")
	public void oSistemaNaoDevePermitirAAlteracaoDoPlano() throws Throwable {
		assertEquals("0", BrowserManager.driver.findElement(By.name("formTablePlanos:repeatPlanos:0:bolsasPlano")).getAttribute("value"));
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
	
	@Quando("^existirem bolsas disponiveis o suficiente")
	public void existiremBolsasDisponiveisOSuficiente() throws Throwable {
		assertEquals("3", BrowserManager.driver.findElement(By.id("bolsasNumCucu")).getText());
	}
	
	@Quando("^tentar adicionar um numero valido de bolsas para um plano$")
	public void tentarAdicionarUmNumeroValidoDeBolsasParaUmPlano() throws Throwable {
		BrowserManager.driver.findElement(By.id("formTablePlanos:repeatPlanos:0:botaoAdicionaBolsas")).click();
		BrowserManager.esperar(1500);
		BrowserManager.driver.findElement(By.id("formTablePlanos:repeatPlanos:0:botaoAdicionaBolsas")).click();
		BrowserManager.esperar(3000);
	}
	
	@Entao("^o sistema deve realizar a distribuicao$")
	public void oSistemaDeveRealizarADistribuicao() throws Throwable {
		assertEquals("2", BrowserManager.driver.findElement(By.name("formTablePlanos:repeatPlanos:0:bolsasPlano")).getAttribute("value"));
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
	
	@Quando("^finalizar a distribuicao de bolsas$")
	public void finalizarADistribuicaoDeBolsas() throws Throwable {
		assertEquals("2", BrowserManager.driver.findElement(By.name("formTablePlanos:repeatPlanos:0:bolsasPlano")).getAttribute("value"));
	}
	
	@Quando("^homologar o plano de monitoria$")
	public void homologarOPlanoDeMonitoria() throws Throwable {
		BrowserManager.driver.findElement(By.id("formTablePlanos:repeatPlanos:0:botaoUpdate")).click();
		BrowserManager.esperar(1500);
		BrowserManager.driver.findElement(By.name("formAtualizador:homologar")).click();
		BrowserManager.esperar(1500);		
	}
	
	@Entao("^o sistema deve atualizar o status do plano para homologado$")
	public void oSistemaDeveAtualizarOStatusDoPlanoParaHomologado() throws Throwable {
		assertEquals("HOMOLOGADO", BrowserManager.driver.findElement(By.name("statusHomolCucu")).getText());
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
}
