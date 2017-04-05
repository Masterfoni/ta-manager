package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TB_PROFESSOR")
@DiscriminatorValue(value="P")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
public class Professor extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column (name="INT_SIAPE")
	private Integer siape;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TXT_TIPO_PROFESSOR")
	private TipoProfessor tipo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TXT_TITULACAO")
	private Titulacao titulacao;
	
	public enum Titulacao {
		GRADUAÇÃO,
		ESPECIALIZAÇÃO,
		MESTRADO,
		DOUTORADO
	}

	public Titulacao getTitulacao() {
		return titulacao;
	}

	public void setTiulacao(Titulacao titulacao) {
		this.titulacao = titulacao;
	}
	
	public enum TipoProfessor {
		COORDENADOR,
		NORMAL,
		COMISSAO	
	}

	public TipoProfessor getTipo() {
		return tipo;
	}

	public void setTipo(TipoProfessor tipo) {
		this.tipo = tipo;
	}

	public Integer getSiape() {
		return siape;
	}

	public void setSiape(Integer siape) {
		this.siape = siape;
	}
	
}
