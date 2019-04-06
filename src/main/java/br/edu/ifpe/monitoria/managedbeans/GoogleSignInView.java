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
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

@ManagedBean(name = "googleSignInView")
@ViewScoped
public class GoogleSignInView implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final JacksonFactory jacksonFactory = new JacksonFactory();

	private String idToken;

	private HttpSession session;

	@EJB
	private UsuarioLocalBean usuarioBean;
	
	public void loginGoogle() throws IOException, GeneralSecurityException {
		FacesContext fc = FacesContext.getCurrentInstance();

		Payload payload = verificarIntegridade(idToken);

		List<String> dominiosInstitucionais = new ArrayList<String>(Dominios.getDominiosInstituncionais());
		List<String> dominiosAlunos = new ArrayList<String>(Dominios.getDominiosAlunos());
		
		boolean emailInstitucional = dominiosInstitucionais.stream().anyMatch(dominio -> {
			return payload != null 
					&& payload.getEmail() != null 
					&& payload.getEmail().substring(payload.getEmail().indexOf("@")).equals("@" + dominio);
		});
		
		boolean emailAluno = dominiosAlunos.stream().anyMatch(dominio -> {
			return payload != null 
					&& payload.getEmail() != null 
					&& payload.getEmail().substring(payload.getEmail().indexOf("@")).equals("@" + dominio);
		});

		LongRequestResult idResult = usuarioBean.consultarIdByEmail(payload.getEmail());

		ExternalContext ec = fc.getExternalContext();

		if (session != null)
			session.invalidate();

		session = (HttpSession) ec.getSession(true);

		PerfilGoogle perfilMontado = prepareSessionForLoginServidor(payload, session, idResult.data);
		session.setAttribute("isServidor", emailInstitucional);
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		if (!emailInstitucional && !emailAluno) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Por favor, utilize seu e-mail institucional."));
		} else {
			try {
				request.login(payload.getEmail(), perfilMontado.getSubject());
			} catch (ServletException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Não foi possível realizar o login na aplicação, contate o suporte."));
			}
			
			if (idResult.data != null) {
				String uri = "/comum/homepage.xhtml?faces-redirect=true";
				fc.getApplication().getNavigationHandler().handleNavigation(fc, null, uri);
			} else {
				if (emailAluno) {
					String uri = "/publico/cadastroAluno.xhtml?faces-redirect=true";
					fc.getApplication().getNavigationHandler().handleNavigation(fc, null, uri);
				} else if (emailInstitucional) {
					String uri = "/publico/cadastroServidor.xhtml?faces-redirect=true";
					fc.getApplication().getNavigationHandler().handleNavigation(fc, null, uri);
				}
			}
		}
	}

	private PerfilGoogle prepareSessionForLoginServidor(Payload payload, HttpSession session, Long userId) {
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

	private Payload verificarIntegridade(String idToken) throws GeneralSecurityException, IOException {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
				.setAudience(Collections
						.singletonList("835716531913-m3mt3905k8itbmflg8t7mlqabgjcruce.apps.googleusercontent.com"))
				.build();

		Payload payload = null;

		GoogleIdToken googleIdToken = verifier.verify(idToken);
		if (idToken != null) {
			payload = googleIdToken.getPayload();
			System.out.println("User ID: " + payload.getSubject());
		} else {
			System.out.println("Invalid ID token.");
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
