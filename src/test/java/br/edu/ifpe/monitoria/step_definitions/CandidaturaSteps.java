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

public class CandidaturaSteps {
	
	public CandidaturaSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.CandidaturaCucumber) {
			 DbUnitUtil.selecionaDataset(Dataset.CandidaturaCucumber);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como aluno$")
	public void queOUsuarioEstaLogadoComoAluno() throws Throwable {
	    LoginSteps.logar("gemteste1@gmail.com", "");
	}

	@Dado("^esteja na pagina de inscricao$")
	public void estejaNaPaginaDeInscricao() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/aluno/inscricaoMonitoria.xhtml");
	}

	@Dado("^se inscrever para monitoria em um componente curricular$")
	public void seInscreverParaMonitoriaEmUmComponenteCurricular() throws Throwable {
		BrowserManager.driver.findElement(By.id("escolhaplano:repeatPlanos:0:botaoBolsista")).click();
	}

	@Entao("^o aluno se candidata a monitoria$")
	public void oAlunoSeCandidataAMonitoria() throws Throwable {
		WebElement inscrito = BrowserManager.driver.findElement(By.id("inscrito"));
		List <WebElement> texto = inscrito.findElements(By.tagName("p"));
		assertEquals("Inscrição atual", texto.get(0).getText());
		
		assertEquals("Componente curricular: Trabalho de Conclusão de Curso\n" + 
				"Turno: NOTURNO\n" + 
				"Modalidade: Bolsista", texto.get(1).getText());
	}
	
}
