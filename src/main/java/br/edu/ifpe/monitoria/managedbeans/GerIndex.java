package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Usuario;

@ManagedBean (name="gerIndex")
public class GerIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GerUsuarioBean usuarioBean;
	
	private List<Usuario> usuarios;

	private Usuario usuario;

	private String email;

	private String senha;

	private String nomeUsuario;
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

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

	@PostConstruct
	public void init() 
	{
		usuarios = usuarioBean.consultaUsuarios();
	}
	
	public boolean deletarUsuario(Usuario usuario) {
		usuarios.remove(usuario);
		
		return usuarioBean.deletaUsuario(usuario.getId());
	}
	
	public boolean criarUsuario() {
		usuario = new Usuario();

		usuario.setEmail(email);
		usuario.setSenha(senha);
		usuario.setNome(nomeUsuario);

		if(usuarioBean.persisteUsuario(usuario))
		{
			usuarios.add(usuario);
			return true;
		}
		else
			return false;
	}
}
