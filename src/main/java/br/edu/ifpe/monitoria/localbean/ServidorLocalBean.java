package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Servidor;

@Stateless
@LocalBean
public class ServidorLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteProfessor (@NotNull @Valid Servidor servidor)
	{
		em.persist(servidor);
	
		return true;
	}
	
	public List<Servidor> consultaServidores()
	{
		List<Servidor> professores = em.createNamedQuery("Servidor.findAll", Servidor.class).getResultList();
		
		return professores;
	}
	
	public Servidor consultaServidorById(Long id)
	{
		Servidor servidorPorId = null;
		
		List<Servidor> resultList = em.createNamedQuery("Servidor.findById", Servidor.class).setParameter("id", id).getResultList();
		
		if(resultList.size() > 0) {
			servidorPorId = resultList.get(0);
		}
		
		return servidorPorId;
	}
	
	public Servidor findServidorBySiape(Integer siape)
	{
		Servidor servidorPorSiape = null;
		
		try {
			servidorPorSiape = em.createNamedQuery("Servidor.findBySiape", Servidor.class).setParameter("siape", siape).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return servidorPorSiape;
	}
}
