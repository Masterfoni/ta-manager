package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;

@ManagedBean (name="gerenciaUsuarioView")
public class GerenciaUsuarioView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuariobean;

	public List<Usuario> usuarios;
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public GerenciaUsuarioView() {
	}
	
	public void buscaUsuario(String email, String nome) {
		
	}
	
	public List<Usuario> listagemUsuarios() {
		usuarios = usuariobean.consultaUsuarios();
		return usuarios;
	}
}
