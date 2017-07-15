package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Professor;

@Stateless
@LocalBean
public class ProfessorLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteProfessor (@NotNull @Valid Professor professor)
	{
		em.persist(professor);
		
		return true;
	}
	
	public List<Professor> consultaProfessores()
	{
		List<Professor> professores = em.createNamedQuery("Professor.findAll", Professor.class).getResultList();
		
		return professores;
	}
}
