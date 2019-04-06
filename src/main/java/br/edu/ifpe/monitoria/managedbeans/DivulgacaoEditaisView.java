package br.edu.ifpe.monitoria.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.utils.RecaptchaService;

@ManagedBean  (name="divulgacaoEditaisView")
public class DivulgacaoEditaisView {
	
	private boolean captchaValidado;
	
	private List<Edital> editais;
	
	
	@EJB
	private EditalLocalBean editalbean;
	
	public DivulgacaoEditaisView() {
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
}
