package br.edu.ifpe.monitoria.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.utils.RecaptchaService;

@ManagedBean  (name="divulgacaoCCView")
public class DivulgacaoCC {
	
	private boolean captchaValidado;
	
	public DivulgacaoCC() {
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
}
