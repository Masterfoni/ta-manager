package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SequenceGenerator (name = "SEQUENCIA_USUARIO",
					sequenceName = "SQ_USUARIO",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_USUARIO")
@Access(AccessType.FIELD)
public class Aluno extends Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_USUARIO")
	private Long id;
	
	@Column (name="TXT_MATRICULA")
	private String matricula;
}
