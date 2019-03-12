package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;
import br.edu.ifpe.monitoria.utils.Dominios;
import br.edu.ifpe.monitoria.utils.LongRequestResult;

@ManagedBean (name="googleSignInView")
@SessionScoped
public class GoogleSignInView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final JacksonFactory jacksonFactory = new JacksonFactory();
	
	private String idToken;
	
	private HttpSession session;
	
//	public GoogleSignInView() {
//		FacesContext fc = FacesContext.getCurrentInstance();
//		ExternalContext ec = fc.getExternalContext();
//		session = (HttpSession) ec.getSession(true);
//		session.setMaxInactiveInterval(20*60);
//	}
	
	@EJB
	private UsuarioLocalBean usuarioBean;
	
	public void loginGoogle() throws IOException 	{
		FacesContext fc = FacesContext.getCurrentInstance();
		
		Payload payload = verificarIntegridade(idToken);
		
		List<String> dominiosInstituncionais = new ArrayList<String>();
		List<String> dominiosAlunos = new ArrayList<String>();
		
		boolean emailInstituncional = false;
		boolean emailAluno = false;
		
		dominiosInstituncionais = Dominios.getDominiosInstituncionais();
		dominiosAlunos = Dominios.getDominiosAlunos();
		
		for(String dominio : dominiosInstituncionais) {
			if(payload != null && payload.getEmail() != null && payload.getEmail().substring(payload.getEmail().indexOf("@")).equals("@" + dominio))
			{
				emailInstituncional = true;
			}
		}
		for(String dominio : dominiosAlunos) {
			if(payload != null && payload.getEmail() != null && payload.getEmail().substring(payload.getEmail().indexOf("@")).equals("@" + dominio))
			{
				emailAluno = true;
			}
		}
		
		LongRequestResult idResult = usuarioBean.consultarIdByEmail(payload.getEmail());
		
		ExternalContext ec = fc.getExternalContext();
		
		if (session != null)
			session.invalidate();
		
		session = (HttpSession) ec.getSession(true);
		
		PerfilGoogle perfilMontado = prepareSessionForLoginServidor(payload, session, idResult.data);
		session.setAttribute("isServidor", emailInstituncional);
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpServletResponse response =  (HttpServletResponse) ec.getResponse();
		
		try {
			request.login(payload.getEmail(), perfilMontado.getSubject());
		} catch (ServletException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Por favor, utilize seu e-mail institucional."));
		} 
		
		if(!emailInstituncional && !emailAluno)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Por favor, utilize seu e-mail institucional."));
		} else {
			if(idResult.data != null) {
				//ec.redirect("/comum/homepage.xhtml");
				String redirect = response.encodeRedirectURL(request.getContextPath() + "/comum/homepage.xhtml");
				System.out.println(redirect + "*******************");
				response.sendRedirect(redirect);
				
				//String uri = "/comum/homepage.xhtml";
				//FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
			} else {
				if(emailAluno) {
					//ec.redirect("/publico/cadastroAluno.xhtml");
					String uri = "/publico/cadastroAluno.xhtml";
					//FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
					
					String redirect = response.encodeRedirectURL(request.getContextPath() + uri);
					System.out.println(redirect + "*******************");
					response.sendRedirect(redirect);
				}
				if(emailInstituncional) {
					//ec.redirect("/publico/cadastroServidor.xhtml");
					String uri = "/publico/cadastroServidor.xhtml";
					///FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
					
					String redirect = response.encodeRedirectURL(request.getContextPath() + uri);
					System.out.println(redirect + "*******************");
					response.sendRedirect(redirect);
				}
			}
		}
	}
	
	private PerfilGoogle prepareSessionForLoginServidor(Payload payload, HttpSession session, Long userId)
	{
		PerfilGoogle perfilGoogle = new PerfilGoogle();
		perfilGoogle.setFamilyName((String) payload.get("family_name"));
		perfilGoogle.setGivenName((String) payload.get("given_name"));
		perfilGoogle.setPicture((String) payload.get("picture"));
		perfilGoogle.setHostedDomain(payload.getHostedDomain());
		perfilGoogle.setSubject(payload.getSubject());
		
		String email = payload.getEmail();
		String nome = (String) payload.get("name");
		
		session.setAttribute("perfilGoogle", perfilGoogle);
		session.setAttribute("nome", nome);
		session.setAttribute("email", email);
		session.setAttribute("id", userId);
		
		return perfilGoogle;
	}
	
	private Payload verificarIntegridade(String idToken) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
			    .setAudience(Collections.singletonList("835716531913-m3mt3905k8itbmflg8t7mlqabgjcruce.apps.googleusercontent.com"))
			    .build();
		
		Payload payload = null;
		
		try {
			GoogleIdToken googleIdToken = verifier.verify(idToken);
			if (idToken != null) {
				payload = googleIdToken.getPayload();

				System.out.println("User ID: " + payload.getSubject());
			} else {
				System.out.println("Invalid ID token.");
			}
			
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return payload;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
}

