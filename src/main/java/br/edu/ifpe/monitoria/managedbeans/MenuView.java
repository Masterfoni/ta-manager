package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;

@ManagedBean (name="menuView")
@SessionScoped
public class MenuView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoriaLocalBean monitoriaBean;
	
	@EJB
	AlunoLocalBean alunoBean;
	
	boolean comissao;
	
	boolean aluno;
	
	boolean professor;
	
	boolean monitor;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("comissao");
		aluno = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("aluno");
		professor = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("professor");
		
		if (aluno) {
			monitor = monitoriaBean.isCurrentMonitor(alunoBean.consultaAlunoById((Long)session.getAttribute("id")));
		} else {
			monitor = false;
		}
	}

	public boolean isComissao() {
		return comissao;
	}

	public void setComissao(boolean comissao) {
		this.comissao = comissao;
	}

	public boolean isAluno() {
		return aluno;
	}

	public void setAluno(boolean aluno) {
		this.aluno = aluno;
	}

	public boolean isProfessor() {
		return professor;
	}

	public void setProfessor(boolean professor) {
		this.professor = professor;
	}

	public boolean isMonitor() {
		return monitor;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}
}