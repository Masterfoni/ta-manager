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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="TB_ALUNO")
@DiscriminatorValue(value="ALUNO")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "Aluno.findById", query = "SELECT a FROM Aluno a WHERE a.id = :id")
})
public class Aluno extends Usuario implements Serializable {

	private static final long serialVersionUID = 2L;

	@NotNull(message = "{mensagem.notnull}{tipo.matricula}")
	@Pattern(regexp = "[0-9]{5}[a-zA-Z]{1}[0-9]{1}-[a-zA-Z]{2}[0-9]{4}", message = "{mensagem.matricula}")
	@Size (max=14)
	@Column (name="TXT_MATRICULA", unique=true)
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
