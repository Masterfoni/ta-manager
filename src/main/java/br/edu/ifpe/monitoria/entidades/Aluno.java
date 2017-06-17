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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name="TB_ALUNO")
@DiscriminatorValue(value="Aluno")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
@Access(AccessType.FIELD)
public class Aluno extends Usuario implements Serializable {

	private static final long serialVersionUID = 2L;
	
	@NotNull(message = "{mensagem.notnull}{tipo.matricula}")
	@Pattern(regexp = "[0-9]{5}[a-zA-Z]{1}[0-9]{1}-[a-zA-Z]{2}[0-9]{4}", message = "{mensagem.matricula}")
	@Size (max=14)
	@Column (name="TXT_MATRICULA", unique=true)
	private String matricula;

	@NotBlank(message = "{mensagem.notnull}{tipo.cpf}")
	@CPF(message = "{mensagem.cpf}")
	@Column (name="TXT_CPF", unique=true)
	private String cpf;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.rg}")
	@Column (name="TXT_RG", unique=true)
	private String rg;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.emissor}")
	@Column (name="TXT_RG_EMISSOR")
	private String rgEmissor;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.sexo}")
	@Column (name="TXT_SEXO")
	private String sexo;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CURSO", referencedColumnName = "ID")
	private Coordenacao curso;
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRgEmissor() {
		return rgEmissor;
	}

	public void setRgEmissor(String rgEmissor) {
		this.rgEmissor = rgEmissor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Coordenacao getCurso() {
		return curso;
	}

	public void setCurso(Coordenacao curso) {
		this.curso = curso;
	}
}
