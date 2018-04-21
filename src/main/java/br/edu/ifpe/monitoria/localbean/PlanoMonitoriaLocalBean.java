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

import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;

@Stateless
@LocalBean
@DeclareRoles({"comissao", "professor", "aluno"})
public class PlanoMonitoriaLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	/**
	 * Método responsável por inserir no banco um novo plano de monitoria
	 *
	 * @param PlanoMonitoria uma instância de PlanoMonitoria que representa o plano à ser persistido
	 * @return true no caso de sucesso 
	 */
	@RolesAllowed({"professor", "comissao"})
	public boolean persistePlanoMonitoria (@Valid @NotNull PlanoMonitoria plano)
	{
		em.persist(plano);
		
		return true;
	}
	
	public List<PlanoMonitoria> consultaPlanosByServidor(Long id)
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByProfessor", PlanoMonitoria.class).setParameter("id", id).getResultList();
		
		return planos;
	}
	
	public List<PlanoMonitoria> consultaPlanosByCoordenador(Long id)
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByCoordenador", PlanoMonitoria.class).setParameter("id", id).getResultList();
		
		return planos;
	}
	
	/**
	 * Método responsável por listar todos os planos cadastrados no sistema
	 *
	 * @return {@code List<PlanoMonitoria>} Uma lista de planos de monitoria, que pode estar vazia ou não. 
	 */
	@RolesAllowed({"professor", "comissao", "aluno"})
	public List<PlanoMonitoria> consultaPlanos()
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findAll", PlanoMonitoria.class).getResultList();
		
		return planos;
	}
	
	@RolesAllowed({"professor", "comissao"})
	public PlanoMonitoria consultaPlanosById(Long id)
	{
		PlanoMonitoria planoPorId = em.createNamedQuery("PlanoMonitoria.findById", PlanoMonitoria.class).setParameter("id", id).getSingleResult();
		
		return planoPorId;
	}
	
	@RolesAllowed({"professor, caomissao"})
	public boolean atualizaPlanoMonitoria(PlanoMonitoria plano)
	{
		em.merge(plano);

		return true;
	}
}
