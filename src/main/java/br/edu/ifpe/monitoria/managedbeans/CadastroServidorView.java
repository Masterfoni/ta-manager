package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Administrativo;
import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Professor;
import br.edu.ifpe.monitoria.entidades.Professor.Titulacao;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;

@ManagedBean  (name="cadastroServidorView")
public class CadastroServidorView implements Serializable{

	private static final long serialVersionUID = 5746606365793540925L;
	
	private Professor professor;
	private Administrativo administrativo;
	private PerfilGoogle perfilGoogle;
	private String tipo;
	private String email;
	private String nome;
	
	private String senha;
	private String cpf;
	private String rg;
	private String rgEmissor;
	private String sexo;

	private Integer siape; 
	private Titulacao titulo;
	
	private String jackson;
	
	@EJB
	private PerfilGoogleLocalBean pglBean;
	
	public String cadastrar(){
		
		if(tipo.equals("prof")){
			professor.setEmail(email);
			professor.setCpf(cpf);
			professor.setNome(nome);
			professor.setRg(rg);
			professor.setRgEmissor(rgEmissor);
			professor.setSenha(senha);
			professor.setSexo(sexo);
			professor.setSiape(siape);
			professor.setTitulacao(titulo);
			perfilGoogle.setUsuario(professor);
			pglBean.persistePerfilGoogle(perfilGoogle);
		}
		else {
			administrativo.setCpf(cpf);
			administrativo.setEmail(email);
			administrativo.setNome(nome);
			administrativo.setRg(rg);
			administrativo.setRgEmissor(rgEmissor);
			administrativo.setSenha(senha);
			administrativo.setSexo(sexo);
			administrativo.setSiape(siape);
			perfilGoogle.setUsuario(administrativo);
			pglBean.persistePerfilGoogle(perfilGoogle);
		}
		return "homepage";
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
		tipo = "prof";
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

	public Titulacao[] getTitulos() {
		return Titulacao.values();
	}

	public String getJackson() {
		return jackson;
	}

	public void setJackson(String jackson) {
		this.jackson = jackson;
	}

	public Titulacao getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulacao titulo) {
		this.titulo = titulo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRgEmissor() {
		return rgEmissor;
	}

	public void setRgEmissor(String rgEmissor) {
		this.rgEmissor = rgEmissor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getSiape() {
		return siape;
	}

	public void setSiape(Integer siape) {
		this.siape = siape;
	}
}
