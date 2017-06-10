package br.edu.ifpe.monitoria.localbean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;

@Stateless
@LocalBean
public class PerfilGoogleLocalBean 
{
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persistePerfilGoogle(@NotNull @Valid PerfilGoogle perfilGoogle)
	{
		em.persist(perfilGoogle);
		
		return true;
	}
}
