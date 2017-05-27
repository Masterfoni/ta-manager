package br.edu.ifpe.monitoria.testes;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Coordenacao;
import br.edu.ifpe.monitoria.entidades.Professor;

public class ValidationTestes {

	private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;
	
	@BeforeClass
    public static void setUpClass() {
        logger.setLevel(Level.INFO);
        emf = Persistence.createEntityManagerFactory("monitoria");
        DbUnitUtil.inserirDados();
    }
	
	@AfterClass
    public static void tearDownClass() {
        emf.close();
    }
	
	@Before
    public void setUp() {
        em = emf.createEntityManager();
        beginTransaction();
    }
	
	@After
    public void tearDown() {
        commitTransaction();
        em.close();
        em = null;
        et = null;
    }

	private void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }
	
	private void commitTransaction() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
           
            if(et.isActive())
            	et.rollback();
        }
    }
	
	@Test
    public void t01_criarCCValido() {
		ComponenteCurricular cc = new ComponenteCurricular();

		cc.setCargaHoraria(46);
		cc.setCodigo("DSC");
		cc.setCoordenacao(em.find(Coordenacao.class, (long)1));
		cc.setNome("Desenvolvimento de Software Corporativo");
		cc.setPeriodo("2017/2");
		cc.setProfessor(em.find(Professor.class, (long)1));
		cc.setTurno(ComponenteCurricular.Turno.NOTURNO);
	
		em.persist(cc);
        assertNotNull(cc.getId());
	}
	
	@Test
    public void t02_criarCCInvalido() {
		ComponenteCurricular cc = null;
		
		try {
			cc = new ComponenteCurricular();
			cc.setCargaHoraria(46);
			cc.setCodigo("DSC");
			cc.setCoordenacao(em.find(Coordenacao.class, (long)1));
			cc.setNome("Desenvolvimento de Software Corporativo");
			cc.setPeriodo("ufdj/2");
			cc.setProfessor(em.find(Professor.class, (long)1));
			cc.setTurno(ComponenteCurricular.Turno.NOTURNO);

			em.persist(cc);
			assertTrue(false);
		}catch (ConstraintViolationException ex) {
			Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation<?> violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(1, constraintViolations.size());
		}
	}
	
}
