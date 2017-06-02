package br.edu.ifpe.monitoria.managedbeans;

import java.util.List;

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
public class GerUsuarioBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public List<Usuario> consultaUsuarios()
	{
		List<Usuario> resultado = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
		
		return resultado;
	}
	
	public boolean deletaUsuario(Long id)
	{
		Usuario usuarioDeletado = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();

		em.remove(usuarioDeletado);
		
		return true;
	}
	
	public boolean persisteUsuario(@NotNull @Valid Usuario usuario)
	{
		em.persist(usuario);
		
		return true;
	}
}
