package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.entidades.Servidor.Titulacao;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;

@ManagedBean  (name="cadastroServidorView")
public class CadastroServidorView implements Serializable{

	private static final long serialVersionUID = 5746606365793540925L;
	
	private Servidor servidor;
	private PerfilGoogle perfilGoogle;
	private String email;
	private String nome;
	
	private String jackson;
	
	@EJB
	private PerfilGoogleLocalBean pglBean;
	
	public String salvarProfessor(){
		
		servidor.setEmail(email);
		servidor.setNome(nome);
		perfilGoogle.setUsuario(servidor);
		pglBean.persistePerfilGoogle(perfilGoogle);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession)ec.getSession(true);
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		
		try {
			request.login(email, perfilGoogle.getSubject());
			session.setAttribute("usuario", servidor);
			return "sucesso";
//			ec.redirect("comum/homepage.xhtml");
		} catch (ServletException e) {
			e.printStackTrace();
			return "falha";
		}
	}
	
	@PostConstruct
	public void carregarInfo()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession)ec.getSession(true);
		perfilGoogle = (PerfilGoogle)session.getAttribute("perfilGoogle");
		email= (String)session.getAttribute("email");
		nome = (String)session.getAttribute("nome");
		servidor = new Servidor();
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

	public String getJackson() {
		return jackson;
	}

	public void setJackson(String jackson) {
		this.jackson = jackson;
	}
	
}
