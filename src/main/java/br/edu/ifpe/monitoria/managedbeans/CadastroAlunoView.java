package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

@ManagedBean (name="cadastroAlunoView")
@ViewScoped
public class CadastroAlunoView implements Serializable {

	private static final long serialVersionUID = 8504345217031427227L;

	private Aluno aluno;
	private PerfilGoogle perfilGoogle;
	private String email;
	private String nome;

	@EJB
	private PerfilGoogleLocalBean pglBean;

	@EJB
	private UsuarioLocalBean usuarioBean;

	@EJB
	private AlunoLocalBean alunoBean;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		HttpSession session = (HttpSession) ec.getSession(false);
		aluno = new Aluno();
		
		if(session.getAttribute("perfilGoogle") == null && perfilGoogle == null) {
			String uri = "/welcome.xhtml?faces-redirect=true";
			fc.getApplication().getNavigationHandler().handleNavigation(fc, null, uri);
		} else if((boolean)session.getAttribute("isServidor")) {
			String uri = "/publico/cadastroServidor.xhtml?faces-redirect=true";
			fc.getApplication().getNavigationHandler().handleNavigation(fc, null, uri);
		}

		perfilGoogle = (PerfilGoogle) session.getAttribute("perfilGoogle");
		email = (String) session.getAttribute("email");
		nome = (String) session.getAttribute("nome");
	}

	public void salvarAluno() throws ServletException {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		if(isAlunoValido()) {
			aluno.setEmail(email);
			aluno.setNome(nome);
			perfilGoogle.setUsuario(aluno);
			pglBean.persistePerfilGoogle(perfilGoogle, true);
			
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			request.logout();

			HttpSession session = reconstruirSessao();
			session.setAttribute("usuario", aluno);
			session.setAttribute("id", aluno.getId());
			
			request.login(email, perfilGoogle.getSubject());

			fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "/comum/homepage.xhtml?faces-redirect=true");
		}
	}
	
	private HttpSession reconstruirSessao() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		HttpSession session = (HttpSession)ec.getSession(false);
		session.invalidate();
		
		return (HttpSession)ec.getSession(true);
	}
	
	private boolean isAlunoValido() {
		boolean result = true;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (usuarioBean.consultaUsuarioPorCpf(aluno.getCpf()) != null) {
			context.addMessage(null, new FacesMessage("CPF já cadastrado!"));
			result = false;
		} else if (usuarioBean.consultaUsuarioPorRg(aluno.getRg()).result != null) {
			context.addMessage(null, new FacesMessage("RG já cadastrado!"));
			result = false;
		} else if (alunoBean.consultaAlunoByMatricula(aluno.getMatricula()).result != null) {
			context.addMessage(null, new FacesMessage("Matricula já cadastrado!"));
			result = false;
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
