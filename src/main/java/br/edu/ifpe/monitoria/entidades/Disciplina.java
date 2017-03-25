package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

public class Disciplina implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String codigo;
	private String turno;
	private String periodo;

	private Coordenacao coordenacao;
	private Professor professor;
}
