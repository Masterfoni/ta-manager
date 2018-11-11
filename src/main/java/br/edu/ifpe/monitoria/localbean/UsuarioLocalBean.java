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

/**
* Classe responsável pela execução de operações sobre a entidade Usuário
* 
* @author João Vitor
* @author Felipe Araújo
* 
*/
@Stateless
@LocalBean
public class UsuarioLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	/**
	 * <p>Método responsável por trazer todos os usuários cadastrados na base do sistema de monitoria
	 * </p>
	 * @return uma lista de objetos do tipo {@code Usuário} representando os vários usuários cadastrados na base de dados
	 */
	public List<Usuario> consultaUsuarios()
	{
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
		
		return usuarios;
	}
	
	/**
	 * <p>Método que valida se um determinado usuário pertence ao grupo COMISSAO de permissões
	 * </p>
	 * @param usuario Objeto do tipo {@code Usuário} que representa o usuário à ser consultado
	 * @return {@code true} para o caso do usuário estar no grupo COMISSAO ou {@code false} caso o contrário
	 */
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
	
	/**
	 * <p>Método responsável por retirar um determinado usuário do grupo COMISSAO
	 * </p>
	 * @param usuario Objeto do tipo {@code Usuário} que representa o usuário à ser excluído do grupo COMISSAO
	 */
	public void revokeComissao(Usuario usuario)
	{
		List<Grupo> grupos = usuario.getGrupos();
		
		for(int i = 0; i < grupos.size(); i++) {
			Grupo grupo = grupos.get(i);
			
			if(grupo.getGrupo().equals(Grupos.COMISSAO)) {
				usuario.getGrupos().remove(grupo);
				
				if(!em.contains(grupo)) {
					grupo = em.merge(grupo); 
				}
				
				em.remove(grupo);
			}
		}
		
		em.merge(usuario);
		em.flush();
	}
	
	/**
	 * <p>Método que concede permissões de COMISSAO à um usuário, por meio da adição do mesmo ao grupo COMISSAO
	 * </p>
	 * @param usuario Objeto do tipo {@code Usuário} que representa o usuário à ser incluído na comissão
	 */
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
			
			em.persist(newComissao);
			
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
