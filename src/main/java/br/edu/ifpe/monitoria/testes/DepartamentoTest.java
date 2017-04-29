package br.edu.ifpe.monitoria.testes;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.edu.ifpe.monitoria.entidades.Departamento;

public class DepartamentoTest {

	private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;
	
    public DepartamentoTest() {
	}

    @BeforeClass
    public static void setUpClass() {
        logger.setLevel(Level.SEVERE);
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
            et.rollback();
            fail(ex.getMessage());
        }
    }
	
	@Test
    public void t01_nomePorSigla() {
        logger.info("Executando t01: SELECT * FROM TB_DEPARTAMENTO WHERE TXT_SIGLA LIKE :sigla");
        TypedQuery<Departamento> query = em.createQuery(
                "SELECT * FROM TB_DEPARTAMENTO WHERE TXT_SIGLA LIKE :sigla",
                Departamento.class);
        query.setParameter("sigla", "DASE%");
        List<Departamento> departamentos = query.getResultList();

        for (Departamento departamento : departamentos) {
            assertTrue(departamento.getNome().startsWith("Departamento"));
        }

        assertEquals(1, departamentos.size());
    }
	
}
