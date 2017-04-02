package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Disciplina implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_DEPARTAMENTO")
	private Long id;
	
	@Column (name="TXT_NOME")
	private String nome;
	
	@Column (name="TXT_CODIGO")
	private String codigo;
	
	@Column (name="TXT_TURNO")
	private String turno;
	
	@Column (name="TXT_PERIODO")
	private String periodo;

	private Coordenacao coordenacao;
	private Professor professor;
}
