package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;

@ManagedBean (name="indexView")
public class IndexView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuarioBean;

	@EJB
	private AlunoLocalBean alunoBean;

	private Usuario usuario;

	private Aluno aluno;

	FacesContext facesContext;
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public IndexView() {
		usuario = new Usuario();
		aluno = new Aluno();
	}

	public void cadastrarAluno()
	{
		if(alunoBean.persisteAluno(aluno))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}

	public String loginUsuario()
	{
			try {
				facesContext = FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
				request.login(usuario.getEmail(), usuario.getSenha());
				facesContext.getExternalContext().getSession(true);
			} 
			catch (ServletException e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha ou usuário inválidos!", null);
		        facesContext.addMessage(null, message);
		        return "index";
			}
			
			return "homepage";
	}
}
