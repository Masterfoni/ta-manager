package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localBean.UsuarioLocalBean;

@ManagedBean (name="indexView")
public class IndexView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuarioBean;
	
	private List<Usuario> usuarios;

	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void consultarUsuarioEmail()
	{
		String email = usuario.getEmail();
		
		if(email == null || email.isEmpty())
			usuarios = usuarioBean.consultaUsuarios();
		else
		{
			if(usuarios != null && !usuarios.isEmpty())
			{
				usuarios.clear();
				usuarios.add(usuarioBean.consultaUsuario(email));
			}
			else
				usuarios.add(usuarioBean.consultaUsuario(email));
		}
	}
}
