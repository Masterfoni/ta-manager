package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Administrativo;
import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Professor;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;

@ManagedBean  (name="cadastroServidorView")
public class CadastroServidorView implements Serializable{

	private static final long serialVersionUID = 5746606365793540925L;
	private Professor professor;
	private Administrativo administrativo;
	private PerfilGoogle perfilGoogle;
	private String tipo;
	String email;
	String nome;
	
	@EJB
	private PerfilGoogleLocalBean pglBean;
	
	public String salvar(){
		professor.setEmail(email);
		professor.setNome(nome);
		perfilGoogle.setUsuario(professor);
		pglBean.persistePerfilGoogle(perfilGoogle);
		return "home";
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
		professor = new Professor();
		administrativo = new Administrativo();
	}
	
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public Administrativo getAdministrativo() {
		return administrativo;
	}

	public void setAdministrativo(Administrativo administrativo) {
		this.administrativo = administrativo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
