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
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@Stateless
@LocalBean
@DeclareRoles({"comissao", "professor"})
public class ComponenteCurricularLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed({"professor", "comissao"})
	public List<ComponenteCurricular> consultaComponentesCurriculares()
	{
		List<ComponenteCurricular> componentes = em.createNamedQuery("ComponenteCurricular.findAll", ComponenteCurricular.class).getResultList();
		
		return componentes;
	}
	
	/**
	 * Método responsável por atualizar um componente curricular.
	 *
	 * @param componenteCurricular Um objeto ComponenteCurricular que representa o estado atualizado.
	 * @return true no caso de sucesso 
	 */
	@RolesAllowed({"professor", "comissao"})
	public boolean atualizaComponenteCurricular(ComponenteCurricular componenteCurricular)
	{
		em.merge(componenteCurricular);
		
		return true;
	}
	
	@RolesAllowed({"professor", "comissao"})
	public ComponenteCurricular consultaComponenteById(Long id)
	{
		ComponenteCurricular componentePorId = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
										   .setParameter("id", id).getSingleResult();
		
		return componentePorId;
	}
	
	@RolesAllowed({"professor", "comissao"})
	public List<ComponenteCurricular> consultaComponentesByName(String nome)
	{
		List<ComponenteCurricular> componentes = em.createNamedQuery("ComponenteCurricular.findByNome", ComponenteCurricular.class)
												 .setParameter("nome", nome).getResultList();
		
		return componentes;
	}
	
	@RolesAllowed({"professor", "comissao"})
	public DelecaoRequestResult deletaComponenteCurricular(Long id)
	{
		DelecaoRequestResult result = new DelecaoRequestResult();
		result.result = false;
		
		if(!em.createNamedQuery("PlanoMonitoria.findByComponente", PlanoMonitoria.class).setParameter("id", id).getResultList().isEmpty())
		{
			result.errors.add("Existem planos vinculados à este componente, não é possível deletá-lo!");
		}
		else 
		{
			ComponenteCurricular componenteDeletado = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
														.setParameter("id", id).getSingleResult();
			em.remove(componenteDeletado);
			
			result.result = true;
		}
		
		return result;
	}
	
	@RolesAllowed({"professor", "comissao"})
	public boolean persisteComponenteCurricular(@NotNull @Valid ComponenteCurricular componente)
	{
		em.persist(componente);
		
		return true;
	}
}
