package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Frequencia;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.FrequenciaLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;

@ManagedBean (name="relatorioMensalView")
@ViewScoped
public class RelatorioMensalView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
	private ComponenteCurricular componenteRelatorio;
	
	private GregorianCalendar mesRelatorio;
	
	private List<Monitoria> monitorias;
	
	private PlanoMonitoria planoRelatorio;
	
	private Servidor loggedServidor;
	
	@EJB
	private PlanoMonitoriaLocalBean planoBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private FrequenciaLocalBean frequenciaBean;
	
	@EJB
	private ServidorLocalBean servidorBean;
	
	private boolean comissao;

	private Edital editalGlobal;

	public RelatorioMensalView() {
		if(componenteRelatorio == null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			componenteRelatorio = (ComponenteCurricular) session.getAttribute("componenteRelatorio");
		}
		
		if(mesRelatorio == null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			mesRelatorio = (GregorianCalendar) session.getAttribute("mesRelatorio");
		}
	}
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id"));
		
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("comissao");
		
		editalGlobal = sharedMenuView.getEditalGlobal();
		
		List<PlanoMonitoria> planos;
		
		if(comissao) {
			planos = planoBean.consultaPlanosByEdital(editalGlobal, true); 
		}else {
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
	
	public boolean getStatusFrequencia(Monitoria monitoria) {
		Frequencia frequenciaMensal = frequenciaBean.findSingleByMonitoriaMes(monitoria, mesRelatorio);
		
		return frequenciaMensal != null ? frequenciaMensal.isRecebido() : false;
	}

	public List<Monitoria> getMonitorias() {
		if (monitorias == null) {
			monitorias = monitoriaBean.consultaMonitoriaSelecionadaByPlano(planoRelatorio);
		}
		
		return monitorias;
	}

	public void setMonitorias(List<Monitoria> monitorias) {
		this.monitorias = monitorias;
	}
	
	public String getNomeMes() {
		Locale brazil = new Locale("pt", "BR");
		return mesRelatorio.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, brazil) + "/" + 
			mesRelatorio.get(GregorianCalendar.YEAR);
	}

	public ComponenteCurricular getComponenteRelatorio() {
		return componenteRelatorio;
	}

	public void setComponenteRelatorio(ComponenteCurricular componenteRelatorio) {
		this.componenteRelatorio = componenteRelatorio;
	}

	public GregorianCalendar getMesRelatorio() {
		return mesRelatorio;
	}

	public void setMesRelatorio(GregorianCalendar mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}
}


