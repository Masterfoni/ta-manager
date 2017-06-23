package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_ADMINISTRATIVO")
@DiscriminatorValue(value="ADMINISTRATIVO")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
public class Administrativo extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Max(9999999)
	@Column (name="INT_SIAPE")
	private Integer siape;

	public Integer getSiape() {
		return siape;
	}

	public void setSiape(Integer siape) {
		this.siape = siape;
	}
	
}
