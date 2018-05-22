package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@Stateless
@LocalBean
public class ComponenteCurricularLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
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
	public boolean atualizaComponenteCurricular(ComponenteCurricular componenteCurricular)
	{
		em.merge(componenteCurricular);
		
		return true;
	}
	
	public ComponenteCurricular consultaComponenteById(Long id)
	{
		ComponenteCurricular componentePorId = em.createNamedQuery("ComponenteCurricular.findById", ComponenteCurricular.class)
										   .setParameter("id", id).getSingleResult();
		
		return componentePorId;
	}
	
	public ComponenteCurricular consultaComponenteByName(String nome)
	{
		ComponenteCurricular componente = em.createNamedQuery("ComponenteCurricular.findByNome", ComponenteCurricular.class)
												 .setParameter("nome", nome).getSingleResult();
		
		return componente;
	}
	
	public List<ComponenteCurricular>consultaComponentesByCurso(Long cursoId) {
		return em.createNamedQuery("ComponenteCurricular.findByCurso", ComponenteCurricular.class).setParameter("id", cursoId).getResultList();
	}
	
	/**
	 * Método responsável por remover um componente curricular do banco de dados.
	 *
	 * @param {@code Long} id Identificador ùnico do componente curricular
	 * @return {@code DelecaoRequestResult} Objeto que engloba uma lista de erros e o resultado da operação, que caso seja bem sucedida,
	 * será {@code true} e a lista de erros estará vazia.
	 */
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
	
	public boolean persisteComponenteCurricular(@NotNull @Valid ComponenteCurricular componente)
	{
		em.persist(componente);
		
		return true;
	}

	/**
	 * Método responsável por procurar os componentes curriculares de um professor especifico.
	 *
	 * @param {@code Long} id Identificador único do professor
	 * @return {@code List<ComponenteCurricular>} Lista de componentes curriculares de um professor
	 */
	public List<ComponenteCurricular> consultaComponentesByProfessor(Servidor servidor) {
		
		List<ComponenteCurricular> componentes = em.createNamedQuery("ComponenteCurricular.findByProfessor", ComponenteCurricular.class)
				 .setParameter("professorId", servidor.getId()).getResultList();
		
		return componentes;
	}
}
