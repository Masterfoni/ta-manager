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
@DeclareRoles({"administrativo", "professor"})
public class ComponenteCurricularLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed({"professor", "administrativo"})
	public List<ComponenteCurricular> consultaComponentesCurriculares()
	{
		List<ComponenteCurricular> componentes = em.createNamedQuery("ComponenteCurricular.findAll", ComponenteCurricular.class).getResultList();
		
		return componentes;
	}
	
	@RolesAllowed({"professor"})
	public boolean atualizaComponenteCurricular(ComponenteCurricular componenteCurricular)
	{
		ComponenteCurricular componenteAtualizar = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
											   .setParameter("id", componenteCurricular.getId()).getSingleResult();
		
		
		em.merge(componenteAtualizar);
		
		return true;
	}
	
	@RolesAllowed({"professor", "administrativo"})
	public ComponenteCurricular consultaComponenteById(Long id)
	{
		ComponenteCurricular componentePorId = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
										   .setParameter("id", id).getSingleResult();
		
		return componentePorId;
	}
	
	@RolesAllowed({"professor", "administrativo"})
	public List<ComponenteCurricular> consultaComponentesByName(String nome)
	{
		List<ComponenteCurricular> componentes = em.createNamedQuery("ComponenteCurricular.findByNome", ComponenteCurricular.class)
												 .setParameter("nome", nome).getResultList();
		
		return componentes;
	}
	
	@RolesAllowed({"professor"})
	public boolean deletaComponenteCurricular(Long id)
	{
		ComponenteCurricular componenteDeletado = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
										 		  .setParameter("id", id).getSingleResult();
		
		em.remove(componenteDeletado);
		
		return true;
	}
	
	@RolesAllowed({"professor"})
	public boolean persisteComponenteCurricular(@NotNull @Valid ComponenteCurricular componente)
	{
		em.persist(componente);
		
		return true;
	}
}
