package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.RelatorioFinal;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.RelatorioFinalLocalBean;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;
import br.edu.ifpe.monitoria.utils.RelatorioFinalRequestResult;

@ManagedBean (name="relatorioFinalView")
@ViewScoped
public class RelatorioFinalView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private RelatorioFinalLocalBean relatorioFinalBean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
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
	}
	
	public void salvarRelatorio() {
		if(relatorioAtual.getId() == null) {
			relatorioAtual.setMonitoria(monitoriaAtual);
			CriacaoRequestResult persisteRelatorio = relatorioFinalBean.persisteRelatorio(relatorioAtual);
			
			if(persisteRelatorio.hasErrors()) {
				for(String erro : persisteRelatorio.errors) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(erro));
				}
			}
		} else {
			AtualizacaoRequestResult atualizacaoResultado = relatorioFinalBean.atualizaRelatorio(relatorioAtual);
			
			if(atualizacaoResultado.hasErrors()) {
				for(String erro : atualizacaoResultado.errors) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(erro));
				}
			}
		}
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
