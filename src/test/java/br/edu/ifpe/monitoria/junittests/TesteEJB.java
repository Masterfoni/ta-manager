/**
 * 
 */
package br.edu.ifpe.monitoria.junittests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.enterprise.security.ee.auth.login.ProgrammaticLogin;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;

/**
 * @author Felipe Lima
 *
 */
@RunAs("comissao")
public class TesteEJB  {

	 private static EJBContainer container;

	 @EJB
	 private EditalLocalBean editalbean;
	 
	 @BeforeClass
	 public static void setUpClass() throws Exception {
		 Map<String, Object> properties = new HashMap<String, Object>();
		 properties.put(EJBContainer.MODULES, new File("target/classes"));
		 properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "C:/glassfish4/glassfish");
		 properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "C:/glassfish4/glassfish/domains/domain1/config/domain.xml");
		 container = EJBContainer.createEJBContainer(properties);
	 }
	 
	 @Before
	 public void setUp() throws NamingException {
		 editalbean = (EditalLocalBean)container.getContext().lookup("java:global/classes/EditalLocalBean");
	 }
	 
	 @Test
	 public void criarEdital() throws Exception {
		 
		 ProgrammaticLogin login = new ProgrammaticLogin();
		 login.login("fal@a.recife.ifpe.edu.br", "109820288239215949192", "saltRealm", true);
		 
		 Edital edital = new Edital();
		 edital.setAno(2018);
		 edital.setNumero(2);
		 edital.setNumeroEdital(edital.getNumero()+"/"+edital.getAno());
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

	 }

	 @AfterClass
	 public static void tearDownClass() {
		 container.close();
	 }
}
