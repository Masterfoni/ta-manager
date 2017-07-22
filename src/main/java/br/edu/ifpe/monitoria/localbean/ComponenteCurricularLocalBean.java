package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;

@Stateless
@LocalBean
@DeclareRoles({"ADMINISTRATIVO", "PROFESSOR"})
public class ComponenteCurricularLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATIVO"})
	public List<ComponenteCurricular> consultaComponentesCurriculares()
	{
		List<ComponenteCurricular> componentes = em.createNamedQuery("ComponenteCurricular.findAll", ComponenteCurricular.class).getResultList();
		
		return componentes;
	}
	
	@RolesAllowed({"PROFESSOR"})
	public boolean atualizaComponenteCurricular(ComponenteCurricular componenteCurricular)
	{
		ComponenteCurricular componenteAtualizar = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
											   .setParameter("id", componenteCurricular.getId()).getSingleResult();
		
		
		em.merge(componenteAtualizar);
		
		return true;
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATIVO"})
	public ComponenteCurricular consultaComponenteById(Long id)
	{
		ComponenteCurricular componentePorId = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
										   .setParameter("id", id).getSingleResult();
		
		return componentePorId;
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATIVO"})
	public List<ComponenteCurricular> consultaComponentesByName(String nome)
	{
		List<ComponenteCurricular> componentes = em.createNamedQuery("ComponenteCurricular.findByNome", ComponenteCurricular.class)
												 .setParameter("nome", nome).getResultList();
		
		return componentes;
	}
	
	@RolesAllowed({"PROFESSOR"})
	public boolean deletaComponenteCurricular(Long id)
	{
		ComponenteCurricular componenteDeletado = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
										 		  .setParameter("id", id).getSingleResult();
		
		em.remove(componenteDeletado);
		
		return true;
	}
	
	@RolesAllowed({"PROFESSOR"})
	public boolean persisteComponenteCurricular(@NotNull @Valid ComponenteCurricular componente)
	{
		em.persist(componente);
		
		return true;
	}
}
