package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
	
	public Usuario usuarioAtualizado;
	
	public String nomeBusca;
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public Usuario getUsuarioAtualizado() {
		return usuarioAtualizado;
	}

	public void setUsuarioAtualizado(Usuario usuarioAtualizado) {
		this.usuarioAtualizado = usuarioAtualizado;
	}

	public GerenciaUsuarioView() {
		nomeBusca = "";
		usuarios = new ArrayList<Usuario>();
		usuarioAtualizado = new Usuario();
	}
	
	@PostConstruct
	public void init() {
		usuarios = usuariobean.consultaUsuarios();
		
		if(!usuarios.isEmpty())
			usuarioAtualizado = usuarios.get(0);
	}
	
	public void buscaUsuario() {
		if(nomeBusca.isEmpty())
			System.out.println(this.usuarios);
		else
			this.usuarios = usuariobean.consultaUsuarioByName("%"+nomeBusca+"%");
	}
	
	public String deletaUsuario(Usuario usuario) {
		usuarios.remove(usuario);
		
		usuariobean.deletaUsuario(usuario.getId());
		
		return "";
	}
	
	public void alteraUsuario(Usuario usuario) {
		usuarioAtualizado = usuario;
	}
	
	public void persisteAlteracao() {
		usuariobean.atualizaUsuario(usuarioAtualizado);
	}
}
