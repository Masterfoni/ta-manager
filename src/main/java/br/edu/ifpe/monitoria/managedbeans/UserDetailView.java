package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localBean.UsuarioLocalBean;

@ManagedBean(name = "userDetailView")
public class UserDetailView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuarioBean;
	
	private Long userId;

	private Usuario usuario = new Usuario();

	private String email;
	
	private String nome;
	
	private String senha;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean updateUsuario() {
		//usuario = new Usuario();
		
		//usuario.setId(userId);
		//usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
	
		usuarioBean.atualizaUsuario(usuario);
		
		return true;
	}
	
}
