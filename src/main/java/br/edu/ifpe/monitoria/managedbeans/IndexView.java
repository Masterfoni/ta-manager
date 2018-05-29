package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
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
import br.edu.ifpe.monitoria.utils.LongRequestResult;

@ManagedBean (name="indexView")
public class IndexView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuarioBean;

	@EJB
	private AlunoLocalBean alunoBean;

	private Usuario usuario;

	private Aluno aluno;
	
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
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		HttpServletRequest request = (HttpServletRequest) extContext.getRequest();
		
		if(request.getRemoteUser() != null)
		{
			try {
				extContext.redirect("../comum/homepage.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cadastrarAluno()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(usuarioBean.consultaUsuarioPorEmail(aluno.getEmail()) != null)
		{
			context.addMessage(null, new FacesMessage("E-mail já cadastrado!"));
		}
		else if(usuarioBean.consultaUsuarioPorRg(aluno.getRg()) != null)
		{
			context.addMessage(null, new FacesMessage("RG já cadastrado!"));
		}
		else if(usuarioBean.consultaUsuarioPorCpf(aluno.getCpf()) != null)
		{
			context.addMessage(null, new FacesMessage("CPF já cadastrado!"));
		}
		else if(alunoBean.consultaAlunoByMatricula(aluno.getMatricula()) != null)
		{
			context.addMessage(null, new FacesMessage("Matricula já cadastrada!"));
		}
		else if(alunoBean.persisteAluno(aluno, true))
		{
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
			aluno = new Aluno();
		}
	}

	public String loginUsuario()
	{
		String loginResult = "sucesso";

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext ec = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		String email = usuario.getEmail();
		LongRequestResult userIdResult = usuarioBean.consultarIdByEmail(email);
		
		if(email.substring(email.indexOf("@")).equals("@a.recife.ifpe.edu.br"))
		{
			facesContext.addMessage(null, new FacesMessage("Servidor, realize login pelo botão \"Login Servidor\" com seu email institucional."));
			loginResult = "falha";
		}
		else if(!userIdResult.hasErrors())
		{
			session.setAttribute("id", userIdResult.data);
			session.setAttribute("email", usuario.getEmail());
			
			try {
				request.login(usuario.getEmail(), usuario.getSenha());
			} catch (ServletException e) {
				e.printStackTrace();
				
				facesContext.addMessage(null, new FacesMessage("Senha ou usuário inválidos!"));
				
				loginResult = "falha";
			}
		}
		else
		{
			for(String error : userIdResult.errors)
			{
				facesContext.addMessage(null, new FacesMessage(error));
			}
			
			loginResult = "falha";
		}
		
		return loginResult;
	}
}
