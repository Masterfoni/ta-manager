package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Usuario;

@Stateless
@LocalBean
@DeclareRoles({"administrativo", "professor", "aluno"})
public class UsuarioLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@DenyAll
	public List<Usuario> consultaUsuarios()
	{
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
		
		return usuarios;
	}
	
	@RolesAllowed({"professor", "administrativo", "aluno"})
	public boolean atualizaUsuario(Usuario usuario)
	{
		Usuario usuarioAtualizar = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", usuario.getId()).getSingleResult();
		
		usuarioAtualizar.setEmail(usuario.getEmail());
		usuarioAtualizar.setNome(usuario.getNome());
		usuarioAtualizar.setSenha(usuario.getSenha());
		
		em.merge(usuarioAtualizar);
		
		return true;
	}
	
	@PermitAll
	public Usuario consultaUsuarioPorEmailSenha(String email, String senha)
	{
		Usuario userResult = new Usuario();
		
		try {
			userResult = em.createNamedQuery("Usuario.findByEmailSenha", Usuario.class).setParameter("email", email)
																					   .setParameter("senha", senha)
																					   .getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return userResult;
	}
	
	@RolesAllowed({"professor", "administrativo", "aluno"})
	public Usuario consultaUsuarioPorEmail(String email)
	{
		Usuario userResult = null;
		
		try {
			userResult = em.createNamedQuery("Usuario.findByEmail", Usuario.class).setParameter("email", email)
																					   .getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return userResult;
	}
	
	@RolesAllowed({"professor", "administrativo", "aluno"})
	public Usuario consultaUsuarioById(Long id)
	{
		Usuario usuarioPorId = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
		
		return usuarioPorId;
	}
	
	@RolesAllowed({"professor", "administrativo", "aluno"})
	public List<Usuario> consultaUsuarioByName(String nome)
	{
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findByNome", Usuario.class).setParameter("nome", nome).getResultList();
		
		return usuarios;
	}
	
	@PermitAll
	public Long consultarIbByEmail(String email)
	{
		Long id = em.createNamedQuery("Usuario.findIdByEmail", Long.class).setParameter("email", email).getSingleResult();
		
		return id;
	}
	
	@DenyAll
	public boolean deletaUsuario(Long id)
	{
		Usuario usuarioDeletado = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
		
		em.remove(usuarioDeletado);
		
		return true;
	}
	
	@RolesAllowed({"professor", "administrativo", "aluno"})
	public boolean persisteUsuario(@NotNull @Valid Usuario usuario)
	{
		em.persist(usuario);
		
		return true;
	}
}
