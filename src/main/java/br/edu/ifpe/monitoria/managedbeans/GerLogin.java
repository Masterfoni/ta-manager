package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.monitoria.entidades.Usuario;

@ManagedBean (name="gerLogin")
public class GerLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
    private GerUsuarioBean usuarioBean;
	
	private Usuario usuario;
	
	private String email;

	private String senha;
	
	private String nomeUsuario;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public boolean criaUsuario() {
		usuario = new Usuario();
		
		usuario.setEmail(email);
		usuario.setSenha(senha);
		usuario.setNome(nomeUsuario);
		//return true;
		return usuarioBean.persisteUsuario(usuario);
	}
	

}
