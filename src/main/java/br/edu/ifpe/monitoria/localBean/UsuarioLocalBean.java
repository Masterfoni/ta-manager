package br.edu.ifpe.monitoria.localBean;

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
public class UsuarioLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public List<Usuario> consultaUsuarios()
	{
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
		
		return usuarios;
	}
	
	public boolean atualizaUsuario(Usuario usuario)
	{
		Usuario usuarioAtualizar = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", usuario.getId()).getSingleResult();
		
		usuarioAtualizar.setEmail(usuario.getEmail());
		usuarioAtualizar.setNome(usuario.getNome());
		usuarioAtualizar.setSenha(usuario.getSenha());
		
		em.merge(usuarioAtualizar);
		
		return true;
	}
	
	public Usuario consultaUsuario(String email)
	{
		Usuario usuarioPorEmail = em.createNamedQuery("Usuario.findByEmail", Usuario.class).setParameter("email", email).getSingleResult();
		
		return usuarioPorEmail;
	}
	
	public Usuario consultaUsuarioById(Long id)
	{
		Usuario usuarioPorId = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
		
		return usuarioPorId;
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
