package br.edu.ifpe.monitoria.localbean;

import java.util.List;

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
public class PlanoMonitoriaLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persistePlanoMonitoria (@NotNull @Valid PlanoMonitoria plano)
	{
		em.persist(plano);
		
		return true;
	}
	
	public List<PlanoMonitoria> consultaPlanos()
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findAll", PlanoMonitoria.class).getResultList();
		
		return planos;
	}
	
	public PlanoMonitoria consultaPlanosById(Long id)
	{
		PlanoMonitoria planoPorId = em.createNamedQuery("PlanoMonitoria.findById", PlanoMonitoria.class).setParameter("id", id).getSingleResult();
		
		return planoPorId;
	}
	
	public List<PlanoMonitoria> consultaPlanosByName(String nome)
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByNome", PlanoMonitoria.class).setParameter("nome", nome).getResultList();
		
		return planos;
	}
	
	public boolean atualizaPlanoMonitoria(PlanoMonitoria plano)
	{
//		Usuario usuarioAtualizar = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", usuario.getId()).getSingleResult();
//		
//		usuarioAtualizar.setEmail(usuario.getEmail());
//		usuarioAtualizar.setNome(usuario.getNome());
//		usuarioAtualizar.setSenha(usuario.getSenha());
//		
//		em.merge(usuarioAtualizar);
//		
		return true;
	}
}
