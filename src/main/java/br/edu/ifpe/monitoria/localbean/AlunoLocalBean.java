package br.edu.ifpe.monitoria.localbean;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Grupo;

@Stateless
@LocalBean
public class AlunoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteAluno (@NotNull @Valid Aluno aluno)
	{		
		em.persist(aluno);
		Grupo gp = new Grupo();
		gp.setGrupo(Grupo.Grupos.ALUNO);
		gp.setUsuario(aluno);
		gp.setEmail(aluno.getEmail());
		em.persist(gp);
		
		return true;
	}
	
	@PermitAll
	public Aluno consultaAlunoById(Long id)
	{
		Aluno alunoPorId = null;
		
		try {
			alunoPorId = em.createNamedQuery("Aluno.findById", Aluno.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return alunoPorId;
	}
	
	@PermitAll
	public Aluno consultaAlunoByMatricula(String matricula)
	{
		Aluno alunoPorMatricula = null;
		
		try {
			alunoPorMatricula = em.createNamedQuery("Aluno.findByMatricula", Aluno.class).setParameter("matricula", matricula).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return alunoPorMatricula;
	}
	
	@RolesAllowed("aluno")
	public boolean atualizaAluno (Aluno aluno) {
		
		em.merge(aluno);
		return true;
	}
}
