package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_PROFESSOR")
@DiscriminatorValue(value="PROFESSOR")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
public class Professor extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Max(9999999)
	@Column (name="INT_SIAPE")
	private Integer siape;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TXT_TITULACAO")
	private Titulacao titulacao;
	
	public enum Titulacao {
		GRADUAÇÃO("Graduado"),
		ESPECIALIZAÇÃO("Especialista"),
		MESTRADO("Mestre"),
		DOUTORADO("Doutor");
		
		private String label;
		
		private Titulacao(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}

	public Integer getSiape() {
		return siape;
	}

	public void setSiape(Integer siape) {
		this.siape = siape;
	}

	public Titulacao getTitulacao() {
		return titulacao;
	}

	public void setTitulacao(Titulacao titulacao) {
		this.titulacao = titulacao;
	}
}
