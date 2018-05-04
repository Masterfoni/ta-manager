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

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular.Turno;
import br.edu.ifpe.monitoria.entidades.Servidor.Titulacao;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;
import br.edu.ifpe.monitoria.testutils.JUnitUtils;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MonitoriaTest 
{
	private static EJBContainer container;

	@EJB
	private UsuarioLocalBean usuariobean;
	
	@EJB
	private CursoLocalBean cursobean;
	
	@EJB
	private ServidorLocalBean servidorbean;
	
	@EJB
	private ComponenteCurricularLocalBean componentebean;
	
	@EJB
	private EditalLocalBean editalbean;
	
	@EJB
	private PlanoMonitoriaLocalBean planobean;
	
	@EJB
	private MonitoriaLocalBean monitoriabean;
	
	@EJB
	private AlunoLocalBean alunobean;

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
		componentebean = (ComponenteCurricularLocalBean) JUnitUtils.getLocalBean(container, "ComponenteCurricularLocalBean");
		planobean = (PlanoMonitoriaLocalBean) JUnitUtils.getLocalBean(container, "PlanoMonitoriaLocalBean");
		editalbean = (EditalLocalBean) JUnitUtils.getLocalBean(container, "EditalLocalBean");
		monitoriabean = (MonitoriaLocalBean) JUnitUtils.getLocalBean(container, "MonitoriaLocalBean");
		alunobean = (AlunoLocalBean) JUnitUtils.getLocalBean(container, "AlunoLocalBean");
	}

	@Test
	public void t01_criarMonitoria() throws Exception 
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
		
		ComponenteCurricular cc = new ComponenteCurricular();
		cc.setCargaHoraria(52);
		cc.setCodigo("JACMT");
		cc.setCurso(curso);
		cc.setNome("TEORIA SINFONICA");
		cc.setPeriodo("2018/2");
		cc.setProfessor(servidor);
		cc.setTurno(Turno.NOTURNO);
		
		componentebean.persisteComponenteCurricular(cc);
		
		assertNotNull(cc.getId());
		
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
		
		PlanoMonitoria plano = new PlanoMonitoria();
		
		plano.setBolsas(2);
		plano.setCc(cc);
		plano.setEdital(edital);
		plano.setJustificativa("eu quis");
		plano.setListaAtividades("ALGUMAS COISAS");
		plano.setObjetivo("OBJECTIVE");
		plano.setVoluntarios(2);
		
		planobean.persistePlanoMonitoria(plano);
		assertNotNull(plano.getId());
		
		Aluno aluno = new Aluno();
		aluno.setCurso(curso);
		aluno.setMatricula("20132Y6-RC9999");
		aluno.setCpf("614.340.824-66");
		aluno.setEmail("emailjackaluno@a.recife.ifpe.edu.br");
		aluno.setNome("Jackson Five Aluno");
		aluno.setRg("0980520");
		aluno.setRgEmissor("SDS/PE");
		aluno.setSal("salzin");
		aluno.setSenha("draco123");
		aluno.setSexo("Masculino");
		
		alunobean.persisteAluno(aluno, false);
		assertNotNull(aluno.getId());
		
		Monitoria monitoria = new Monitoria();
		monitoria.setAluno(aluno);
		monitoria.setAvaliado(true);
		monitoria.setBolsa(true);
		monitoria.setEdital(edital);
		monitoria.setPlanoMonitoria(plano);
		monitoria.setSelecionado(true);
		
		monitoriabean.persisteMonitoria(monitoria);
		assertNotNull(monitoria.getId());
	}
	
	@Test
	public void t02_consultarMonitoria() throws Exception 
	{
		assertTrue(monitoriabean.consultaMonitorias().size() > 0);
	}
	
	@Test
	public void t03_alterarMonitoria() throws Exception {
		Monitoria monitoria = monitoriabean.consultaMonitorias().get(0);
		monitoria.setSelecionado(false);

		monitoriabean.atualizaMonitoria(monitoria);
		
		monitoria = monitoriabean.consultaMonitorias().get(0);
		
		assertFalse(monitoria.isSelecionado());
	}
	
	@Test
	public void t04_deletarMonitoria() throws Exception 
	{
		Monitoria monitoria = monitoriabean.consultaMonitorias().get(0);
		
		assertFalse(monitoriabean.deletaMonitoria(monitoria.getId()).hasErrors());
		
		Aluno aluno = alunobean.consultaAlunoByMatricula("20132Y6-RC9999");
		
		assertFalse(alunobean.deletaAluno(aluno.getId()).hasErrors());
		
		PlanoMonitoria plano = planobean.consultaPlanos().get(0);
		
		DelecaoRequestResult resultadoPlano = planobean.deletaPlanoMonitoria(plano.getId());
		
		assertFalse(resultadoPlano.hasErrors());
		
		ComponenteCurricular cc = componentebean.consultaComponenteByName("TEORIA SINFONICA");

		DelecaoRequestResult resultadoCc = componentebean.deletaComponenteCurricular(cc.getId());
		
		assertFalse(resultadoCc.hasErrors());
		
		Curso curso = cursobean.consultaCursoByName("CURSOTESTE");

		DelecaoRequestResult resultadoCurso = cursobean.deletaCurso(curso.getId());
		
		assertFalse(resultadoCurso.hasErrors());
		
		Usuario usuario = usuariobean.consultaUsuarioPorEmail("emailjack@a.recife.ifpe.edu.br");
		
		assertFalse(usuariobean.deletaUsuario(usuario.getId()).hasErrors());
		
		Edital edital = editalbean.consultaEditalByNumero("999999/2020");
		
		assertFalse(editalbean.deletaEdital(edital.getId()).hasErrors());
	}

	@AfterClass
	public static void tearDownClass() 
	{
		container.close();
	}

}
