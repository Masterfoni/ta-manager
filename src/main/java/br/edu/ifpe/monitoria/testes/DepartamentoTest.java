package br.edu.ifpe.monitoria.testes;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
	
	
	//SELECT JPQL - (em.createQuery) -> Query String
	@Test
    public void t01_nomePorSigla() {
        logger.info("Executando t01: SELECT * FROM TB_DEPARTAMENTO WHERE TXT_SIGLA LIKE :sigla");
        TypedQuery<Departamento> query = em.createQuery(
                "SELECT d FROM Departamento d WHERE d.sigla LIKE :sigla",
                Departamento.class);
        query.setParameter("sigla", "DASE%");
        List<Departamento> departamentos = query.getResultList();

        for (Departamento departamento : departamentos) {
            assertTrue(departamento.getNome().startsWith("Departamento"));
        }

        assertEquals(1, departamentos.size());
    }
	
	//SELECT JPQL - (em.createQuery) -> Query Nomeada
	@Test
    public void t02_nomePorSigla() {
        logger.info("Executando t02: Departamento.PorSigla");
        TypedQuery<Departamento> query = em.createNamedQuery("Departamento.PorSigla", Departamento.class);
        query.setParameter("sigla", "DASE%");
        List<Departamento> departamentos = query.getResultList();

        for (Departamento departamento : departamentos) {
            assertTrue(departamento.getNome().startsWith("Departamento"));
        }

        assertEquals(1, departamentos.size());
    }
	
	//SELECT SQL - (em.createNativeQuery) -> Query Nomeada
	@Test
    public void t03_departamentosSQLNomeada() {
        logger.info("Executando t03: Departamento.PorNomeSQL");
        Query query;
        query = em.createNamedQuery("Departamento.PorNomeSQL");
        query.setParameter(1, "Departamento%");
        List<Departamento> departamentos = query.getResultList();
        assertEquals(2, departamentos.size());

        if (logger.isLoggable(Level.INFO)) {
            for (Departamento departamento : departamentos) {
                logger.log(Level.INFO, departamento.getNome());
            }
        }
    }
	
	//SELECT SQL - (em.createNativeQuery) -> String
	@Test
	public void t04_departamentosSQL() {
		logger.info("Executando t04: SELECT ID, TXT_NOME, TXT_SIGLA FROM TB_DEPARTAMENTO WHERE TXT_NOME LIKE ? ORDER BY ID");
	    Query query;
	    query = em.createNativeQuery(
	    		"SELECT ID, TXT_NOME, TXT_SIGLA FROM TB_DEPARTAMENTO WHERE TXT_NOME LIKE ? ORDER BY ID",
	            Departamento.class);
	    query.setParameter(1, "Departamento%");
	    List<Departamento> departamentos = query.getResultList();
	    assertEquals(2, departamentos.size());

	    if (logger.isLoggable(Level.INFO)) {
	    	for (Departamento departamento : departamentos) {
	    		logger.log(Level.INFO, departamento.getNome());
	        }
	    }
	}
	
	// UPDATE em QUERY
	@Test
    public void t05_update() {
        logger.info("Executando t05: UPDATE Departamento AS d SET d.txt_nome = ?1 WHERE d.id = ?2");
        Long id = (long) 5;
        Query query = em.createQuery("UPDATE Departamento AS d SET d.nome = ?1 WHERE d.id = ?2");
        query.setParameter(1, "Departamento Acadêmico de Sistemas e Eletrônica");
        query.setParameter(2, id);
        query.executeUpdate();
        Departamento departamento = em.find(Departamento.class, id);
        assertEquals("Departamento Acadêmico de Sistemas e Eletrônica", departamento.getNome());
        logger.info(departamento.getNome());
	}
	
	// DELETE em query
    @Test
    public void t06_delete() {
        logger.info("Executando t06: DELETE FROM Departamento AS d WHERE d.id = ?1");
        Long id = (long) 6;
        Query query = em.createQuery("DELETE FROM Departamento AS d WHERE d.id = ?1");
        query.setParameter(1, id);
        query.executeUpdate();
        Departamento departamento = em.find(Departamento.class, id);
        assertNull(departamento);
        logger.log(Level.INFO, "Oferta {0} removida com sucesso.", id);
    }

    // PERSISTIR via objeto
    @Test
    public void t07_persistirDepartamento() {
        logger.info("Executando t07: persistir Departamento");
        Departamento dpto = new Departamento();
        dpto.setNome("Diretoria de Ensino");
        dpto.setSigla("DEN");        
        em.persist(dpto);
        em.flush();
        assertNotNull(dpto.getId());
        logger.log(Level.INFO, "Categoria {0} incluída com sucesso.", dpto);
    }
    
    // ATUALIZAR via objeto
    @Test
    public void t08_atualizarSigla() {
        logger.info("Executando t08: atualizar Categoria");
        TypedQuery<Departamento> query = em.createNamedQuery("Departamento.PorSigla", Departamento.class);
        query.setParameter("sigla", "DASE%");
        Departamento dpto = query.getSingleResult();
        assertNotNull(dpto);
        dpto.setSigla("DSE");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    //  ATUALIZAR via objeto utilizando o merge
    @Test
    public void t09_atualizarDepartamentoMerge() {
        logger.info("Executando t09: atualizar Categoria com Merge");
        TypedQuery<Departamento> query = em.createNamedQuery("Departamento.PorSigla", Departamento.class);
        query.setParameter("sigla", "DSE%");
        Departamento departamento = query.getSingleResult();
        assertNotNull(departamento);
        em.clear();
        departamento.setSigla("DASE");
        em.merge(departamento);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    // EXCLUI via objeto
    @Test
    public void t10_removerDepartamento() {
        logger.info("Executando t10: remover Departamento");
        TypedQuery<Departamento> query = em.createNamedQuery("Departamento.PorSigla", Departamento.class);
        query.setParameter("sigla", "DASE");
        Departamento departamento = query.getSingleResult();
        assertNotNull(departamento);
        em.remove(departamento);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
}
