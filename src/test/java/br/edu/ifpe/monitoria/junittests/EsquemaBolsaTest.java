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

import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.EsquemaBolsa;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.entidades.Servidor.Titulacao;
import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.EsquemaBolsaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;
import br.edu.ifpe.monitoria.testutils.JUnitUtils;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;
import br.edu.ifpe.monitoria.utils.EsquemaBolsaRequestResult;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EsquemaBolsaTest {
	private static EJBContainer container;

	@EJB
	private UsuarioLocalBean usuariobean;

	@EJB
	private CursoLocalBean cursobean;

	@EJB
	private ServidorLocalBean servidorbean;
	
	@EJB
	private EditalLocalBean editalbean;
	
	@EJB
	private EsquemaBolsaLocalBean esquemabean;

	@BeforeClass
	public static void setUpClass() throws Exception 
	{
		container = JUnitUtils.buildContainer();
	}

	@Before
	public void setUp() throws NamingException 
	{
		usuariobean = (UsuarioLocalBean) JUnitUtils.getLocalBean(container, "UsuarioLocalBean");
		servidorbean = (ServidorLocalBean) JUnitUtils.getLocalBean(container, "ServidorLocalBean");
		cursobean = (CursoLocalBean) JUnitUtils.getLocalBean(container, "CursoLocalBean");
		editalbean = (EditalLocalBean) JUnitUtils.getLocalBean(container, "EditalLocalBean");
		esquemabean = (EsquemaBolsaLocalBean) JUnitUtils.getLocalBean(container, "EsquemaBolsaLocalBean");
	}

	@Test
	public void t01_criarEsquemaBolsa() throws Exception 
	{
		Servidor servidor = new Servidor();
		servidor.setTitulacao(Titulacao.DOUTORADO);
		servidor.setSiape(9999990);
		servidor.setCpf("569.529.179-85");
		servidor.setEmail("emailjack@a.recife.ifpe.edu.br");
		servidor.setNome("Jackson Five");
		servidor.setRg("7980520");
		servidor.setRgEmissor("SDS/PE");
		servidor.setSal("salzin");
		servidor.setSenha("draco123");
		servidor.setSexo("Masculino");

		servidorbean.persisteProfessor(servidor);
		assertNotNull(servidor.getId());

		Curso curso = new Curso();
		curso.setCoordenacao("COORDTEST");
		curso.setDepartamento("DPTOTEST");
		curso.setNome("CURSOTESTE");
		curso.setSigla("CTST");
		curso.setCoordenador(servidor);

		cursobean.persisteCurso(curso);
		assertNotNull(curso.getId());
		
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
		edital.setVigente(true);

		editalbean.persisteEdital(edital);
		assertNotNull(edital.getId());
		
		EsquemaBolsaRequestResult consultaEsquemaByEditalCurso = esquemabean.consultaEsquemaByEditalCurso(edital, curso);
		assertFalse(consultaEsquemaByEditalCurso.hasErrors());
	}

	@Test
	public void t02_alterarEsquemaBolsa() throws Exception 
	{
		EsquemaBolsa esquema = esquemabean.consultaEsquemaByEdital(editalbean.consultaEditalByNumero("999999/2020")).get(0);
		
		esquema.setQuantidade(2);
		esquemabean.atualizaEsquemaBolsa(esquema);

		esquema = esquemabean.consultaEsquemaByEdital(editalbean.consultaEditalByNumero("999999/2020")).get(0);

		assertTrue(esquema.getQuantidade().equals(2));
	}
 
	@Test
	public void t03_deletarEsquemaBolsa() throws Exception 
	{
		EsquemaBolsa esquema = esquemabean.consultaEsquemaByEdital(editalbean.consultaEditalByNumero("999999/2020")).get(0);

		DelecaoRequestResult delecaoResult = esquemabean.deletaEsquema(esquema.getId());
		assertFalse(delecaoResult.hasErrors());
		
		DelecaoRequestResult resultado = cursobean.deletaCurso(cursobean.consultaCursoByName("CURSOTESTE").getId());

		assertFalse(resultado.hasErrors());

		Usuario usuario = usuariobean.consultaUsuarioPorEmail("emailjack@a.recife.ifpe.edu.br");

		assertFalse(usuariobean.deletaUsuario(usuario.getId()).hasErrors());
		
		Edital edital = editalbean.consultaEditalByNumero("999999/2020");
		
		DelecaoRequestResult resultadoEdital = editalbean.deletaEdital(edital.getId());
		
		assertFalse(resultadoEdital.hasErrors());
	}

	@AfterClass
	public static void tearDownClass() 
	{
		container.close();
	}
}
