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

public class AtaMensalSteps {

	public AtaMensalSteps() {
		if(DbUnitUtil.ultimo_executado != Dataset.AtaMensal) {
			 DbUnitUtil.selecionaDataset(Dataset.AtaMensal);
	         DbUnitUtil.inserirDados();
		}
	}
	
	@Dado("^que o usuario esta logado como aluno monitor de um componente$")
	public void queOUsuarioEstaLogadoComoAlunoMonitorDeUmComponente() throws Throwable {
		LoginSteps.logar(LoginSteps.Tipo.ALUNO, "danilo@gmail.com", "draco123");
	}

	@Dado("^esteja na pagina minha monitoria$")
	public void estejaNaPaginaMinhaMonitoria() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/aluno/minhaMonitoria.xhtml");
	}
	
	@Quando("^clicar em registrar atividade$")
	public void clicarEmRegistrarAtividade() throws Throwable {
		BrowserManager.driver.findElement(By.id("registrarAtividade")).click();
	}
	
	@Quando("^preencher com as informacoes da atividade$")
	public void preencherComAsInformacoesDaAtividade() throws Throwable {
		BrowserManager.driver.findElement(By.id("dataRA")).sendKeys("2018-02-02");
		BrowserManager.driver.findElement(By.id("entradaRA")).sendKeys("10:00");
		BrowserManager.driver.findElement(By.id("saidaRA")).sendKeys("12:00");
		BrowserManager.driver.findElement(By.id("cadastro:atividadeRA")).sendKeys("MINHAS ATIVIDADES");
		BrowserManager.driver.findElement(By.id("cadastro:observacaoRA")).sendKeys("MINHAS OBSERVACOES");
		BrowserManager.esperar(2500);
	}
	
	@Quando("^clicar em registrar$")
	public void clicarEmRegistrar() throws Throwable {
		BrowserManager.driver.findElement(By.id("cadastro:cadastrar")).click();
	}
	
	@Entao("^o sistema deve registrar a atividade$")
	public void oSistemaDeveRegistrarAAtividade() throws Throwable {
	    assertEquals("Dia: 02/02/2018 | Hora inicial: 10:00:00 | Hora final: 12:00:00" , 
	    		BrowserManager.driver.findElement(By.id("formTableAtvidades:repeatAtividades:0:dadosAtividade")).getText());
	    BrowserManager.esperar(2500);
	    BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}
	
	@Quando("^preencher com uma data fora do periodo de monitoria$")
	public void preencherComUmaDataForaDoPeriodoDeMonitoria() throws Throwable {
		BrowserManager.driver.findElement(By.id("dataRA")).sendKeys("2017-02-02");
		BrowserManager.driver.findElement(By.id("entradaRA")).sendKeys("10:00");
		BrowserManager.driver.findElement(By.id("saidaRA")).sendKeys("12:00");
		BrowserManager.driver.findElement(By.id("cadastro:atividadeRA")).sendKeys("MINHAS ATIVIDADES");
		BrowserManager.driver.findElement(By.id("cadastro:observacaoRA")).sendKeys("MINHAS OBSERVACOES");
		BrowserManager.esperar(2500);
	}

	@Entao("^o sistema deve exibir a mensagem de data fora do periodo$")
	public void oSistemaDeveExibirAMensagemDeDataForaDoPeriodo() throws Throwable {
		WebElement alert = BrowserManager.driver.findElement(By.className("alert"));
		List <WebElement> texto = alert.findElements(By.tagName("li"));
		assertEquals("A data da atividade precisa ser no periodo da monitoria. Entre 02/02/2018 e 02/08/2018", texto.get(0).getText());
		BrowserManager.esperar(2500);
	    BrowserManager.driver.findElement(By.id("navbar-top:logout")).click();
		BrowserManager.driver.close();
		BrowserManager.driver = null;
	}

	@Quando("^preencher com horas inconsistentes$")
	public void preencherComHorasInconsistentes() throws Throwable {
		BrowserManager.driver.findElement(By.id("dataRA")).sendKeys("2018-02-02");
		BrowserManager.driver.findElement(By.id("entradaRA")).sendKeys("10:00");
		BrowserManager.driver.findElement(By.id("saidaRA")).sendKeys("09:00");
		BrowserManager.driver.findElement(By.id("cadastro:atividadeRA")).sendKeys("MINHAS ATIVIDADES");
		BrowserManager.driver.findElement(By.id("cadastro:observacaoRA")).sendKeys("MINHAS OBSERVACOES");
		BrowserManager.esperar(2500);
	}

	@Entao("^o sistema deve exibir a mensagem de hora errada$")
	public void oSistemaDeveExibirAMensagemDeHoraErrada() throws Throwable {
		WebElement alert = BrowserManager.driver.findElement(By.className("alert"));
		List <WebElement> texto = alert.findElements(By.tagName("li"));
		assertEquals("A hora final da atividade deve ser depois da hora inicial.", texto.get(0).getText());
		BrowserManager.esperar(2500);
	}
}
