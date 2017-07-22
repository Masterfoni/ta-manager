package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;

@ManagedBean (name="inscricaoMonitoriaView")
@ViewScoped
public class InscricaoMonitoriaView implements Serializable{
	
	private static final long serialVersionUID = -2822785320179539798L;
	
	@EJB
	private PlanoMonitoriaLocalBean planoBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	private Monitoria monitoria;
	
	private List<PlanoMonitoria> planos;

	public InscricaoMonitoriaView() {
		planos = new ArrayList<PlanoMonitoria>();
		monitoria = new Monitoria();
	}
	
	@PostConstruct
	public void init() {
		planos = planoBean.consultaPlanos();
	}
	
	public void selecionarPlano(PlanoMonitoria plano) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext(); 
		HttpSession session = (HttpSession) ec.getSession(false);
		
		Long id = (Long) session.getAttribute("id");
		System.out.println(id);
		Aluno aluno = alunoBean.consultaAlunoById(id);
		
		monitoria.setPlanoMonitoria(plano);
		monitoria.setAluno(aluno);
		monitoriaBean.persisteMonitoria(monitoria);
	}
	
	public boolean temBolsa(PlanoMonitoria plano) {
		if(plano.getBolsas() > 0) {
			return true;
		}
		return false;
	}
	
	public List<PlanoMonitoria> getPlanos() {
		return planos;
	}

	public void setPlanos(ArrayList<PlanoMonitoria> planos) {
		this.planos = planos;
	}

	public void setPlanos(List<PlanoMonitoria> planos) {
		this.planos = planos;
	}
	
	public Monitoria getMonitoria() {
		return monitoria;
	}

	public void setMonitoria(Monitoria monitoria) {
		this.monitoria = monitoria;
	}

}
