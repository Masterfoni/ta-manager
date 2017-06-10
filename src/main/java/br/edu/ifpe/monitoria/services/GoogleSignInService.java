package br.edu.ifpe.monitoria.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.api.client.json.jackson2.JacksonFactory;

import br.edu.ifpe.monitoria.entidades.PerfilGoogle;
import br.edu.ifpe.monitoria.entidades.Professor;
import br.edu.ifpe.monitoria.entidades.Usuario;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

@Stateless
@WebService(serviceName="googleSignInService")
@Path("/googleSignInService")
public class GoogleSignInService {

	private static final JacksonFactory jacksonFactory = new JacksonFactory();

	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	Professor professor;
	PerfilGoogle perfilGoogle;
	
	@POST 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.APPLICATION_JSON) 
	public PerfilGoogle onSignIn(String resposta){
		
		Payload payload = verificarIntegridade(resposta);
		perfilGoogle = null;
		
		if(payload != null){
			
			Usuario userResult = em.createNamedQuery("Usuario.findByEmail", Usuario.class).
						setParameter("email", payload.getEmail())
					   .getSingleResult();
			
			if(userResult != null) {
				// encaminha para a pagina principal pois o usuario já tem conta
			}
			else {
				//encaminha para a página de criação de conta
			}
			
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
			
			//em.persist(perfilGoogle);
		}
		else 
		{
			//encaminhar para pagina de login informando login invalido
			//ou houve um problema de autenticação, favor informar ao administrador
		}
		
		return perfilGoogle;
	}
	
	private Payload verificarIntegridade(String resposta) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
			    .setAudience(Collections.singletonList("835716531913-m3mt3905k8itbmflg8t7mlqabgjcruce.apps.googleusercontent.com"))
			    .build();
		
		Payload payload = null;
		
		// (Receive idTokenString by HTTPS POST)
		try {
			GoogleIdToken idToken = verifier.verify(resposta);
			if (idToken != null) {
				payload = idToken.getPayload();
				// Print user identifier
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
	
}
