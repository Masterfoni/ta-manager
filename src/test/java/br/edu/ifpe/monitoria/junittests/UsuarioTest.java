package br.edu.ifpe.monitoria.junittests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;
import br.edu.ifpe.monitoria.testutils.JUnitUtils;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest {
	private static EJBContainer container;

	@EJB
	private UsuarioLocalBean usuariobean;

	@BeforeClass
	public static void setUpClass() throws Exception {
		container = JUnitUtils.buildContainer();
	}

	@Before
	public void setUp() throws NamingException {
		usuariobean = (UsuarioLocalBean) JUnitUtils.getLocalBean(container, "UsuarioLocalBean");
	}

	@Test
	public void t01_criarUsuario() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setCpf("569.529.179-85");
		usuario.setEmail("emailjack@email.com");
		usuario.setNome("Jackson Five");
		usuario.setRg("7980520");
		usuario.setRgEmissor("SDS/PE");
		usuario.setSal("salzin");
		usuario.setSenha("draco123");
		usuario.setSexo("Masculino");

		usuariobean.persisteUsuario(usuario);
		assertNotNull(usuario.getId());
	}
	
	@Test
	public void t02_consultarUsuarios() throws Exception {
		assertTrue(usuariobean.consultaUsuarios().size() > 0);
	}
	
	@Test
	public void t03_alterarUsuario() throws Exception {
		Usuario usuario = usuariobean.consultaUsuarioPorEmail("emailjack@email.com");
		usuario.setNome("JACKSON SIX");

		usuariobean.atualizaUsuario(usuario);
		
		usuario = usuariobean.consultaUsuarioPorEmail("emailjack@email.com");
		
		assertTrue(usuario.getNome().equals("JACKSON SIX"));
	}
	
	@Test
	public void t04_deletarUsuario() throws Exception {
		Usuario usuario = usuariobean.consultaUsuarioPorEmail("emailjack@email.com");
		
		DelecaoRequestResult resultado = usuariobean.deletaUsuario(usuario.getId());
		
		assertFalse(resultado.hasErrors());
	}

	@AfterClass
	public static void tearDownClass() {
		container.close();
	}

}
