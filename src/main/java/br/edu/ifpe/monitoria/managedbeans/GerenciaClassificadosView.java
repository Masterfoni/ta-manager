package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;

@ManagedBean (name="gerenciaClassificadosView")
@ViewScoped
public class GerenciaClassificadosView implements Serializable {

	private static final long serialVersionUID = -3536843049471998334L;
	
	private Edital edital;
	
	private List<PlanoMonitoria> planos;
	
	private List<Monitoria> monitorias;
	
	private PlanoMonitoria planoSelecionado;	
	
	private Servidor loggedServidor;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private EditalLocalBean editalBean;
	
	@EJB 
	private ServidorLocalBean servidorBean;
	
	@EJB
	private PlanoMonitoriaLocalBean planoBean;

	public GerenciaClassificadosView() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id")); 
		
		List<Edital> editaisVigentes = editalBean.consultaEditaisVigentes();
		
		edital = editaisVigentes.size() > 0 ? editaisVigentes.get(0) : null;
	}

	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}

	public List<PlanoMonitoria> getPlanos() {
		planos = edital != null ? planoBean.consultaPlanosByEdital(edital, true) : new ArrayList<PlanoMonitoria>();
		return planos;
	}

	public void setPlanos(List<PlanoMonitoria> planos) {
		this.planos = planos;
	}

	public List<Monitoria> getMonitorias() {
		monitorias = edital != null ? monitoriaBean.consultaMonitoriaByPlano(planoSelecionado) : new ArrayList<Monitoria>();
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


