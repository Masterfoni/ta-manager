package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.RelatorioFinalLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.RelatorioFinalRequestResult;
import br.edu.ifpe.monitoria.utils.SessionContext;

@ManagedBean (name="relatorioFinalAvaliacaoView")
@ViewScoped
public class RelatorioFinalAvaliacaoView implements Serializable {

	private static final long serialVersionUID = -8481665492310127227L;

	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
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

	private boolean comissao;

	private Edital editalGlobal;

	public RelatorioFinalAvaliacaoView() {
		if(componenteRelatorio == null) {
			componenteRelatorio = (ComponenteCurricular) SessionContext.getInstance().getAttribute("componenteRelatorio");
		}
	}
	
	@PostConstruct
	public void init() {
		loggedServidor = servidorBean.consultaServidorById((Long)SessionContext.getInstance().getAttribute("id"));
		
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("comissao");
		
		editalGlobal = sharedMenuView.getEditalGlobal();
		
		List<PlanoMonitoria> planos;
		
		if(comissao) {
			planos = planoBean.consultaPlanosByEdital(editalGlobal, true); 
		} else {
			planos = planoBean.consultaPlanosByEditalServidor(loggedServidor.getId(), editalGlobal.getId());
		}
		
		for (int i = 0; i < planos.size(); i++) {
			PlanoMonitoria planoMonitoria = planos.get(i);
			
			if (planoMonitoria.getCc().getId().equals(componenteRelatorio.getId())) {
				planoRelatorio = planoMonitoria;
				break;
			}
		}
	}
	
	public List<Monitoria> getMonitorias() {
		if (monitorias == null) {
			monitorias = monitoriaBean.consultaMonitoriaSelecionadaByPlano(planoRelatorio);
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


