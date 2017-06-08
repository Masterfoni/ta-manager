package br.edu.ifpe.monitoria.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator (name = "SEQUENCIA_LOGINGOOGLE",
		sequenceName = "SQ_LOGINGOOGLE",
		initialValue = 1,
		allocationSize = 1)
@Table(name="TB_LOGINGOOGLE")
public class LoginGoogle {

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_LOGINGOOGLE")
	private Long id;
	
	@Column (name="TXT_SUBJECT")
	private String subject;
	
	@Column (name="TXT_NAME")
	private String name;
	
	@Column (name="TXT_PICTURE")
	private String picture;
	
	@Column (name="TXT_GIVEN_NAME")
	private String givenName;
	
	@Column (name="TXT_FAMILY_NAME")
	private String familyName;
	
	@Column (name="TXT_HOSTED_DOMAIN")
	private String hostedDomain;
	
}
