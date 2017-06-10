package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="TB_ALUNO")
@DiscriminatorValue(value="A")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
@Access(AccessType.FIELD)
public class Aluno extends Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Pattern(regexp = "[0-9]{5}[a-zA-Z]{1}[0-9]{1}-[a-zA-Z]{2}[0-9]{4}", message = "{br.edu.ifpe.monitoria.entidades.Aluno.matricula}")
	@Size (max=13)
	@Column (name="TXT_MATRICULA")
	private String matricula;

	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CURSO", referencedColumnName = "ID")
	private Coordenacao curso;
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Coordenacao getCurso() {
		return curso;
	}

	public void setCurso(Coordenacao curso) {
		this.curso = curso;
	}
}
