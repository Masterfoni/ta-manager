package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.RelatorioFinal;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.RelatorioFinalLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.RelatorioFinalRequestResult;

@ManagedBean (name="relatorioFinalView")
@ViewScoped
public class RelatorioFinalView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private RelatorioFinalLocalBean relatorioFinalBean;
	
	@EJB
	private ServidorLocalBean servidorbean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private EditalLocalBean editalBean;
	
	public List<Curso> cursos;
	
	public List<ComponenteCurricular> componentes;
	
	public List<Aluno> monitores;
	
	public RelatorioFinal relatorioAtual;
	
	private Aluno loggedAluno;
	
	private Edital editalVigente;
	
	private Monitoria monitoriaAtual;
	
	private boolean monitor;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedAluno = alunoBean.consultaAlunoById((Long)session.getAttribute("id")); 
		
		monitor = monitoriaBean.isCurrentMonitor(loggedAluno);
		
		componentes = new ArrayList<ComponenteCurricular>();
		cursos = new ArrayList<Curso>();
		monitores = new ArrayList<Aluno>();
	}
	
	public void salvarRelatorio() {
		if(relatorioAtual.getId() == null) {
			relatorioAtual.setMonitoria(monitoriaAtual);
			relatorioFinalBean.persisteRelatorio(relatorioAtual);
		} else {
			AtualizacaoRequestResult atualizacaoResultado = relatorioFinalBean.atualizaRelatorio(relatorioAtual);
			
			if(atualizacaoResultado.hasErrors()) {
				System.out.println(atualizacaoResultado.errors);
			}
		}
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<ComponenteCurricular> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
	}

	public List<Aluno> getMonitores() {
		return monitores;
	}

	public void setMonitores(List<Aluno> monitores) {
		this.monitores = monitores;
	}

	public RelatorioFinal getRelatorioAtual() {
		if(relatorioAtual == null) {
			RelatorioFinalRequestResult rfMonitor = relatorioFinalBean.consultaRelatorioFinalPorMonitor(loggedAluno.getId());
			
			if(rfMonitor.hasErrors()) { 
				relatorioAtual = new RelatorioFinal();
			} else {
				relatorioAtual = rfMonitor.result;
			}
		}
		
		return relatorioAtual;
	}

	public void setRelatorioAtual(RelatorioFinal relatorioAtual) {
		this.relatorioAtual = relatorioAtual;
	}

	public Aluno getLoggedAluno() {
		return loggedAluno;
	}

	public void setLoggedAluno(Aluno loggedAluno) {
		this.loggedAluno = loggedAluno;
	}

	public boolean isMonitor() {
		return monitor;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}

	public Edital getEditalVigente() {
		return editalVigente;
	}

	public void setEditalVigente(Edital editalVigente) {
		this.editalVigente = editalVigente;
	}

	public Monitoria getMonitoriaAtual() {
		this.monitoriaAtual = monitoriaBean.consultaMonitoriaAtualPorAluno(loggedAluno);
		return monitoriaAtual;
	}

	public void setMonitoriaAtual(Monitoria monitoriaAtual) {
		this.monitoriaAtual = monitoriaAtual;
	}
}
