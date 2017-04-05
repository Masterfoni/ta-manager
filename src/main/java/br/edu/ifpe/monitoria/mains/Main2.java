package br.edu.ifpe.monitoria.mains;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.edu.ifpe.monitoria.entidades.Coordenacao;
import br.edu.ifpe.monitoria.entidades.Departamento;
import br.edu.ifpe.monitoria.entidades.Disciplina;
import br.edu.ifpe.monitoria.entidades.Professor;

public class Main2 {

	public static void main(String[] args) {

		Professor p = new Professor();
		p.setEmail("jval@a.recife.ifpe.edu.br");
		p.setNome("João Vitor Almeida de Lima");
		p.setSiape(1234567);
		
		Departamento dpto = new Departamento();
		dpto.setNome("Departamento de Ensino");
		dpto.setSigla("DEN");
		
		Coordenacao coord = new Coordenacao();
		coord.setCoordenador(p);
		coord.setDepartamento(dpto);
		coord.setNome("Coord Sistemas da Informação");
		coord.setSigla("CSIN");
		
		Disciplina disc = new Disciplina();
		disc.setProfessor(p);
		disc.setCoordenacao(coord);
		disc.setTurno("NOITE");
		disc.setCodigo("TADS-111");
		disc.setPeriodo("2017.1");
		disc.setNome("Soft Corporativo");
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		System.out.println("..");
		try {
			emf = Persistence.createEntityManagerFactory("monitoria");
			em = emf.createEntityManager();
			et = em.getTransaction();
			et.begin();
			em.persist(p);
			em.persist(dpto);
			em.persist(coord);
			em.persist(disc);
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
