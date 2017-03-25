package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

public class Coordenacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String sigla;
	private Departamento departamento;
	private Usuario coordenador;
	
}
