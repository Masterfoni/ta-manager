package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
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

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.entidades.Servidor.Titulacao;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;

@ManagedBean (name="cadastroServidorView")
@ViewScoped
public class CadastroServidorView implements Serializable {

	private static final long serialVersionUID = 5746606365793540925L;
	
	private Servidor servidor;
	private PerfilGoogle perfilGoogle;
	private String email;
	private String nome;
	
	@EJB
	private PerfilGoogleLocalBean pglBean;
	
	@EJB
	private UsuarioLocalBean usuarioBean;
	
	@EJB
	private ServidorLocalBean servidorBean;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		HttpSession session = (HttpSession)ec.getSession(false);
		
		servidor = new Servidor();
		
		if(session.getAttribute("perfilGoogle") == null && perfilGoogle == null) {
			String uri = "/welcome.xhtml?faces-redirect=true";
			fc.getApplication().getNavigationHandler().handleNavigation(fc, null, uri);
		} else if(!(boolean)session.getAttribute("isServidor")) {
			String uri = "/publico/cadastroAluno.xhtml?faces-redirect=true";
			fc.getApplication().getNavigationHandler().handleNavigation(fc, null, uri);
		}
		
		perfilGoogle = (PerfilGoogle)session.getAttribute("perfilGoogle");
		email= (String)session.getAttribute("email");
		nome = (String)session.getAttribute("nome");
	}
	
	public void salvarProfessor() throws ServletException, IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		if(isProfessorValido()) {
			servidor.setEmail(email);
			servidor.setNome(nome);
			perfilGoogle.setUsuario(servidor);
			pglBean.persistePerfilGoogle(perfilGoogle);
			
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			request.logout();

			HttpSession session = reconstruirSessao();
			session.setAttribute("usuario", servidor);
			session.setAttribute("id", servidor.getId());
			
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
	
	private boolean isProfessorValido() {
		boolean result = true;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(usuarioBean.consultaUsuarioPorCpf(servidor.getCpf()) != null) {
			context.addMessage(null, new FacesMessage("CPF já cadastrado!"));
			result = false;
		} else if(usuarioBean.consultaUsuarioPorRg(servidor.getRg()).result != null) {
			context.addMessage(null, new FacesMessage("RG já cadastrado!"));
			result = false;
		} else if(servidorBean.findServidorBySiape(servidor.getSiape()) != null) {
			context.addMessage(null, new FacesMessage("SIAPE já cadastrado!"));
			result = false;
		}
		
		return result;
	}
	
	public Servidor getServidor() {
		return servidor;
	}
	
	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public PerfilGoogle getPerfilGoogle() {
		return perfilGoogle;
	}

	public void setPerfilGoogle(PerfilGoogle perfilGoogle) {
		this.perfilGoogle = perfilGoogle;
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

	public Titulacao[] getTitulos() {
		return Titulacao.values();
	}	
}
