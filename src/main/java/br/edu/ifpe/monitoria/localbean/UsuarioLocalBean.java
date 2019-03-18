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
import br.edu.ifpe.monitoria.utils.UsuarioRequestResult;

/**
* Classe responsável pela execução de operações sobre a entidade Usuário,
* utilizando o {@code EntityManager} como interface para interagir com a base.
* <p> Contexto de persistencia: <strong>monitoria</strong></p>
*  
* @author João Vitor
* @author Felipe Araújo
* 
*/
@Stateless
@LocalBean
public class UsuarioLocalBean {
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
	
	/**
	 * <p>Método que atualiza informações de um usuário, especificamente: Email, nome e senha.
	 * </p>
	 * @param usuario Objeto do tipo {@code Usuário} que representa o usuário com as informações atualizadas
	 * @return {@code true} para o caso da operação bem sucedida ou {@code false} caso o contrário
	 */
	public boolean atualizaUsuario(Usuario usuario)
	{
		try {
			Usuario usuarioAtualizar = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", usuario.getId()).getSingleResult();
			
			usuarioAtualizar.setEmail(usuario.getEmail());
			usuarioAtualizar.setNome(usuario.getNome());
			usuarioAtualizar.setSenha(usuario.getSenha());
			
			em.merge(usuarioAtualizar);			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>Método que busca um determinado usuário através do seu email e sua senha
	 * </p>
	 * @param email Objeto do tipo {@code String} representando o e-mail
	 * @param senha Objeto do tipo {@code String} representando a senha
	 * @return {@code Usuario} o usuário encontrado ou {@code null} caso não encontre nenhum
	 */
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
	
	/**
	 * <p>Método que busca um determinado usuário através apenas do seu e-mail
	 * </p>
	 * @param email Objeto do tipo {@code String} representando o e-mail
	 * @return {@code Usuario} o usuário encontrado ou {@code null} caso não encontre nenhum
	 */
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
	
	/**
	 * <p>Método que busca um determinado usuário através apenas do seu RG
	 * </p>
	 * @param rg Objeto do tipo {@code String} representando o rg
	 * @return {@code UsuarioRequestResult} um objeto contendo o usuário encontrado ou {@code null} caso não encontre nenhum dentro da propriedade result
	 */
	public UsuarioRequestResult consultaUsuarioPorRg(String rg) {
		
		UsuarioRequestResult userResult = new UsuarioRequestResult();
		
		try {
			userResult.result = em.createNamedQuery("Usuario.findByRg", Usuario.class).setParameter("rg", rg).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			userResult.errors.add("Nenhum usuário encontrado com este RG");
		}
		
		return userResult;
	}
	
	/**
	 * <p>Método que busca um determinado usuário através apenas do cpf
	 * </p>
	 * @param cpf Objeto do tipo {@code String} representando o cpf
	 * @return {@code Usuario} o usuário encontrado ou {@code null} caso não encontre nenhum
	 */
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
	
	/**
	 * <p>Método que busca um determinado usuário através do ID
	 * </p>
	 * @param id Objeto do tipo {@code Long} representando o cpf
	 * @return {@code Usuario} o usuário encontrado ou {@code null} caso não encontre nenhum
	 */
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
	
	/**
	 * <p>Método que busca usuários por um determinado nome, no caso desses metodos a palavra inteira tem de ser igual, ignorando apenas uppercase.
	 * </p>
	 * @param nome Objeto do tipo {@code String} representando o nome
	 * @return {@code List<Usuario>} lista de usuários encontrados com aquele nome
	 */
	public List<Usuario> consultaUsuarioByName(String nome)
	{
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findByNome", Usuario.class).setParameter("nome", nome).getResultList();
		
		return usuarios;
	}
	
	/**
	 * <p>Método que busca o ID de um usuário pelo e-mail
	 * </p>
	 * @param email Objeto do tipo {@code String} representando o email
	 * @return {@code LongRequestResult} objeto contendo o ID do usuário no caso de sucesso ou contendo uma lista de mensagens de erros.
	 */
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
	
	/**
	 * <p>Método que deleta o usuário da base por meio do seu identificador único
	 * </p>
	 * @param id Objeto do tipo {@code Long} representando o identificador único do usuário
	 * @return {@code DelecaoRequestResult} objeto contendo {@code true} na variável {@code data} no caso de sucesso ou
	 * contendo {@code false} na variável {@code data} aliado à uma lista de mensagens de erro.
	 */
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
	
	/**
	 * <p>Método que persiste um determinado usuário na base
	 * </p>
	 * @param usuario Objeto do tipo {@code Usuario} à ser persistido
	 * @return {@code true} ao persistir com sucesso
	 */
	public boolean persisteUsuario(@NotNull @Valid Usuario usuario)
	{
		em.persist(usuario);
		
		return true;
	}
}
