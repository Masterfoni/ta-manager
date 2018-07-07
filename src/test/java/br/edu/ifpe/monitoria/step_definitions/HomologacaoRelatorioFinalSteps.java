package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class HomologacaoRelatorioFinalSteps {
	
	public HomologacaoRelatorioFinalSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.HomologacaoRelatorioFinalCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.HomologacaoRelatorioFinalCucumber);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como professor orientador$")
	public void queOUsuarioEstaLogadoComoProfessorOrientador() throws Throwable {
		LoginSteps.logar(LoginSteps.Tipo.PROFESSOR, "fal@a.recife.ifpe.edu.br", "draco123#?");
	}

	@Dado("^esteja na pagina de gerencia de relatorios finais$")
	public void estejaNaPaginaDeGerenciaDeRelatoriosFinais() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/professor/gerenciaRelatoriosFinais.xhtml");
	}

	@Quando("^visualizar os detalhes de um relatorio final$")
	public void visualizarOsDetalhesDeUmRelatorioFinal() throws Throwable {
		BrowserManager.driver.findElement(By.id("formInfoMonitor:btnDetalhes")).click();
		BrowserManager.esperar(1000);
	}
	
	@Quando("^homologar um relatorio final$")
	public void homologarUmRelatorioFinal() throws Throwable {
		BrowserManager.driver.findElement(By.id("formDetalhes:btnHomologar")).click();
		BrowserManager.esperar(2000);
	}
	
	@Entao("^o sistema deve concretizar a homologacao$")
	public void oSistemaDeveAtualizarOUnicoRelatorioFinal() throws Throwable {
		assertEquals("O relatório deste monitor já se encontra homologado!", BrowserManager.driver.findElement(By.id("alreadyHomologado")).getText());
		
		BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
}
