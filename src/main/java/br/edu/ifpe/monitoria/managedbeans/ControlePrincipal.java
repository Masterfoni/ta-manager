package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "controlePrincipal")
@RequestScoped
public class ControlePrincipal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String mensagem;
	
	public ControlePrincipal() {
		mensagem = "JACKSON0";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
