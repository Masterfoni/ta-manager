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
		 DbUnitUtil.selecionaDataset(Dataset.AtaMensal);
         DbUnitUtil.inserirDados();
	}
	
	@Dado("^que o usuario esta logado como aluno monitor de um componente$")
	public void queOUsuarioEstaLogadoComoAlunoMonitorDeUmComponente() throws Throwable {
		LoginSteps.logar("joaovitor8891879@gmail.com", "");
	}

	@Dado("^esteja na pagina minha monitoria$")
	public void estejaNaPaginaMinhaMonitoria() throws Throwable {
		BrowserManager.driver.get("http://localhost:8080/gem/aluno/minhaMonitoria.xhtml");
	}
	
	@Quando("^clicar em registrar atividade$")
	public void clicarEmRegistrarAtividade() throws Throwable {
		BrowserManager.driver.findElement(By.id("registrarAtividade")).click();
		BrowserManager.esperar(750);
	}
	
	@Quando("^preencher com as informacoes da atividade$")
	public void preencherComAsInformacoesDaAtividade() throws Throwable {
		BrowserManager.driver.findElement(By.id("dataRA")).sendKeys("2019-02-02");
		BrowserManager.driver.findElement(By.id("entradaRA")).sendKeys("10:00");
		BrowserManager.driver.findElement(By.id("saidaRA")).sendKeys("12:00");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("cadastro:atividadeRA")).sendKeys("MINHAS ATIVIDADES");
		BrowserManager.esperar(750);
		BrowserManager.driver.findElement(By.id("cadastro:observacaoRA")).sendKeys("MINHAS OBSERVACOES");
		BrowserManager.esperar(2500);
	}
	
	@Quando("^clicar em registrar$")
	public void clicarEmRegistrar() throws Throwable {
		BrowserManager.driver.findElement(By.id("cadastro:cadastrar")).click();
	}
	
	@Entao("^o sistema deve registrar a atividade$")
	public void oSistemaDeveRegistrarAAtividade() throws Throwable {
		BrowserManager.driver.findElement(By.id("formTableAtvidades")).getText().contains("Dia: 02/02/2019");
		LoginSteps.deslogar();
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
		assertEquals("A data da atividade precisa ser no periodo da monitoria. Entre 02/02/2019 e 02/08/2019", texto.get(0).getText());
		LoginSteps.deslogar();
	}

	@Quando("^preencher com horas inconsistentes$")
	public void preencherComHorasInconsistentes() throws Throwable {
		BrowserManager.driver.findElement(By.id("dataRA")).sendKeys("2019-02-02");
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
