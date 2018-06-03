package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;

@ManagedBean (name="inserirNotasView")
@ViewScoped
public class InserirNotasView implements Serializable{

	private static final long serialVersionUID = -3536843049471998334L;
	private PlanoMonitoria plano;
	private List<Monitoria> monitorias;
	private boolean periodoDeInsercaoNotas;
	private Edital edital;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private EditalLocalBean editalBean;

	public InserirNotasView() {
		if(plano == null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			plano = (PlanoMonitoria) session.getAttribute("plano");
		}
	}

	public void salvarNotas() {
		monitoriaBean.salvarNotas(monitorias);
		monitorias = null;
	}
	
	public void alterarEmpate(Monitoria monitoria, boolean subir) {
		monitoriaBean.alterarPosicaoDoEmpate(monitoria, subir, monitorias);
		monitorias = null;
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
			monitorias = monitoriaBean.ordenar(monitorias);
		}
		return monitorias;
	}

	public void setMonitorias(List<Monitoria> monitorias) {
		this.monitorias = monitorias;
	}

	public boolean isPeriodoDeInsercaoNotas() {
		if(edital == null)
			edital = editalBean.consultaEditalById(plano.getEdital().getId());
		if(new Date().after(edital.getInicioInsercaoNota()) && 
				new Date().before(edital.getFimInsercaoNota()))
			setPeriodoDeInsercaoNotas(true);
		else
			setPeriodoDeInsercaoNotas(false);
		return periodoDeInsercaoNotas;
	}

	public void setPeriodoDeInsercaoNotas(boolean periodoDeInsercaoNotas) {
		this.periodoDeInsercaoNotas = periodoDeInsercaoNotas;
	}
}


