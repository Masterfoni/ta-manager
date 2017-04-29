package br.edu.ifpe.monitoria.mains;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Coordenacao;
import br.edu.ifpe.monitoria.entidades.Departamento;
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.Professor;

public class Main2 {

	public static void main(String[] args) {

		//Professor prof = null;
		//Aluno aluno = null;
		
		long idProf = 2, idAluno = 1;
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;

		System.out.println("..");

		Departamento dpto = new Departamento();
		dpto.setNome("Departamento de Ensino");
		dpto.setSigla("DEN");
		
		try {
			emf = Persistence.createEntityManagerFactory("monitoria");
			em = emf.createEntityManager();
			et = em.getTransaction();
			et.begin();

			Professor prof = em.find(Professor.class, idProf);
			
			Coordenacao coord = new Coordenacao();
			coord.setCoordenador(prof);
			coord.setDepartamento(dpto);
			coord.setNome("Coord Sistemas da Informação");
			coord.setSigla("CSIN");
			
			
			ComponenteCurricular disc = new ComponenteCurricular();
			disc.setProfessor(prof);
			disc.setCoordenacao(coord);
			disc.setTurno("NOITE");
			disc.setCodigo("TADS-111");
			disc.setPeriodo("2017.1");
			disc.setNome("Soft Corporativo");
			
			Aluno al = em.find(Aluno.class, idAluno);
			
			Monitoria mon = new Monitoria();
			mon.setAluno(al);
			mon.setComponenteCurricular(disc);
			mon.setBolsa(false);
			
			
			
			em.persist(dpto);
			em.persist(coord);
			em.persist(disc);
			em.persist(mon);
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
