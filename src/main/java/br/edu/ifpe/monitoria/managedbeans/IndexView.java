package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		String email = usuario.getEmail();
		Long id = usuarioBean.consultarIbByEmail(email);
		
		facesContext = FacesContext.getCurrentInstance();
		ExternalContext ec = facesContext.getExternalContext();
		if(email.substring(email.indexOf("@")).equals("@a.recife.ifpe.edu.br"))
		{
			System.out.println(email.substring(email.indexOf("@")));
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Servidor, realizar login pelo botão \"Login Servidor\" com seu email instituncional.", null);
			facesContext.addMessage(null, message);
			return "falha";
		}
		else
		{
			try {
				HttpServletRequest request = (HttpServletRequest) ec.getRequest();
				request.login(usuario.getEmail(), usuario.getSenha());
				HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(true);
				session.setAttribute("id", id);
				session.setAttribute("email", usuario.getEmail());
			} 
			catch (ServletException e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha ou usuário inválidos!", null);
				facesContext.addMessage(null, message);
				return "falha";
			}
		}
		
		return "sucesso";
	}
}
