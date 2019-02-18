package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;
import br.edu.ifpe.monitoria.utils.SessionContext;

@ManagedBean (name="cadastroAlunoView")
@SessionScoped
public class CadastroAlunoView implements Serializable {

	private static final long serialVersionUID = 8504345217031427227L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
	private Aluno aluno;
	
	private PerfilGoogle perfilGoogle;
	
	private String email;
	
	private String nome;
	
	private String cpf;
	
	private String rg;
	
	private String orgao;
	
	private String matricula;
	
	@EJB
	private PerfilGoogleLocalBean pglBean;
	
	@EJB
	private UsuarioLocalBean usuarioBean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	@PostConstruct
	public void carregarInfo() {
		aluno = new Aluno();
		perfilGoogle = new PerfilGoogle();
		SessionContext sessionContext = SessionContext.getInstance();

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		Boolean isServidor = (Boolean) sessionContext.getAttribute("isServidor");
		
		if(isServidor != null && isServidor) {
			try {
				ec.redirect("../publico/cadastroServidor.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		perfilGoogle = (PerfilGoogle) sessionContext.getAttribute("perfilGoogle");
		email = (String) sessionContext.getAttribute("email");
		nome = (String) sessionContext.getAttribute("nome");
	}

	public void salvarAluno() {		
		FacesContext context = FacesContext.getCurrentInstance();
		SessionContext sessionContext = SessionContext.getInstance();
		
		if (usuarioBean.consultaUsuarioPorCpf(aluno.getCpf()) != null) {
			context.addMessage(null, new FacesMessage("CPF já cadastrado!"));
		} else if (usuarioBean.consultaUsuarioPorRg(aluno.getRg()) != null) {
			context.addMessage(null, new FacesMessage("RG já cadastrado!"));
		} else if (alunoBean.consultaAlunoByMatricula(aluno.getMatricula()) != null) {
			context.addMessage(null, new FacesMessage("Matricula já cadastrado!"));
		} else {
			ExternalContext ec = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			
			aluno.setEmail(email);
			aluno.setNome(nome);
			perfilGoogle.setUsuario(aluno);
			pglBean.persistePerfilGoogle(perfilGoogle, true);
			
			try {
				request.login(email, perfilGoogle.getSubject());
				sessionContext.setAttribute("usuario", aluno);
				sessionContext.setAttribute("id", aluno.getId());
				
				ec.dispatch("../comum/homepage.xhtml");
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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

	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
