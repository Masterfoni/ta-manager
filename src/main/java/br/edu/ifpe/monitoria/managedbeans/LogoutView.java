package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean (name="logoutView")
public class LogoutView implements Serializable{

	private static final long serialVersionUID = -2212283148264161669L;

	public void logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        if (session != null) {
            session.invalidate();
            System.out.println("Invalidando a sessão!");
        }
        
        
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();        
        try {
			request.logout();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
        try { 
        	ec.redirect("/FALJVAL/publico/logout.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
