package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.entidades.Servidor.Titulacao;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;

@ManagedBean (name="cadastroServidorView")
@SessionScoped
public class CadastroServidorView implements Serializable{

	private static final long serialVersionUID = 5746606365793540925L;
	
	private Servidor servidor;
	private PerfilGoogle perfilGoogle;
	private String email;
	private String nome;
	
	FacesContext facesContext;
	
	@EJB
	private PerfilGoogleLocalBean pglBean;
	
	@EJB
	private UsuarioLocalBean usuarioBean;
	
	@EJB
	private ServidorLocalBean servidorBean;
	
	@PostConstruct
	public void init()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		HttpSession session = (HttpSession)ec.getSession(false);
		
		servidor = new Servidor();
		
		if(session.getAttribute("perfilGoogle") == null && perfilGoogle == null) {
			try {
				ec.redirect("/welcome.xhtml");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!(boolean)session.getAttribute("isServidor")) {
			try {
				ec.redirect("/publico/cadastroAluno.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		perfilGoogle = (PerfilGoogle)session.getAttribute("perfilGoogle");
		email= (String)session.getAttribute("email");
		nome = (String)session.getAttribute("nome");
	}
	
	public String salvarProfessor() throws ServletException
	{
		String result = "falha";
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(usuarioBean.consultaUsuarioPorCpf(servidor.getCpf()) != null)
		{
			context.addMessage(null, new FacesMessage("CPF já cadastrado!"));
		}
		else if(usuarioBean.consultaUsuarioPorRg(servidor.getRg()) != null)
		{
			context.addMessage(null, new FacesMessage("RG já cadastrado!"));
		}
		else if(servidorBean.findServidorBySiape(servidor.getSiape()) != null)
		{
			context.addMessage(null, new FacesMessage("SIAPE já cadastrado!"));
		}
		else 
		{
			servidor.setEmail(email);
			servidor.setNome(nome);
			perfilGoogle.setUsuario(servidor);
			pglBean.persistePerfilGoogle(perfilGoogle);
			
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession)ec.getSession(false);
			
			session.setAttribute("usuario", servidor);
			session.setAttribute("id", servidor.getId());
			
			result = "sucesso";
			
//			try {
//				request.login(email, perfilGoogle.getSubject());
//			} catch (ServletException e) {
//				throw e;
//			}
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
