package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;

@ManagedBean (name="menuView")
@SessionScoped
public class MenuView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoriaLocalBean monitoriaBean;
	
	@EJB
	AlunoLocalBean alunoBean;
	
	@EJB
	private EditalLocalBean editalBean;
	
	private Edital editalGlobal;
	
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
		editalGlobal = editalBean.consultaEditaisVigentes().size() > 0 ?  editalBean.consultaEditaisVigentes().get(0) : null;
		
		if (aluno) {
			monitor = monitoriaBean.isCurrentMonitor(alunoBean.consultaAlunoById((Long)session.getAttribute("id")));
		} else {
			monitor = false;
		}
	}
	
	public Edital getEditalGlobal() {
		return editalGlobal;
	}

	public void setEditalGlobal(Edital editalGlobal) {
		if(editalGlobal != null && editalGlobal.getNumeroEdital().equals("Selecione um Edital")) {
			this.editalGlobal = null;
		}else {
			this.editalGlobal = editalGlobal;
		}
	}

	public List<Edital> getEditais() {
		List<Edital> editais = new ArrayList<>();
		Edital fake = new Edital();
		fake.setNumeroEdital("Selecione um Edital");
		fake.setId(-1L);
		editais.add(fake);
		editais.addAll(editalBean.consultaEditaisVigentes());
		return editais;
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