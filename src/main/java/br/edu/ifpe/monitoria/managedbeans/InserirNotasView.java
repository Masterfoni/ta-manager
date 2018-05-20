package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;

@ManagedBean (name="inserirNotasView")
@ViewScoped
public class InserirNotasView implements Serializable{

	private static final long serialVersionUID = -3536843049471998334L;
	private PlanoMonitoria plano;
	private List<Monitoria> monitorias;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;

	public InserirNotasView() {
		if(plano == null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			plano = (PlanoMonitoria) session.getAttribute("plano");
		}
	}

	public void salvarNotas() {
		monitoriaBean.salvarNotas(monitorias);
	}
	
	public PlanoMonitoria getPlano() {
		return plano;
	}

	public void setPlano(PlanoMonitoria plano) {
		this.plano = plano;
	}

	public List<Monitoria> getMonitorias() {
		if(monitorias == null) {
			monitorias = monitoriaBean.consultaMonitoriaByPlano(plano);
			monitorias = monitoriaBean.classificar(monitorias);
		}
		return monitorias;
	}

	public void setMonitorias(List<Monitoria> monitorias) {
		this.monitorias = monitorias;
	}
}
