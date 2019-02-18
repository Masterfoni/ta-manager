package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.utils.SessionContext;

@ManagedBean (name="gerenciaCandidaturaView")
@ViewScoped
public class GerenciaCandidaturaView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MonitoriaLocalBean monitoriabean;

	public List<Monitoria> monitorias;
	
	public List<Monitoria> getMonitorias() {
		return monitorias;
	}

	public void setMonitorias(List<Monitoria> monitorias) {
		this.monitorias = monitorias;
	}

	@PostConstruct
	public void init() {		  
		monitorias = monitoriabean.consultaMonitoriaByProfessor((long)SessionContext.getInstance().getAttribute("id"));
	}
	
//	public String defereMonitoria(Monitoria monitoria) {
//		
//		monitorias.remove(monitoria);
//		
//		monitoriabean.defereMonitoria(monitoria);
//		
//		return "";
//	}
//	
//	public void aprovaMonitoria(Monitoria monitoria) {
//		monitoriabean.aprovaMonitoria(monitoria);
//	}
}
