package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.Remove;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "logoutView")
public class LogoutView implements Serializable {

	private static final long serialVersionUID = -2212283148264161669L;

	@Remove
	public String logout() throws ServletException, IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		if (session != null) {
			session.invalidate();
			System.out.println("Invalidando a sessão!");
		}

		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		request.logout();
		return "/publico/logout.xhtml";
	}

}
