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
@DeclareRoles({"administrativo", "professor"})
public class PlanoMonitoriaLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed({"professor"})
	public boolean persistePlanoMonitoria (@Valid @NotNull PlanoMonitoria plano)
	{
		em.persist(plano);
		
		return true;
	}
	
	@RolesAllowed({"professor", "administrativo"})
	public List<PlanoMonitoria> consultaPlanos()
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findAll", PlanoMonitoria.class).getResultList();
		
		return planos;
	}
	
	@RolesAllowed({"professor", "administrativo"})
	public PlanoMonitoria consultaPlanosById(Long id)
	{
		PlanoMonitoria planoPorId = em.createNamedQuery("PlanoMonitoria.findById", PlanoMonitoria.class).setParameter("id", id).getSingleResult();
		
		return planoPorId;
	}
	
	@RolesAllowed({"professor"})
	public boolean atualizaPlanoMonitoria(PlanoMonitoria plano)
	{
		PlanoMonitoria planoAtualizar = em.createNamedQuery("PlanoMonitoria.findById", PlanoMonitoria.class).setParameter("id", plano.getId()).getSingleResult();

		em.merge(planoAtualizar);

		return true;
	}
}
