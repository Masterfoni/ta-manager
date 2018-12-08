package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;

@ManagedBean (name="gerenciaClassificadosView")
@ViewScoped
public class GerenciaClassificadosView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB 
	private ServidorLocalBean servidorBean;
	
	@EJB
	private PlanoMonitoriaLocalBean planoBean;
	
	private Edital editalGlobal;
	
	private List<PlanoMonitoria> planos;
	
	private List<Monitoria> monitorias;
	
	private PlanoMonitoria planoSelecionado;	
	
	private Servidor loggedServidor;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id")); 
		editalGlobal = sharedMenuView.getEditalGlobal();
		
		planos = editalGlobal != null ? planoBean.consultaPlanosByEdital(editalGlobal, true) : new ArrayList<PlanoMonitoria>();
		planoSelecionado = planos.size() > 0 ? planos.get(0) : new PlanoMonitoria();
	}
	
	public void homologarMonitoria(Monitoria monitoriaClassificada) {
		monitoriaClassificada.setHomologado(true);
		monitoriaBean.atualizaMonitoria(monitoriaClassificada);
	}

	public List<PlanoMonitoria> getPlanos() {
		planos = editalGlobal != null ? planoBean.consultaPlanosByEdital(editalGlobal, true) : new ArrayList<PlanoMonitoria>();
		return planos;
	}

	public void setPlanos(List<PlanoMonitoria> planos) {
		this.planos = planos;
	}

	public List<Monitoria> getMonitorias() {
		if(editalGlobal != null) {
			monitorias = planoSelecionado != null ? monitoriaBean.consultaMonitoriaSelecionadaByPlano(planoSelecionado) : new ArrayList<Monitoria>();
		} else {
			monitorias = new ArrayList<Monitoria>();
		}
		
		return monitorias;
	}

	public void setMonitorias(List<Monitoria> monitorias) {
		this.monitorias = monitorias;
	}

	public PlanoMonitoria getPlanoSelecionado() {
		return planoSelecionado;
	}

	public void setPlanoSelecionado(PlanoMonitoria planoSelecionado) {
		this.planoSelecionado = planoSelecionado;
	}

	public Servidor getLoggedServidor() {
		return loggedServidor;
	}

	public void setLoggedServidor(Servidor loggedServidor) {
		this.loggedServidor = loggedServidor;
	}
}


