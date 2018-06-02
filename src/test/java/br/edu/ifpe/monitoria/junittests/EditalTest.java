package br.edu.ifpe.monitoria.junittests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.testutils.JUnitUtils;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EditalTest extends JUnitUtils {

	private static EJBContainer container;

	@EJB
	private EditalLocalBean editalbean;

	@BeforeClass
	public static void setUpClass() throws Exception {
		container = JUnitUtils.buildContainer();
	}

	@Before
	public void setUp() throws NamingException {
		editalbean = (EditalLocalBean) JUnitUtils.getLocalBean(container, "EditalLocalBean");
	}

	@Test
	public void t01_criarEdital() throws Exception {
		Edital edital = new Edital();
		edital.setAno(2020);
		edital.setNumero(999999);
		edital.setNumeroEdital("999999/2020");
		edital.setFimInscricaoComponenteCurricular(new Date());
		edital.setFimInscricaoEstudante(new Date());
		edital.setFimInsercaoNota(new Date());
		edital.setFimInsercaoPlano(new Date());
		edital.setFimMonitoria(new Date());
		edital.setInicioInscricaoComponenteCurricular(new Date());
		edital.setInicioInscricaoEstudante(new Date());
		edital.setInicioInsercaoNota(new Date());
		edital.setInicioInsercaoPlano(new Date());
		edital.setInicioMonitoria(new Date());
		edital.setNotaMinimaSelecao(7.0);
		edital.setMediaMinimaCC(7.0);
		edital.setVigente(true);

		editalbean.persisteEdital(edital);
		assertNotNull(edital.getId());
	}
	
	@Test
	public void t02_consultaEditais() throws Exception {
		assertTrue(editalbean.consultaEditais().size() > 0);
	}
	
	@Test
	public void t03_alterarEdital() throws Exception {
		Edital edital = editalbean.consultaEditalByNumero("999999/2020");
		edital.setVigente(false);
		
		editalbean.atualizaEdital(edital);
		
		edital = editalbean.consultaEditalByNumero("999999/2020");
		
		assertFalse(edital.isVigente());
	}
	
	@Test
	public void t04_deletarEdital() throws Exception {
		Edital edital = editalbean.consultaEditalByNumero("999999/2020");
		
		DelecaoRequestResult resultado = editalbean.deletaEdital(edital.getId());
		
		assertFalse(resultado.hasErrors());
	}

	@AfterClass
	public static void tearDownClass() {
		container.close();
	}
}