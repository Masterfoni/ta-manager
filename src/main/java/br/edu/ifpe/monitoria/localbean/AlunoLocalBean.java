package br.edu.ifpe.monitoria.localbean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Aluno;

@Stateless
@LocalBean
public class AlunoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteAluno (@NotNull @Valid Aluno aluno)
	{
		em.persist(aluno);
		
		return true;
	}
	
	public Aluno consultaAlunoById(Long id)
	{
		Aluno alunoPorId = em.createNamedQuery("Aluno.findById", Aluno.class).setParameter("id", id).getSingleResult();
		
		return alunoPorId;
	}
}
