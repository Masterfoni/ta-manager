package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator (name = "SEQUENCIA_GRUPO",
					sequenceName = "SQ_GRUPO",
					initialValue = 2,
					allocationSize = 1)
@Table(name="TB_USUARIO_GRUPO")
public class Grupo implements Serializable {

	private static final long serialVersionUID = -6564699194200777961L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_GRUPO")
	protected Long id;
	
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
	private Usuario usuario;
	
	@Column (name="TXT_EMAIL")
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column (name="TXT_GRUPO")
	private Grupos grupo;
	
	public enum Grupos {
		ALUNO("aluno"),
		PROFESSOR("professor"),
		COMISSAO("comissao"),
		COORDENADOR("coordenador");
		
		private String label;
		
		private Grupos(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}

	public Long getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grupos getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupos grupo) {
		this.grupo = grupo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
