package br.edu.ifpe.monitoria.entidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {

		Aluno a = new Aluno();
		a.setEmail("a@a.recife.ifpe.edu.br");
		a.setMatricula("20132y6-rc0189");
		a.setNome("Aluno");
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		System.out.println("..");
		try {
			emf = Persistence.createEntityManagerFactory("monitoria");
			em = emf.createEntityManager();
			et = em.getTransaction();
			et.begin();
			em.persist(a);
			et.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (et != null)
				et.rollback();
		} finally {
			if (em != null)
				em.close();
			if(emf != null)
				emf.close();
		}

	}

}
