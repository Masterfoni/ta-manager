package br.edu.ifpe.monitoria.step_definitions;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.edu.ifpe.monitoria.testutils.BrowserManager;
import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;
import cucumber.api.PendingException;
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
	    LoginSteps.logar(LoginSteps.Tipo.ALUNO, "danilo@gmail.com", "draco123");
	}

	@Dado("^esteja na pagina de inscricao$")
	public void estejaNaPaginaDeInscricao() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/aluno/inscricaoMonitoria.xhtml");
	}

	@Dado("^se inscrever para monitoria em um componente curricular$")
	public void seInscreverParaMonitoriaEmUmComponenteCurricular() throws Throwable {
		Select edital = new Select(BrowserManager.driver.findElement(By.id("escolhaedital:selectEdital")));
		edital.selectByValue("1");
		BrowserManager.driver.findElement(By.id("escolhaedital:selecionarEdital")).click();
		
		Select curso = new Select(BrowserManager.driver.findElement(By.id("escolhacurso:selectCurso")));
		curso.selectByValue("1");
		BrowserManager.driver.findElement(By.id("escolhacurso:selecionarCurso")).click();
		
		BrowserManager.driver.findElement(By.id("escolhaplano:repeatPlanos:0:btnBolsista")).click();
		
	}

	@Entao("^o aluno se candidata a monitoria$")
	public void oAlunoSeCandidataAMonitoria() throws Throwable {
		WebElement inscrito = BrowserManager.driver.findElement(By.id("inscrito"));
		List <WebElement> texto = inscrito.findElements(By.tagName("p"));
		assertEquals("Componente curricular: Trabalho de Conclusão de Curso", texto.get(0).getText());
		assertEquals("Turno: NOTURNO", texto.get(1).getText());
		assertEquals("Bolsista: Sim", texto.get(2).getText());
	}
	
}
