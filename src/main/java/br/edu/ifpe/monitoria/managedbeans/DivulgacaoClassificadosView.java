package br.edu.ifpe.monitoria.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.utils.RecaptchaService;

@ManagedBean  (name="divulgacaoClassificadosView")
public class DivulgacaoClassificadosView {
	
	private boolean captchaValidado;
	
	private List<List<List<Monitoria>>> monitorias;
	private List<List<PlanoMonitoria>> planos;
	private List<Edital> editais;
	
	@EJB 
	private PlanoMonitoriaLocalBean planobean;
	
	@EJB
	private EditalLocalBean editalbean;
	
	@EJB
	private MonitoriaLocalBean monitoriabean;
	
	public DivulgacaoClassificadosView() {
		captchaValidado = false;
	}
	
	public void validarCaptcha() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		RecaptchaService recaptcha = new RecaptchaService();
		captchaValidado = recaptcha.checkRecaptcha(facesContext);
	}

	public boolean isCaptchaValidado() {
		return captchaValidado;
	}

	public void setCaptchaValidado(boolean captchaValidado) {
		this.captchaValidado = captchaValidado;
	}

	public List<Edital> getEditais() {
		if(editais == null) {
			editais = editalbean.consultaEditaisVigentes();
		}
		return editais;
	}

	public void setEditais(List<Edital> editais) {
		this.editais = editais;
	}

	public List<List<PlanoMonitoria>> getPlanos() {
		if(planos == null) {
			planos = new ArrayList<>();
			for (Edital edital : editais) {
				List<PlanoMonitoria> planinho = planobean.consultaPlanosByEdital(edital, true);
				planos.add(planinho);
			}
		}
		return planos;
	}

	public void setPlanos(List<List<PlanoMonitoria>> planos) {
		this.planos = planos;
	}


	public List<List<List<Monitoria>>> getMonitorias() {
		List<List<Monitoria>> monitoriasPorEdital;
		if(monitorias == null) {
			monitorias = new ArrayList<>();
			for(int i=0;i<editais.size();i++) {
				monitoriasPorEdital = new ArrayList<List<Monitoria>>();
				for (List<PlanoMonitoria> planinhos : planos) {
					for (PlanoMonitoria planinho : planinhos) {
						List<Monitoria> monitinha = monitoriabean.consultaMonitoriaByPlano(planinho);
						monitinha = monitoriabean.ordenar(monitinha);
						monitoriasPorEdital.add(monitinha);
					}
				}
				monitorias.add(monitoriasPorEdital);
			}
		}
		
		return monitorias;
	}


	public void setMonitorias(List<List<List<Monitoria>>> monitorias) {
		this.monitorias = monitorias;
	}
}
