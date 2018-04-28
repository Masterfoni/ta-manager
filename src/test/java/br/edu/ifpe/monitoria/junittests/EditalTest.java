package br.edu.ifpe.monitoria.junittests;


import static org.jboss.arquillian.persistence.CleanupStrategy.USED_TABLES_ONLY;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;

@UsingDataSet("datasets/main.xml")
@Cleanup(phase = BEFORE, strategy = USED_TABLES_ONLY)
public class EditalTest extends AbstractTest {
	@Inject
	private UsuarioLocalBean usuarioBean;

	@Test
	@InSequence(1)
	public void testFindAllEditais() {
		assertNotNull(usuarioBean.consultaUsuarios());
	}
}