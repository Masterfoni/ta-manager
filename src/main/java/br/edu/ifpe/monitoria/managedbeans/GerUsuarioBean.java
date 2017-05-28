package br.edu.ifpe.monitoria.managedbeans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Usuario;

@Stateless
@LocalBean
public class GerUsuarioBean {

	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteUsuario(@NotNull @Valid Usuario usuario)
	{
		em.persist(usuario);
		
		return true;
	}
	
}
