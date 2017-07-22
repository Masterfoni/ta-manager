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

import br.edu.ifpe.monitoria.entidades.Departamento;

@Stateless
@LocalBean
@DeclareRoles("{administrativo}")
public class DepartamentoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed("{administrativo}")
	public List<Departamento> consultaDepartamentos()
	{
		List<Departamento> departamentos = em.createNamedQuery("Departamento.findAll", Departamento.class).getResultList();
		
		return departamentos;
	}
	
	@RolesAllowed("{administrativo}")
	public boolean atualizaDepartamento(Departamento departamento)
	{
		Departamento departamentoAtualizar = em.createNamedQuery("Departamento.findById", Departamento.class)
											   .setParameter("id", departamento.getId()).getSingleResult();
		
		departamentoAtualizar.setNome(departamento.getNome());
		departamentoAtualizar.setSigla(departamento.getSigla());
		
		em.merge(departamentoAtualizar);
		
		return true;
	}
	
	@RolesAllowed("{administrativo}")
	public Departamento consultaDepartamentoById(Long id)
	{
		Departamento departamentoPorId = em.createNamedQuery("Departamento.findById", Departamento.class)
										   .setParameter("id", id).getSingleResult();
		
		return departamentoPorId;
	}
	
	@RolesAllowed("{administrativo}")
	public List<Departamento> consultaDepartamentoByName(String nome)
	{
		List<Departamento> departamentos = em.createNamedQuery("Departamento.findByNome", Departamento.class)
										.setParameter("nome", nome).getResultList();
		
		return departamentos;
	}
	
	@RolesAllowed("{administrativo}")
	public boolean deletaDepartamento(Long id)
	{
		Departamento departamentoDeletado = em.createNamedQuery("Departamento.findById", Departamento.class)
										 .setParameter("id", id).getSingleResult();
		
		em.remove(departamentoDeletado);
		
		return true;
	}
	
	@RolesAllowed("{administrativo}")
	public boolean persisteDepartamento(@NotNull @Valid Departamento departamento)
	{
		em.persist(departamento);
		
		return true;
	}
}
