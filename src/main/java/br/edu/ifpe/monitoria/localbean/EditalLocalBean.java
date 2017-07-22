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

import br.edu.ifpe.monitoria.entidades.Edital;

@Stateless
@LocalBean
@DeclareRoles({"administrativo"})
public class EditalLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed("{administrativo}")
	public List<Edital> consultaEditais()
	{
		List<Edital> editais = em.createNamedQuery("Edital.findAll", Edital.class).getResultList();
		
		return editais;
	}
	
	@RolesAllowed("{administrativo}")
	public boolean atualizaEdital(Edital edital)
	{
		Edital editalAtualizar = em.createNamedQuery("Edital.findById", Edital.class)
											   .setParameter("id", edital.getId()).getSingleResult();
		
		editalAtualizar.setNumeroEdital(edital.getNumeroEdital());
		
		em.merge(editalAtualizar);
		
		return true;
	}
	
	@RolesAllowed("{administrativo}")
	public Edital consultaEditalById(Long id)
	{
		Edital editalPorId = em.createNamedQuery("Edital.findById", Edital.class)
										   .setParameter("id", id).getSingleResult();
		
		return editalPorId;
	}
	
	@RolesAllowed("{administrativo}")
	public List<Edital> consultaEditalByNumeroEdital(String numeroEdital)
	{
		List<Edital> editais = em.createNamedQuery("Edital.findByNumeroEdital", Edital.class)
										.setParameter("nome", numeroEdital).getResultList();
		
		return editais;
	}
	
	@RolesAllowed("{administrativo}")
	public boolean deletaEdital(Long id)
	{
		Edital editalDeletado = em.createNamedQuery("Edital.findById", Edital.class)
										 .setParameter("id", id).getSingleResult();
		
		em.remove(editalDeletado);
		
		return true;
	}
	
	@RolesAllowed("{administrativo}")
	public boolean persisteEdital(@NotNull @Valid Edital edital)
	{
		em.persist(edital);
		
		return true;
	}
}
