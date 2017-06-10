package br.edu.ifpe.monitoria.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.api.client.json.jackson2.JacksonFactory;

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Professor;
import br.edu.ifpe.monitoria.localbean.PerfilGoogleLocalBean;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

@Stateless
@WebService(serviceName="googleSignInService")
@Path("/googleSignInService")
public class GoogleSignInService {

	private static final JacksonFactory jacksonFactory = new JacksonFactory();
	
//	@EJB
//	PerfilGoogleLocalBean pglb;
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	Professor professor;
	PerfilGoogle perfilGoogle;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey";
	}
	
	@POST 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces("application/json") 
	public void onSignIn(String resposta){
		
		Payload payload = verificarIntegridade(resposta);
		
		if(payload != null){
			
			professor = new Professor();
			perfilGoogle = new PerfilGoogle();
			
			professor.setEmail(payload.getEmail());
			professor.setNome((String) payload.get("name"));
			
			professor.setCpf("102.503.234-94");
			professor.setRg("a");
			professor.setRgEmissor("a");
			professor.setSenha("a");
			professor.setSexo("a");
			professor.setSiape(123);
			
			
			perfilGoogle.setFamilyName((String) payload.get("family_name"));
			perfilGoogle.setGivenName((String) payload.get("given_name"));
			perfilGoogle.setHostedDomain(payload.getHostedDomain());
			perfilGoogle.setPicture((String) payload.get("picture"));
			perfilGoogle.setSubject(payload.getSubject());
			perfilGoogle.setUsuario(professor);
			
			em.persist(perfilGoogle);
		}
		
	}
	
	private Payload verificarIntegridade(String resposta) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
			    .setAudience(Collections.singletonList("835716531913-m3mt3905k8itbmflg8t7mlqabgjcruce.apps.googleusercontent.com"))
			    .build();

		Payload payload;
		
		// (Receive idTokenString by HTTPS POST)
		try {
			GoogleIdToken idToken = verifier.verify(resposta);
			if (idToken != null) {
				payload = idToken.getPayload();
				// Print user identifier
				System.out.println("User ID: " + payload.getSubject());
				return payload;
			} else {
				System.out.println("Invalid ID token.");
				return null;
			}
			
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

//	@POST 
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
//	@Produces("application/json") 
//	public void cadastraUsuario(String resposta) {
//		System.out.println(resposta);
//	}
	
}
