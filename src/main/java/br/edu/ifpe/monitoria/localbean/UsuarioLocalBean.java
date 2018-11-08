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

import br.edu.ifpe.monitoria.entidades.Grupo;
import br.edu.ifpe.monitoria.entidades.Grupo.Grupos;
import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;
import br.edu.ifpe.monitoria.utils.LongRequestResult;

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
	
	public boolean checaComissao(Usuario usuario)
	{
		boolean isComissao = false;
		
		List<Grupo> grupos = em.createQuery("SELECT g FROM Grupo g WHERE g.usuario = :usuario", Grupo.class)
				.setParameter("usuario", usuario)
				.getResultList();
		
		for(int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).getGrupo().equals(Grupos.COMISSAO)) {
				isComissao = true;
			}
		}
		
		return isComissao;
	}
	
	public void revokeComissao(Usuario usuario)
	{
		List<Grupo> grupos = usuario.getGrupos();
		
		for(int i = 0; i < grupos.size(); i++) {
			Grupo grupo = grupos.get(i);
			
			if(grupo.getGrupo().equals(Grupos.COMISSAO)) {
				usuario.getGrupos().remove(grupo);
			}
		}
		
		em.merge(usuario);
	}
	
	public void grantComissao(Usuario usuario)
	{
		List<Grupo> grupos = usuario.getGrupos();
		boolean anyComissao = false;
		
		for(int i = 0; i < grupos.size(); i++) {
			Grupo grupo = grupos.get(i);
			
			if(grupo.getGrupo().equals(Grupos.COMISSAO)) {
				anyComissao = true;
			}
		}
		
		if(!anyComissao) {
			Grupo newComissao = new Grupo();
			newComissao.setEmail(usuario.getEmail());
			newComissao.setGrupo(Grupos.COMISSAO);
			newComissao.setUsuario(usuario);
			
			usuario.getGrupos().add(newComissao);
			
			em.merge(usuario);
		}
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
	
	public Usuario consultaUsuarioPorEmail(String email)
	{
		Usuario userResult = null;
		
		try {
			userResult = em.createNamedQuery("Usuario.findByEmail", Usuario.class).setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return userResult;
	}
	
	public Usuario consultaUsuarioPorRg(String rg)
	{
		Usuario userResult = null;
		
		try {
			userResult = em.createNamedQuery("Usuario.findByRg", Usuario.class).setParameter("rg", rg).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return userResult;
	}
	
	public Usuario consultaUsuarioPorCpf(String cpf)
	{
		Usuario userResult = null;
		
		try {
			userResult = em.createNamedQuery("Usuario.findByCpf", Usuario.class).setParameter("cpf", cpf).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return userResult;
	}
	
	public Usuario consultaUsuarioById(Long id)
	{
		Usuario usuarioPorId = null;

		try {
			usuarioPorId = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return usuarioPorId;
	}
	
	public List<Usuario> consultaUsuarioByName(String nome)
	{
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findByNome", Usuario.class).setParameter("nome", nome).getResultList();
		
		return usuarios;
	}
	
	public LongRequestResult consultarIdByEmail(String email)
	{
		LongRequestResult result = new LongRequestResult();
		
		try {
			result.data = em.createNamedQuery("Usuario.findIdByEmail", Long.class).setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			result.errors.add("E-mail inexistente!");
		}

		return result;
	}
	
	public DelecaoRequestResult deletaUsuario(Long id)
	{
		DelecaoRequestResult resultado = new DelecaoRequestResult();
		
		Usuario usuarioDeletado = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
		
		try 
		{
			em.remove(usuarioDeletado);
			resultado.result = true;
		} catch(Exception e) {
			resultado.errors.add("Problemas na deleção, contate o suporte.");
			resultado.result = false;
		}
		
		return resultado;
	}
	
	public boolean persisteUsuario(@NotNull @Valid Usuario usuario)
	{
		em.persist(usuario);
		
		return true;
	}
}
