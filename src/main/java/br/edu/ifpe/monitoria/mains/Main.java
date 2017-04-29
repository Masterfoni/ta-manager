package br.edu.ifpe.monitoria.mains;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Professor;
import br.edu.ifpe.monitoria.entidades.Professor.TipoProfessor;
import br.edu.ifpe.monitoria.entidades.Professor.Titulacao;

public class Main {

	public static void main(String[] args) {

		Aluno a = new Aluno();
		a.setEmail("a@a.recife.ifpe.edu.br");
		a.setMatricula("20132y6-rc0189");
		a.setNome("Aluno");
		a.setCpf("000.000.000-00");
		a.setRg("0.000.000");
		a.setRgEmissor("SDS-PE");
		a.setSexo("Masculino");
		
		Professor p = new Professor();
		p.setEmail("p@ifpe.edu.br");
		p.setNome("Darth");
		p.setSiape(2981585);
		p.setTipo(TipoProfessor.ORIENTADOR);
		p.setTiulacao(Titulacao.GRADUAÇÃO);
		
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
			em.persist(p);
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
