package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
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
		monitorias = null;
	}
	
	public void alterarEmpate(Monitoria monitoria, boolean subir) {
		 List<Monitoria> empatados = getEmpatados(monitoria);
		 double notaAnterior = monitoria.getNotaDesempate();
		 double notaMaxima = empatados.size();
		 double novaNota;
		 if(subir) {
			 if(notaAnterior + 1.00 <= notaMaxima) {
				 novaNota = notaAnterior + 1.0;
			 } else {
				 novaNota = notaAnterior;
			 }
		 } else {
			 if(notaAnterior - 1.00 >= 0.0) {
				 novaNota = notaAnterior - 1.0;
			 } else {
				 novaNota = notaAnterior;
			 }
		 }
		 
		 if(novaNota != notaAnterior) {
			for (Monitoria m1 : monitorias) {
				if(empatados.contains(m1)) {
					if(m1.getNotaDesempate().doubleValue() == novaNota) {
						m1.setNotaDesempate(notaAnterior);
					}
					else if(m1.getId() == monitoria.getId()) {
						m1.setNotaDesempate(novaNota);
					}
				}
			}
		 }
		 
		 monitoriaBean.salvarNotas(monitorias);
		 monitorias = null;
	}
	
	private List<Monitoria> getEmpatados(Monitoria m1) {
		List<Monitoria> empatados = new ArrayList<>();
		for (Monitoria monitoria : monitorias) {
			if(monitoria.isEmpatado()) {
				if(monitoria.getMediaComponente().doubleValue() == m1.getMediaComponente().doubleValue() &&
						monitoria.getNotaSelecao().doubleValue() == m1.getNotaSelecao().doubleValue()) {
					empatados.add(monitoria);
				}
			}
		}
		return empatados;
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
	
}
