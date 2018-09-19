package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.RelatorioFinalLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.RelatorioFinalRequestResult;

@ManagedBean (name="relatorioFinalAvaliacaoView")
@ViewScoped
public class RelatorioFinalAvaliacaoView implements Serializable {

	private static final long serialVersionUID = -8481665492310127227L;

	private ComponenteCurricular componenteRelatorio;
	
	private List<Monitoria> monitorias;
	
	private PlanoMonitoria planoRelatorio;
	
	private Servidor loggedServidor;
	
	@EJB
	private PlanoMonitoriaLocalBean planoBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private RelatorioFinalLocalBean relatorioBean;
	
	@EJB
	private ServidorLocalBean servidorBean;

	public RelatorioFinalAvaliacaoView() {
		if(componenteRelatorio == null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			componenteRelatorio = (ComponenteCurricular) session.getAttribute("componenteRelatorio");
		}
	}
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id"));
		
		List<PlanoMonitoria> consultaPlanosByServidor = planoBean.consultaPlanosByServidor(loggedServidor.getId());
		
		for (int i = 0; i < consultaPlanosByServidor.size(); i++) {
			PlanoMonitoria planoMonitoria = consultaPlanosByServidor.get(i);
			
			if (planoMonitoria.getCc().getId() == componenteRelatorio.getId()) {
				planoRelatorio = planoMonitoria;
				break;
			}
		}
	}
	
	public List<Monitoria> getMonitorias() {
		if (monitorias == null) {
			monitorias = monitoriaBean.consultaMonitoriaByPlano(planoRelatorio);
		}
		
		return monitorias;
	}
	
	public boolean isRelatorioFinalAprovado(Monitoria monitoria) {
		RelatorioFinalRequestResult relFinalRequestResult = this.relatorioBean.consultaRelatorioFinalPorMonitor(monitoria.getAluno().getId());
		
		return relFinalRequestResult.result != null ? relFinalRequestResult.result.isHomologado() : false;
	}

	public void setMonitorias(List<Monitoria> monitorias) {
		this.monitorias = monitorias;
	}

	public ComponenteCurricular getComponenteRelatorio() {
		return componenteRelatorio;
	}

	public void setComponenteRelatorio(ComponenteCurricular componenteRelatorio) {
		this.componenteRelatorio = componenteRelatorio;
	}
}


