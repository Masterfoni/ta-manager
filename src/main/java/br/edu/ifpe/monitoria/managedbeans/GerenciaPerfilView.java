package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;

@ManagedBean (name="gerenciaPerfilView")
@ViewScoped
public class GerenciaPerfilView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuariobean;

	public Usuario usuarioAtualizado;
	
	public Usuario getUsuarioAtualizado() {
		return usuarioAtualizado;
	}

	public void setUsuarioAtualizado(Usuario usuarioAtualizado) {
		this.usuarioAtualizado = usuarioAtualizado;
	}

	@PostConstruct
	public void init() {
		usuarioAtualizado = new Usuario();
	}
	
	public void alteraUsuario(Usuario usuario) {
		usuarioAtualizado = usuario;
	}
	
	public void persisteAlteracao() {
		usuariobean.atualizaUsuario(usuarioAtualizado);
	}
}
