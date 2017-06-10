package br.edu.ifpe.monitoria.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TB_ADMINISTRATIVO")
@DiscriminatorValue(value="A")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
public class Administrativo {

}
