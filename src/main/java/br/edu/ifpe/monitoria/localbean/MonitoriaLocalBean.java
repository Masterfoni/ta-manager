package br.edu.ifpe.monitoria.localbean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Monitoria;

@Stateless
@LocalBean
public class MonitoriaLocalBean {
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteMonitoria (@Valid @NotNull Monitoria monitoria)
	{
		em.persist(monitoria);
		return true;
	}
}
