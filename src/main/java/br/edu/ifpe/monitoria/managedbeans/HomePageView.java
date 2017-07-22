package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;

@ManagedBean (name="homePageView")
public class HomePageView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	private List<Monitoria> monitorias;
	
	public HomePageView() {
		monitorias = new ArrayList<Monitoria>();
	}
	
	@PostConstruct
	public void init() {
		monitorias = monitoriaBean.consultaMonitoriasAvaliadas();
	}

	public List<Monitoria> getMonitorias() {
		return monitorias;
	}

	public void setMonitorias(List<Monitoria> monitorias) {
		this.monitorias = monitorias;
	}
}
