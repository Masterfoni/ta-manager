package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean (name="indexView")
@RequestScoped
public class IndexView implements Serializable {

	private static final long serialVersionUID = 1L;

	public IndexView() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		HttpServletRequest request = (HttpServletRequest) extContext.getRequest();
		
		if(request.getRemoteUser() != null)
		{
			try {
				extContext.redirect("../comum/homepage.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
