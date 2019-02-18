package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.entidades.Servidor.Titulacao;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;
import br.edu.ifpe.monitoria.utils.SessionContext;

@ManagedBean (name="cadastroServidorView")
@ViewScoped
public class CadastroServidorView implements Serializable {

	private static final long serialVersionUID = 5746606365793540925L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
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
	public void carregarInfo() {
		servidor = new Servidor();
		perfilGoogle = new PerfilGoogle();
		SessionContext sessionContext = SessionContext.getInstance();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		Boolean isServidor = (Boolean) sessionContext.getAttribute("isServidor");
		
		if(isServidor != null && !isServidor) {
			try {
				ec.redirect("../publico/cadastroAluno.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		perfilGoogle = (PerfilGoogle) sessionContext.getAttribute("perfilGoogle");
		email = (String) sessionContext.getAttribute("email");
		nome = (String) sessionContext.getAttribute("nome");
		
		if (perfilGoogle != null) {
			sharedMenuView.setMyPerfilGoogle(perfilGoogle);
		}
		
		if (email != null) {
			sharedMenuView.setMyEmail(email);
		}		
	}
	
	public void salvarProfessor() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionContext sessionContext = SessionContext.getInstance();
		
		if(usuarioBean.consultaUsuarioPorCpf(servidor.getCpf()) != null) {
			context.addMessage(null, new FacesMessage("CPF já cadastrado!"));
		} else if(usuarioBean.consultaUsuarioPorRg(servidor.getRg()) != null) {
			context.addMessage(null, new FacesMessage("RG já cadastrado!"));
		} else if(servidorBean.findServidorBySiape(servidor.getSiape()) != null) {
			context.addMessage(null, new FacesMessage("SIAPE já cadastrado!"));
		} else {
			ExternalContext ec = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			
			System.out.println("Setando um servidor com email: " + sharedMenuView.getMyEmail() + " e nome: " + nome);
			System.out.println("Meu perfilGoogle da property: " + sharedMenuView.getMyPerfilGoogle());
			System.out.println("Meu perfilGoogle do bean: " + perfilGoogle);
			
			if(perfilGoogle == null) {
				perfilGoogle = sharedMenuView.getMyPerfilGoogle();
			}
			
			servidor.setEmail(sharedMenuView.getMyEmail());
			servidor.setNome(nome);
			perfilGoogle.setUsuario(servidor);
			pglBean.persistePerfilGoogle(perfilGoogle);
			
			sharedMenuView.setLastUsuario(servidor);
			
			try {
				request.login(email, perfilGoogle.getSubject());
				sessionContext.setAttribute("usuario", servidor);
				sessionContext.setAttribute("id", servidor.getId());
				
				ec.dispatch("../comum/homepage.xhtml");
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
