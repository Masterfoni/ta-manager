package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;

@ManagedBean  (name="cadastroAlunoView")
public class CadastroAlunoView implements Serializable {

	private static final long serialVersionUID = 8504345217031427227L;
	
	private Aluno aluno;
	private PerfilGoogle perfilGoogle;
	private String email;
	private String nome;

	FacesContext facesContext;
	
	@EJB
	private PerfilGoogleLocalBean pglBean;
	
	@EJB
	private UsuarioLocalBean usuarioBean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	@PostConstruct
	public void carregarInfo()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession)ec.getSession(true);
		
		if(session.getAttribute("perfilGoogle") == null) {
			try {
				ec.redirect("../publico/index.xhtml");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if((boolean)session.getAttribute("isServidor")) {
			try {
				ec.redirect("../publico/cadastroServidor.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		perfilGoogle = (PerfilGoogle)session.getAttribute("perfilGoogle");
		email= (String)session.getAttribute("email");
		nome = (String)session.getAttribute("nome");
		aluno = new Aluno();
	}

	public String salvarAluno() {

		String result = "falha";
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(usuarioBean.consultaUsuarioPorCpf(aluno.getCpf()) != null)
		{
			context.addMessage(null, new FacesMessage("CPF já cadastrado!"));
		}
		else if(usuarioBean.consultaUsuarioPorRg(aluno.getRg()) != null)
		{
			context.addMessage(null, new FacesMessage("RG já cadastrado!"));
		}else if(alunoBean.consultaAlunoByMatricula(aluno.getMatricula()) != null)
		{
			context.addMessage(null, new FacesMessage("Matricula já cadastrado!"));
		}
		else {
			aluno.setEmail(email);
			aluno.setNome(nome);
			perfilGoogle.setUsuario(aluno);
			pglBean.persistePerfilGoogle(perfilGoogle, true);
			
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession)ec.getSession(true);
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			
			try {
				request.login(email, perfilGoogle.getSubject());
				session.setAttribute("usuario", aluno);
				session.setAttribute("id", aluno.getId());
				
				result = "sucesso";
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

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

	public PerfilGoogle getPerfilGoogle() {
		return perfilGoogle;
	}

	public void setPerfilGoogle(PerfilGoogle perfilGoogle) {
		this.perfilGoogle = perfilGoogle;
	}
}
