package br.edu.ifpe.monitoria.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator (name = "SEQUENCIA_PERFILGOOGLE",
		sequenceName = "SQ_PERFILGOOGLE",
		initialValue = 1,
		allocationSize = 1)
@Table(name="TB_PERFILGOOGLE")
public class PerfilGoogle {

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_PERFILGOOGLE")
	private Long id;
	
	@OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
	private Usuario usuario;
		
	@Column (name="TXT_SUBJECT")
	private String subject;
	
	@Column (name="TXT_PICTURE")
	private String picture;
	
	@Column (name="TXT_GIVEN_NAME")
	private String givenName;
	
	@Column (name="TXT_FAMILY_NAME")
	private String familyName;
	
	@Column (name="TXT_HOSTED_DOMAIN")
	private String hostedDomain;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getHostedDomain() {
		return hostedDomain;
	}

	public void setHostedDomain(String hostedDomain) {
		this.hostedDomain = hostedDomain;
	}

	public Long getId() {
		return id;
	}
}
