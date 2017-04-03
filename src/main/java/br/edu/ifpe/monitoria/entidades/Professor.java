package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
	
	//enum funcao
}
