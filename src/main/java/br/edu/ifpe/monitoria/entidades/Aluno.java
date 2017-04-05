package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TB_ALUNO")
@DiscriminatorValue(value="A")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
@Access(AccessType.FIELD)
public class Aluno extends Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column (name="TXT_MATRICULA")
	private String matricula;

	@Column (name="TXT_CPF")
	private String cpf;
	
	@Column (name="TXT_RG")
	private String rg;
	
	@Column (name="TXT_RGEMISSOR")
	private String rgEmissor;
	
	@Column (name="TXT_SEXO")
	private boolean sexo;
	
	@Column (name="TXT_TELEFONE")
	private String telefone;
	
	@Column (name="TXT_CELULAR")
	private String celular;
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
