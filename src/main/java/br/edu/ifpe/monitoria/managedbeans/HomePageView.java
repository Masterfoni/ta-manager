package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

@ManagedBean (name="homePageView")
public class HomePageView implements Serializable {

	private static final long serialVersionUID = 1L;

	public HomePageView() {
		
	}
	
	public String gotoGerUsuarios() {
		return "gerenciaUsuario";
		//		try {
		//			FacesContext.getCurrentInstance().getExternalContext().dispatch("/gerenciaUsuario.xhtml");
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
	}
}
